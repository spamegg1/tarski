package tarski
package controller

/** Generates messages for the game by handling the computer player's choices. Uses [[Interpreter]]. */
object Messager:
  /** Generates messages to be displayed to the user about the current state of the game.
    *
    * @param play
    *   The current state of play.
    * @param nm
    *   A [[model.NameMap]] to look up blocks and to evaluate formulas with the interpreter.
    * @return
    *   A list of [[model.Messages]].
    */
  def show(play: Play)(using nm: NameMap): Messages =
    (play.commitment, play.formula) match
      case (Some(false), And(a, b)) => chooseAndOr(a, b)(false)
      case (Some(true), Or(a, b))   => chooseAndOr(a, b)(true)

      case (Some(commit), Neg(a)) =>
        val msg1 = s"You believe this is $commit:"
        val msg2 = ui"${Neg(a)}"
        val msg3 = s"So you believe this is ${!commit}:"
        val msg4 = ui"$a"
        msg1 :: msg2 :: msg3 :: msg4 :: Nil

      case (_, Imp(a, b)) =>
        val msg1 = ui"${play.formula} can be written as:"
        val msg2 = ui"${Or(Neg(a), b)}"
        msg1 :: msg2 :: Nil

      case (_, Iff(a: FOLFormula, b: FOLFormula)) =>
        val msg1 = ui"${play.formula} can be written as:"
        val msg2 = ui"${And(Imp(a, b), Imp(b, a))}"
        msg1 :: msg2 :: Nil

      // combine these two
      case (Some(false), All(x, f)) =>
        val msg1 = s"You believe some object [${x.name}] falsifies:"
        val msg2 = ui"$f"
        val msg3 = "Click on a block, then click OK"
        msg1 :: msg2 :: msg3 :: Nil

      case (Some(true), Ex(x, f)) =>
        val msg1 = s"You believe some object [${x.name}] satisfies:"
        val msg2 = ui"$f"
        val msg3 = "Click on a block, then click OK"
        msg1 :: msg2 :: msg3 :: Nil

      // Cases that need Interpreter and NameMap
      case (Some(commit), a: FOLAtom) =>
        val result = Interpreter.eval(a)
        val msg1   = if commit == result then "You win!" else "You lose."
        val msg2   = ui"$a" + s" is $result in this world."
        msg1 :: msg2 :: Nil

      // combine these two
      case (Some(true), And(a, b)) =>
        val choice = if Interpreter.eval(a) then b else a // choose FALSE formula
        val msg1   = "You believe both are true:"
        val msg2   = ui"$a and $b"
        val msg3   = ui"I choose $choice as false."
        msg1 :: msg2 :: msg3 :: Nil

      case (Some(false), Or(a, b)) =>
        val choice = if Interpreter.eval(a) then a else b // choose TRUE formula
        val msg1   = "You believe both are false:"
        val msg2   = ui"$a and $b"
        val msg3   = ui"I choose $choice as true."
        msg1 :: msg2 :: msg3 :: Nil

      // combine these two
      case (Some(true), All(x, f)) =>
        val msg1   = s"You believe every object [${x.name}] satisfies:"
        val msg2   = ui"$f"
        val choice = AI.chooseBlock(f, x)(false)
        val msg3   = s"I choose $choice as my counterexample"
        msg1 :: msg2 :: msg3 :: Nil

      case (Some(false), Ex(x, f)) =>
        val msg1   = s"You believe no object [${x.name}] satisfies:"
        val msg2   = ui"$f"
        val choice = AI.chooseBlock(f, x)(true)
        val msg3   = s"I choose $choice as my example"
        msg1 :: msg2 :: msg3 :: Nil

      case _ => Nil
  end show

  /** Generates [[model.Messages]] when the user has to choose one of two formulas in the case of `And` or `Or`.
    *
    * @param a
    *   The first conjunct / disjunct.
    * @param b
    *   The second conjunct / disjunct.
    * @param commit
    *   The player's commitment.
    * @return
    *   A list of messages to choose one of the two formulas.
    */
  private def chooseAndOr(a: FOLFormula, b: FOLFormula)(commit: Boolean): Messages =
    val msg1 = s"You believe one of these is $commit:"
    val msg2 = ui"$a or $b"
    val msg3 = s"Choose a $commit formula above."
    msg1 :: msg2 :: msg3 :: Nil

  /** Custom string interpolator to be used with `FOLFormula` in order to avoid `.toUntypedString` calls everywhere.
    *
    * @param args
    *   The arguments to be interpolated.
    * @return
    *   The interpolated combined string of the arguments.
    */
  extension (sc: StringContext) def ui(args: String*): String = sc.s(args*)

  /** This conversion enables us to utilize `.toUntypedString` in the [[ui]] custom interpolator. */
  given Conversion[FOLFormula, String] = _.toUntypedString
end Messager
