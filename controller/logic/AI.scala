package tarski
package controller

/** Makes the block choices of the computer player in the game. */
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
      .find(pred | !_._2) match
      case None            => nm.keys.head
      case Some((name, _)) => name
