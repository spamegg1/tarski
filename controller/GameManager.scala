package tarski
package controller

type Message  = String
type Messages = List[Message]
type Step     = (game: Game, msgs: Messages)

case class GameManager(steps: List[Step]):
  def rewind = steps match
    case head :: next => GameManager(next)
    case Nil          => this
  given NameMap           = Map()
  def addStep(game: Game) = GameManager((game, generateMessages(game)) :: steps)

import Select.*

def generateMessages(g: Game)(using nm: NameMap) = (g.commitment, g.formula) match
  case (Some(commit), a: FOLAtom) =>
    val result  = Interpreter.eval(a)
    val winLose = if commit == result then "You win!" else "You lose."
    val msg     = s"$winLose $a is $result in this world."
    List(msg)

  case (Some(true), And(a, b)) =>
    val evalA  = Interpreter.eval(a)
    val choice = if evalA then b else a
    val msg1   = s"You believe both $a and $b are true."
    val msg2   = s"I choose $choice as false."
    msg2 :: msg1 :: Nil

  case (Some(false), And(a, b)) =>
    g.pos match
      case Off =>
        val msg1 = s"You believe one of $a or $b is ${g.commitment}."
        val msg2 = s"Choose a ${g.commitment} formula above."
        // g.copy(left = Some(a), right = Some(b), pos = Wait)
        msg2 :: msg1 :: Nil
      case Wait  => Nil // g // Wait => Off transition (selecting L/R) handled elsewhere
      case On(_) => Nil // g

  case (Some(true), Or(a, b)) =>
    g.pos match
      case Off =>
        val msg1 = s"You believe one of $a or $b is ${g.commitment}."
        val msg2 = s"Choose a ${g.commitment} formula above."
        // g.copy(left = Some(a), right = Some(b), pos = Wait)
        msg2 :: msg1 :: Nil
      case Wait  => Nil // g // Wait => Off transition (selecting L/R) handled elsewhere
      case On(_) => Nil // g

  case (Some(false), Or(a, b)) =>
    val evalA  = Interpreter.eval(a)
    val choice = if evalA then a else b
    val msg1   = s"You believe both $a and $b are false."
    val msg2   = s"I choose $choice as true."
    // g.copy(formula = choice)
    msg2 :: msg1 :: Nil

  case (Some(commit), Neg(a)) => Nil
  // g.copy(commitment = Some(!commit), formula = a)

  case (_, Imp(a, b)) =>
    val f   = Or(Neg(a), b)
    val msg = s"${g.formula} can be written as $f"
    // g.copy(formula = f)
    List(msg)

  case (_, Iff(a: FOLFormula, b: FOLFormula)) =>
    val f   = And(Imp(a, b), Imp(b, a))
    val msg = s"${g.formula} can be written as $f"
    // g.copy(formula = f)
    List(msg)

  case (Some(true), All(x, f)) =>
    val msg1   = s"You believe ${g.formula} is true."
    val msg2   = s"You believe every object [${x.name}] satisfies $f"
    val choice = nm.keys
      .map(name => name -> Interpreter.eval(f.sub(x, name)))
      .find(!_._2) match
      case None            => nm.keys.head
      case Some((name, _)) => name
    val msg3 = s"I choose $choice as my counterexample"
    // g.copy(formula = f.sub(x, choice))
    msg3 :: msg2 :: msg1 :: Nil

  case (Some(false), All(x, f)) =>
    g.pos match
      case Off =>
        val msg1 = s"You believe some object [${x.name}] falsifies $f"
        val msg2 = s"Click on a block, then click OK"
        // g.copy(pos = Wait)
        msg2 :: msg1 :: Nil
      case Wait     => Nil // g // Wait => On transition handled elsewhere
      case On(name) => Nil // g.copy(formula = f.sub(x, ???), pos = Off)

  case (Some(true), Ex(x, f)) =>
    g.pos match
      case Off =>
        val msg1 = s"You believe some object [${x.name}] satisfies $f"
        val msg2 = s"Click on a block, then click OK"
        // g.copy(pos = Wait)
        msg2 :: msg1 :: Nil
      case Wait     => Nil // g // Wait => On transition handled elsewhere
      case On(name) => Nil // g.copy(formula = f.sub(x, ???), pos = Off)

  case (Some(false), Ex(x, f)) =>
    val msg1   = s"You believe ${g.formula} is false."
    val msg2   = s"You believe no object [${x.name}] satisfies $f"
    val choice = nm.keys
      .map(name => name -> Interpreter.eval(f.sub(x, name)))
      .find(_._2) match
      case None            => nm.keys.head
      case Some((name, _)) => name
    val msg3 = s"I choose $choice as an instance that satisfies it"
    // g.copy(formula = f.sub(x, choice))
    msg3 :: msg2 :: msg1 :: Nil

  case _ => Nil
