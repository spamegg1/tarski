package tarski
package controller

/** Generates messages for the game by handling the computer player's choices. Uses [[Interpreter]]. */
object Messager:
  type FF = FOLFormula

  /** Generates messages to be displayed to the user about the current state of the game.
    *
    * @param play
    *   The current state of play.
    * @param _
    *   A [[model.NameMap]] to look up blocks and to evaluate formulas with the interpreter.
    * @return
    *   A list of [[model.Messages]].
    */
  def show(play: Play)(using NameMap): Messages =
    (play.commitment, play.formula) match
      // symmetric cases
      case (Some(false), And(a, b)) => chooseAndOr(a, b)(false)
      case (Some(true), Or(a, b))   => chooseAndOr(a, b)(true)
      case (Some(false), All(x, f)) => chooseAllEx(f, x)(false)
      case (Some(true), Ex(x, f))   => chooseAllEx(f, x)(true)
      case (_, Imp(a, b))           => rewrite(play.formula, Or(Neg(a), b))
      case (_, Iff(a: FF, b: FF))   => rewrite(play.formula, And(Imp(a, b), Imp(b, a)))
      case (Some(true), And(a, b))  => AI.chooseAndOr(a, b)(true)
      case (Some(false), Or(a, b))  => AI.chooseAndOr(a, b)(false)
      case (Some(true), All(x, f))  => AI.chooseAllEx(f, x)(true)
      case (Some(false), Ex(x, f))  => AI.chooseAllEx(f, x)(false)

      // outlier cases
      case (Some(commit), Neg(a)) =>
        val msg1 = s"You believe this is $commit:"
        val msg2 = ui"${Neg(a)}"
        val msg3 = s"So you believe this is ${!commit}:"
        val msg4 = ui"$a"
        List(msg1, msg2, msg3, msg4)

      case (Some(commit), a: FOLAtom) =>
        val result = Interpreter.eval(a)
        val msg1   = if commit == result then "You win!" else "You lose."
        val msg2   = ui"$a is $result in this world."
        List(msg1, msg2)

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
    List(msg1, msg2, msg3)

  /** Generates [[model.Messages]] when the user has to choose a block in the case of a false `All` or true `Ex`.
    *
    * @param f
    *   A formula that the user is considering, `All` or `Ex`.
    * @param x
    *   A variable that occurs free in `f`.
    * @param commit
    *   The player's commitment.
    * @return
    *   A list of messages to choose a block.
    */
  private def chooseAllEx(f: FOLFormula, x: FOLVar)(commit: Boolean): Messages =
    val action = if commit then "satisfies" else "falsifies"
    val msg    = s"You believe some object [${x.name}] $action:"
    List(msg, ui"$f", "Click on a block, then click OK")

  /** Generates [[model.Messages]] for rewriting an `Iff` or `Imp` formula.
    *
    * @param f
    *   The formula to be rewritten (`Iff` or `Imp`).
    * @param g
    *   The rewritten form of `f` (`And(_, _)` or `Or(_, _)`).
    * @return
    *   A list of messages to rewrite the formula.
    */
  private def rewrite(f: FOLFormula, g: FOLFormula): Messages =
    List(ui"$f can be written as:", ui"$g")

end Messager

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

/** This conversion lets us mix `FOLFormula` and other types in the [[ui]] custom interpolator. */
given Conversion[AnyVal, String] = _.toString
