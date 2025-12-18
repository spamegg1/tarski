package tarski
package controller
package game

import tarski.model.toNameMap
import tarski.controller.Interpreter.sub
import tarski.model.Name

type Message = String

// There will be a handler that initially "sets up" the game by first
// asking the user for their commitment. So we don't have to use Option[Boolean].

case class Game(
    commitment: Boolean,
    formula: FOLFormula,
    left: Option[FOLFormula] = None,
    right: Option[FOLFormula] = None,
    selected: Option[Name] = None,
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
      val msg = s"You believe at least one of $a or $b is false."
      copy(left = Some(a), right = Some(b), messages = msg :: messages)
    case (true, Or(a, b)) =>
      val msg = s"You believe at least one of $a or $b is true."
      copy(left = Some(a), right = Some(b), messages = msg :: messages)
    case (false, Or(a, b)) =>
      val evalA  = Interpreter.eval(a)
      val choice = if evalA then a else b
      val msg1   = s"You believe both $a and $b are false."
      val msg2   = s"I choose $choice as true."
      copy(formula = choice, messages = msg2 :: msg1 :: messages)
    case (commit, Neg(a)) => copy(commitment = !commit, formula = a)
    case (_, Imp(a, b))   =>
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
        case None       => this // choosing a block will be handled elsewhere
        case Some(name) => copy(formula = f.sub(x, name))
    case (true, Ex(x, f)) =>
      selected match
        case None       => this // choosing a block will be handled elsewhere
        case Some(name) => copy(formula = f.sub(x, name))
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

  def chooseBlock(name: Name, formula: FOLFormula): Game =
    val msg2 = s"Click on a block, then click OK"
    formula match
      case All(x, f) =>
        val msg1 = s"You believe some object [${x.name}] falsifies $f"
        copy(formula = f.sub(x, name), messages = msg2 :: msg1 :: messages)
      case Ex(x, f) =>
        val msg1 = s"You believe some object [${x.name}] satisfies $f"
        copy(formula = f.sub(x, name), messages = msg2 :: msg1 :: messages)

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
