package tarski
package controller
package game

import tarski.model.toNameMap
import tarski.controller.Interpreter.sub
import tarski.model.Name

type Message = String

case class Game(
    commitment: Option[Boolean] = None,
    formula: FOLFormula,
    left: Option[FOLFormula] = None,
    right: Option[FOLFormula] = None,
    selected: Option[Name] = None
):
  def myChoice(f: FOLFormula)(using nm: NameMap): Name = ???
  def blockChoice(using nm: NameMap): Name             = ???
  def commitChoice: Boolean                            = ???

  def next(using nm: NameMap): Game = (commitment, formula) match
    case (None, _)                  => copy(commitment = Some(commitChoice))
    case (Some(commit), a: FOLAtom) =>
      val result  = Interpreter.eval(a)
      val winLose = if commit == result then "You win!" else "You lose."
      val msg     = s"$winLose $a is $result in this world."
      this
    case (Some(true), And(a, b)) =>
      val evalA  = Interpreter.eval(a)
      val choice = if evalA then b else a
      val msg1   = s"You believe both $a and $b are true."
      val msg2   = s"I choose $choice as false."
      copy(formula = choice)
    case (Some(false), And(a, b)) =>
      val msg1 = s"You believe at least one of $a or $b is false."
      copy(left = Some(a), right = Some(b))
    case (Some(true), Or(a, b)) =>
      val msg1 = s"You believe at least one of $a or $b is true."
      copy(left = Some(a), right = Some(b))
    case (Some(false), Or(a, b)) =>
      val evalA  = Interpreter.eval(a)
      val choice = if evalA then a else b
      val msg1   = s"You believe both $a and $b are false."
      val msg2   = s"I choose $choice as true."
      copy(formula = choice)
    case (Some(commit), Neg(a))                 => copy(commitment = Some(!commit), formula = a)
    case (_, Imp(a, b))                         => copy(formula = Or(Neg(a), b))
    case (_, Iff(a: FOLFormula, b: FOLFormula)) => copy(formula = And(Imp(a, b), Imp(b, a)))
    case (Some(true), All(x, f))                => this
    case (Some(false), All(x, f))               =>
      selected match
        case None       => this
        case Some(name) => this
    case (Some(true), Ex(x, f)) =>
      selected match
        case None       => this
        case Some(name) => this
    case (Some(false), Ex(x, f)) => this
    case _                       => this

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
