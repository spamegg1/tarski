package tarski
package controller
package game

import tarski.model.toNameMap
import tarski.controller.Interpreter.sub
import tarski.model.Name

type Message = String

enum Turn:
  case You, Me

enum Outcome:
  case Win, Loss

case class Game(
    turn: Turn = Turn.You,
    commitment: Option[Boolean] = None,
    formula: Option[FOLFormula] = None,
    leftOpt: Option[FOLFormula] = None,
    rightOpt: Option[FOLFormula] = None,
    selected: Option[Name] = None,
    outcome: Option[Outcome] = None
):
  def next(using nm: NameMap): Game = formula match
    case None    => ???
    case Some(f) =>
      f match
        case a: FOLAtom =>
          commitment match
            case None         => this
            case Some(commit) =>
              val result  = Interpreter.eval(a)
              val winLose = if commit == result then "You win!" else "You lose."
              val msg     = s"$winLose $a is $result in this world."
              this
        case And(a, b) =>
          (commitment, turn) match
            case (None, _)              => this
            case (Some(true), Turn.You) =>
              copy(turn = Turn.Me, formula = None, leftOpt = Some(a), rightOpt = Some(b))
            case (Some(true), Turn.Me) => // should not happen
              copy(turn = Turn.You, formula = None, leftOpt = Some(a), rightOpt = Some(b))
            case (Some(false), Turn.You) =>
              copy(formula = None, leftOpt = Some(a), rightOpt = Some(b))
            case (Some(false), Turn.Me) => // should not happen
              copy(formula = None, leftOpt = Some(a), rightOpt = Some(b))
        case Or(a, b) =>
          (commitment, turn) match
            case (None, _)              => this
            case (Some(true), Turn.You) =>
              copy(formula = None, leftOpt = Some(a), rightOpt = Some(b))
            case (Some(true), Turn.Me) => // should not happen
              copy(formula = None, leftOpt = Some(a), rightOpt = Some(b))
            case (Some(false), Turn.You) =>
              copy(turn = Turn.Me, formula = None, leftOpt = Some(a), rightOpt = Some(b))
            case (Some(false), Turn.Me) => // should not happen
              copy(turn = Turn.You, formula = None, leftOpt = Some(a), rightOpt = Some(b))
        case Neg(a) =>
          (commitment, turn) match
            case (Some(commit), Turn.You) => copy(commitment = Some(!commit), formula = Some(a))
            case _                        => this
        case Imp(a, b)                         => copy(formula = Some(Or(Neg(a), b)))
        case Iff(a: FOLFormula, b: FOLFormula) => copy(formula = Some(And(Imp(a, b), Imp(b, a))))
        case All(x, f)                         =>
          (commitment, turn) match
            case (None, _)               => this
            case (Some(true), Turn.Me)   => this
            case (Some(false), Turn.Me)  => this
            case (Some(true), Turn.You)  => copy(turn = Turn.Me)
            case (Some(false), Turn.You) =>
              selected match
                case None       => this
                case Some(name) => this

        case Ex(x, f) =>
          (commitment, turn) match
            case (None, _)              => this
            case (Some(true), Turn.Me)  => this
            case (Some(false), Turn.Me) => this
            case (Some(true), Turn.You) =>
              selected match
                case None       => this
                case Some(name) => this
            case (Some(false), Turn.You) => copy(commitment = Some(true), turn = Turn.Me)

object Game:
  def playGame(formula: FOLFormula, world: World, commitment: Boolean): Unit =
    given nm: NameMap = world.board.grid.toNameMap
    println(s"You believe $formula is $commitment")
    formula match
      case a: FOLAtom =>
        val result = Interpreter.eval(a)
        val msg    = if commitment == result then "You win!" else "You lose."
        println(s"$msg $a is $result in this world.")
      case And(a, b) =>
        if commitment then
          println(s"You believe both $a and $b are $commitment")
          println(s"I will try to choose a ${!commitment} formula")
          val resA   = Interpreter.eval(a)
          val chosen = if resA then b else a
          println(s"I chose $chosen as ${!commitment}")
          playGame(chosen, world, commitment)
        else
          println(s"You believe at least one of $a and $b is $commitment")
          println(s"You will try to choose a $commitment formula")
          val chosen = handleChoice(a, b)
          playGame(chosen, world, commitment)
      case Or(a, b) =>
        if commitment then
          println(s"You believe at least one of $a and $b is $commitment")
          println(s"You will try to choose a $commitment formula")
          val chosen = handleChoice(a, b)
          playGame(chosen, world, commitment)
        else
          println(s"You believe both $a and $b are $commitment")
          println(s"I will try to choose a ${!commitment} formula")
          val resA   = Interpreter.eval(a)
          val chosen = if resA then a else b
          println(s"I chose $chosen as ${!commitment}")
          playGame(chosen, world, commitment)
      case Neg(a)    => playGame(a, world, !commitment)
      case Imp(a, b) =>
        val rewrite = Or(Neg(a), b)
        println(s"$formula can be written as $rewrite")
        playGame(rewrite, world, commitment)
      case Iff(a: FOLFormula, b: FOLFormula) =>
        val rewrite = And(Imp(a, b), Imp(b, a))
        println(s"$formula can be written as $rewrite")
        playGame(rewrite, world, commitment)
      case All(x, f) =>
        if commitment then
          println(s"You believe every object satisfies $f")
          println("I will try to choose a counterexample")
          val results = nm.map:
            case (name, (block, pos)) =>
              name -> Interpreter.eval(f.sub(x, FOLConst(name)))
          val choice = results.find(!_._2) match
            case None               => results.head._1
            case Some((name, bool)) => name
          val newFormula = f.sub(x, FOLConst(choice))
          println(s"I chose $newFormula as ${!commitment}")
          playGame(newFormula, world, commitment)
        else
          println(s"You believe some object does not satisfy $f")
          println("You will try to choose a counterexample")
          println("Choose a block, then click OK")
          val choice     = handleBlockChoice
          val newFormula = f.sub(x, FOLConst(choice))
          playGame(newFormula, world, commitment)
      case Ex(x, f) =>
        if commitment then
          println(s"You believe some object satisfies $f")
          println("You will try to choose an instance that satisfies it")
          println("Choose a block, then click OK")
          val choice     = handleBlockChoice
          val newFormula = f.sub(x, FOLConst(choice))
          playGame(newFormula, world, commitment)
        else
          println(s"You believe no object satisfies $f")
          println(s"I will try to choose an instance that satisfies it")
          val results = nm.map:
            case (name, (block, pos)) =>
              name -> Interpreter.eval(f.sub(x, FOLConst(name)))
          val choice = results.find(_._2) match
            case None               => results.head._1
            case Some((name, bool)) => name
          val newFormula = f.sub(x, FOLConst(choice))
          println(s"I chose $newFormula as ${!commitment}")
          playGame(f.sub(x, FOLConst(choice)), world, commitment)

  def handleChoice(a: FOLFormula, b: FOLFormula): FOLFormula = a    // TODO
  def handleBlockChoice: Name                                = "b0" // TODO

/*
Buttons: OK, Back, Choose true, Choose false, Choose left, Choose right

Atomic true/false -> just evaluate, then check against belief -> win/lose
¬f true   -> you believe f is false, eval
¬f false  -> you believe f is true, eval
f∧g true  -> you believe both f,g are true, TW chooses false formula
f∧g false -> you believe at least one of f,g is false, YOU choose false formula
f∨g true  -> you believe at least one of f,g is true, YOU choose true formula
f∨g false -> you believe both f,g are false, TW chooses true formula
f→g true  -> rewrite as ¬f∨g
f→g false -> rewrite as ¬f∨g
f↔g true  -> rewrite as (f→g)∧(g→f)
f↔g false -> rewrite as (f→g)∧(g→f)
∀xf true  -> you believe every object satisfies fx, TW chooses false counterexample
∀xf false -> you believe some object does not satisfy fx, YOU choose false counterexample
∃xf true  -> you believe some object satisfies fx, YOU choose true instance
∃xf false -> you believe no object satisfies fx, TW chooses true instance

 */
