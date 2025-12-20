package tarski
package controller
package game

import tarski.model.toNameMap
import tarski.controller.Interpreter.sub
import tarski.model.Name

type Message = String

// There will be a handler that initially "sets up" the game by first
// asking the user for their commitment. So we don't have to use Option[Boolean].

enum Select:
  case Off
  case Wait
  case On(name: Name)

enum Choice:
  case Off, Wait

case class Game(
    commitment: Boolean,
    formula: FOLFormula,
    left: Option[FOLFormula] = None,
    right: Option[FOLFormula] = None,
    selected: Select = Select.Off,
    choice: Choice = Choice.Off,
    messages: List[Message] = Nil
):
  def next(using nm: NameMap): Game = (commitment, formula) match
    case (commit, a: FOLAtom) =>
      val result  = Interpreter.eval(a)
      val winLose = if commit == result then "You win!" else "You lose."
      val msg     = s"$winLose $a is $result in this world."
      copy(messages = msg :: messages)

    case (true, And(a, b)) =>
      val evalA  = Interpreter.eval(a)
      val choice = if evalA then b else a
      val msg1   = s"You believe both $a and $b are true."
      val msg2   = s"I choose $choice as false."
      copy(formula = choice, messages = msg2 :: msg1 :: messages)

    case (false, And(a, b)) =>
      choice match
        case Choice.Off =>
          val msg1 = s"You believe one of $a or $b is $commitment."
          val msg2 = s"Choose a $commitment formula above."
          copy(left = Some(a), right = Some(b), messages = msg2 :: msg1 :: messages, choice = Choice.Wait)
        case Choice.Wait => this // Wait => Off transition (selecting L/R) handled elsewhere

    case (true, Or(a, b)) =>
      choice match
        case Choice.Off =>
          val msg1 = s"You believe one of $a or $b is $commitment."
          val msg2 = s"Choose a $commitment formula above."
          copy(left = Some(a), right = Some(b), messages = msg2 :: msg1 :: messages, choice = Choice.Wait)
        case Choice.Wait => this // Wait => Off transition (selecting L/R) handled elsewhere

    case (false, Or(a, b)) =>
      val evalA  = Interpreter.eval(a)
      val choice = if evalA then a else b
      val msg1   = s"You believe both $a and $b are false."
      val msg2   = s"I choose $choice as true."
      copy(formula = choice, messages = msg2 :: msg1 :: messages)

    case (commit, Neg(a)) => copy(commitment = !commit, formula = a)

    case (_, Imp(a, b)) =>
      val f   = Or(Neg(a), b)
      val msg = s"$formula can be written as $f"
      copy(formula = f, messages = msg :: messages)

    case (_, Iff(a: FOLFormula, b: FOLFormula)) =>
      val f   = And(Imp(a, b), Imp(b, a))
      val msg = s"$formula can be written as $f"
      copy(formula = f, messages = msg :: messages)

    case (true, All(x, f)) =>
      val msg1   = s"You believe $formula is true."
      val msg2   = s"You believe every object [${x.name}] satisfies $f"
      val choice = nm.keys
        .map(name => name -> Interpreter.eval(f.sub(x, name)))
        .find(!_._2) match
        case None            => nm.keys.head
        case Some((name, _)) => name
      val msg3 = s"I choose $choice as my counterexample"
      copy(formula = f.sub(x, choice), messages = msg3 :: msg2 :: msg1 :: messages)

    case (false, All(x, f)) =>
      selected match
        case Select.Off =>
          val msg1 = s"You believe some object [${x.name}] falsifies $f"
          val msg2 = s"Click on a block, then click OK"
          copy(messages = msg2 :: msg1 :: messages, selected = Select.Wait)
        case Select.Wait     => this // Wait => On transition handled elsewhere
        case Select.On(name) => copy(formula = f.sub(x, name), selected = Select.Off)

    case (true, Ex(x, f)) =>
      selected match
        case Select.Off =>
          val msg1 = s"You believe some object [${x.name}] satisfies $f"
          val msg2 = s"Click on a block, then click OK"
          copy(messages = msg2 :: msg1 :: messages, selected = Select.Wait)
        case Select.Wait     => this // Wait => On transition handled elsewhere
        case Select.On(name) => copy(formula = f.sub(x, name), selected = Select.Off)

    case (false, Ex(x, f)) =>
      val msg1   = s"You believe $formula is false."
      val msg2   = s"You believe no object [${x.name}] satisfies $f"
      val choice = nm.keys
        .map(name => name -> Interpreter.eval(f.sub(x, name)))
        .find(_._2) match
        case None            => nm.keys.head
        case Some((name, _)) => name
      val msg3 = s"I choose $choice as an instance that satisfies it"
      copy(formula = f.sub(x, choice), messages = msg3 :: msg2 :: msg1 :: messages)

    case _ => this
  end next

  def chooseBlock(name: Name): Game = selected match
    case Select.Off   => this // should not happen
    case Select.Wait  => copy(selected = Select.On(name))
    case Select.On(_) => copy(selected = Select.On(name))

  def chooseFormula(f: FOLFormula): Game = choice match
    case Choice.Off  => this // should not happen
    case Choice.Wait => copy(formula = f, left = None, right = None, choice = Choice.Off)
