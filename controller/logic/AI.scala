package tarski
package controller

/** Makes choices and generates messages for the computer player in [[model.Game]]. */
object AI:
  import gapt.expr.formula.fol.{FOLVar, FOLAtom, FOLFormula}
  import model.{Panel, Name, Messages}
  import Util.*, Util.given

  /** Chooses the name of a block in the world that satisfies, or falsifies, the given formula by substituting the name
    * for the given variable.
    *
    * @param f
    *   A `FOLFormula`.
    * @param x
    *   A `FOLVar` that occurs freely in `f`.
    * @param pred
    *   Whether we want to satisfy or falsify `f`.
    * @param nm
    *   A [[model.Panel]] that represents the blocks on the board.
    * @return
    *   The [[model.Name]] of a block that makes `f` true or false when substituted.
    */
  def chooseBlock(f: FOLFormula, x: FOLVar)(pred: Boolean)(using panel: Panel): Name =
    import model.Formulas.sub
    panel.keys
      .map(name => name -> Interpreter.eval(f.sub(x, name)))
      .find(pred || !_._2) match
      case None            => panel.keys.head
      case Some((name, _)) => name

  /** Generates [[model.Messages]] for the computer's formula choices in case of `And` and `Or`.
    *
    * @param a
    *   The first conjunct / disjunct.
    * @param b
    *   The second conjunct / disjunct.
    * @param commit
    *   The player's commitment (the opposite of the computer's commitment).
    * @return
    *   A list of messages for the computer's choice from the two formulas.
    */
  def chooseAndOr(a: FOLFormula, b: FOLFormula)(commit: Boolean)(using Panel): Messages =
    val choice = if Interpreter.eval(a) != commit then a else b
    val msg1   = s"You believe both are $commit:"
    val msg2   = ui"$a and $b"
    val msg3   = ui"I choose $choice as ${!commit}."
    List(msg1, msg2, msg3)

  /** Generates [[model.Messages]] for the computer's formula choices in case of `All` and `Ex`.
    *
    * @param f
    *   A `FOLFormula`.
    * @param x
    *   A `FOLVar` that occurs freely in `f`.
    * @param commit
    *   The player's commitment (the opposite of the computer's commitment).
    * @return
    *   A list of messages for the computer's choice from the two formulas.
    */
  def chooseAllEx(f: FOLFormula, x: FOLVar)(commit: Boolean)(using Panel): Messages =
    val quantity = if commit then "every" else "no"
    val counter  = if commit then "counter" else ""
    val msg1     = s"You believe $quantity object [${x.name}] satisfies:"
    val msg2     = ui"$f"
    val choice   = AI.chooseBlock(f, x)(!commit)
    val msg3     = s"I choose $choice as my ${counter}example"
    List(msg1, msg2, msg3)
end AI
