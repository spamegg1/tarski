package tarski
package controller

/** Contains methods used in evaluation of first-order formulas in a given world. */
object Interpreter:
  import model.{Panel, Pos, Sizes, Tone, Shape}
  import gapt.expr.formula.fol.{FOLVar, FOLAtom, FOLFormula}
  import gapt.expr.formula.{All, And, Atom, Or, Neg, Ex, Imp, Iff}

  /** Evaluates first-order logic formulas accordip to the blocks on the board.
    *
    * It is recursive but without tailrec optimization or memoization. The computation is expected to be very simple, so
    * performance is not a priority.
    *
    * Can throw an `IllegalArgumentException` from its helper, [[evalAtom]].
    *
    * @param formula
    *   A first-order formula to be evaluated.
    * @param panel
    *   A map of named objects, and their blocks and positions.
    * @return
    *   true if the first-order formula holds in the given board and blocks, false otherwise.
    */
  def eval(formula: FOLFormula)(using panel: Panel): Boolean =
    import model.Formulas.sub

    formula match
      case a: FOLAtom => evalAtom(a, panel)
      case And(a, b)  =>
        val (ea, eb) = (eval(a), eval(b)) // make sure both parts parse OK
        ea && eb
      case Or(a, b) =>
        val (ea, eb) = (eval(a), eval(b)) // make sure both parts parse OK
        ea || eb
      case Neg(a)    => !eval(a)
      case Imp(a, b) =>
        val (ea, eb) = (eval(a), eval(b)) // make sure both parts parse OK
        if ea then eb else true
      case Iff(a: FOLFormula, b: FOLFormula) =>
        val (ea, eb) = (eval(a), eval(b)) // make sure both parts parse OK
        ea == eb
      case All(x, f) => panel.keys.forall(name => eval(f.sub(x, name)))
      case Ex(x, f)  => panel.keys.exists(name => eval(f.sub(x, name)))
  end eval

  /** Helper to evaluate atomic formulas in a given world.
    *
    * Atomic formulas are restricted to the followip predicate symbols:
    *
    * Unary: `Sml`, `Mid`, `Big`, `Tri`, `Sqr`, `Cir`, `Blu`, `Lim`, `Red`
    *
    * Binary: `Lft`, `Rgt`, `Bel`, `Abv`, `Adj`, `Les`, `Mor`, `=`, `Eq`, `Siz`, `Shp`, `Ton`, `Row`, `Col`
    *
    * Ternary: `Btw`
    *
    * Throws an `IllegalArgumentException` if any other predicate symbol is used.
    *
    * Look-ups of named objects in the input map are performed without the safety of `.get`, which can result in
    * `java.util.NoSuchElementException`; but this is caught in [[Handler.handleEval]] in the outer boundary. The reason
    * for this design is to leave a formula unevaluated if it refers to a named object that does not exist.
    *
    * @param a
    *   An atomic formula to be evaluated.
    * @param p
    *   A map of named objects, and their blocks and positions.
    * @return
    *   true if the atomic formula holds in the given board and blocks, false otherwise.
    */
  private def evalAtom(a: FOLAtom, p: Panel): Boolean =
    import Pos.*, Sizes.*, Tone.*, Shape.*
    import gapt.expr.formula.fol.{FOLConst => FC, FOLAtom => FA}
    a match
      case FA("Sml", Seq(FC(c)))               => p(c).block.size == Sml
      case FA("Mid", Seq(FC(c)))               => p(c).block.size == Mid
      case FA("Big", Seq(FC(c)))               => p(c).block.size == Big
      case FA("Cir", Seq(FC(c)))               => p(c).block.shape == Cir
      case FA("Tri", Seq(FC(c)))               => p(c).block.shape == Tri
      case FA("Sqr", Seq(FC(c)))               => p(c).block.shape == Sqr
      case FA("Blu", Seq(FC(c)))               => p(c).block.tone == Blu
      case FA("Lim", Seq(FC(c)))               => p(c).block.tone == Lim
      case FA("Red", Seq(FC(c)))               => p(c).block.tone == Red
      case FA("Lft", Seq(FC(c), FC(d)))        => p(c).pos.leftOf(p(d).pos)
      case FA("Rgt", Seq(FC(c), FC(d)))        => p(c).pos.rightOf(p(d).pos)
      case FA("Bel", Seq(FC(c), FC(d)))        => p(c).pos.below(p(d).pos)
      case FA("Abv", Seq(FC(c), FC(d)))        => p(c).pos.above(p(d).pos)
      case FA("Adj", Seq(FC(c), FC(d)))        => p(c).pos.adjoins(p(d).pos)
      case FA("Les", Seq(FC(c), FC(d)))        => p(c).block.size < p(d).block.size
      case FA("Mor", Seq(FC(c), FC(d)))        => p(d).block.size < p(c).block.size
      case FA("=", Seq(FC(c), FC(d)))          => p(c).pos == p(d).pos
      case FA("Row", Seq(FC(c), FC(d)))        => p(c).pos.sameRow(p(d).pos)
      case FA("Col", Seq(FC(c), FC(d)))        => p(c).pos.sameCol(p(d).pos)
      case FA("Eq", Seq(FC(c), FC(d)))         => p(c).block.removeLabel == p(d).block.removeLabel
      case FA("Siz", Seq(FC(c), FC(d)))        => p(c).block.size == p(d).block.size
      case FA("Shp", Seq(FC(c), FC(d)))        => p(c).block.shape == p(d).block.shape
      case FA("Ton", Seq(FC(c), FC(d)))        => p(c).block.tone == p(d).block.tone
      case FA("Btw", Seq(FC(c), FC(d), FC(e))) => p(c).pos.between(p(d).pos, p(e).pos)
      case _                                   => throw IllegalArgumentException(s"Atom $a is parsed incorrectly")
  end evalAtom
end Interpreter
