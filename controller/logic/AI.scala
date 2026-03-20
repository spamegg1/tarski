package tarski
package controller

/** Makes various choices and generates messages for the computer player in the game. */
object AI:
  /** Chooses the name of a block in the world that satisfies, or falsifies, the given formula by substituting the name
    * for the given variable.
    *
    * @param f
    *   An `FOLFormula`.
    * @param x
    *   An `FOLVar` that occurs freely in `f`.
    * @param pred
    *   Whether we want to satisfy or falsify `f`.
    * @param nm
    *   A [[model.NameMap]] that represents the blocks on the board.
    * @return
    *   The [[model.Name]] of a block that makes `f` true or false when substituted.
    */
  def chooseBlock(f: FOLFormula, x: FOLVar)(pred: Boolean)(using nm: NameMap): Name =
    nm.keys
      .map(name => name -> Interpreter.eval(f.sub(x, name)))
      .find(pred || !_._2) match
      case None            => nm.keys.head
      case Some((name, _)) => name

  /** Generates messages for the computer's formula choices in case of `And` and `Or`.
    *
    * @param a
    *   The first conjunct / disjunct.
    * @param b
    *   The second conjunct / disjunct.
    * @param commit
    *   The computer's commitment (the negation of the player's commitment).
    * @return
    *   A list of messages for the computer's choice from the two formulas.
    */
  def chooseAndOr(a: FOLFormula, b: FOLFormula)(commit: Boolean)(using NameMap): Messages =
    val choice = if Interpreter.eval(a) == commit then a else b
    val msg1   = s"You believe both are ${!commit}:"
    val msg2   = ui"$a and $b"
    val msg3   = ui"I choose $choice as $commit."
    msg1 :: msg2 :: msg3 :: Nil
