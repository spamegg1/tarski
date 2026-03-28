package tarski
package controller

/** Contains methods used in evaluation of first-order formulas in a given world. */
object Interpreter:
  /** Evaluates first-order logic formulas according to the blocks on the board.
    *
    * It is recursive but without tailrec optimization or memoization. The computation is expected to be very simple, so
    * performance is not a priority.
    *
    * Can throw an `IllegalArgumentException` from its helper, [[evalAtom]].
    *
    * @param formula
    *   A first-order formula to be evaluated.
    * @param ng
    *   A map of named objects, and their blocks and positions.
    * @return
    *   true if the first-order formula holds in the given board and blocks, false otherwise.
    */
  def eval(formula: FOLFormula)(using ng: NameMap): Boolean = formula match
    case a: FOLAtom => evalAtom(a)
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
    case All(x, f) => ng.keys.forall(name => eval(f.sub(x, name)))
    case Ex(x, f)  => ng.keys.exists(name => eval(f.sub(x, name)))

  /** Helper to evaluate atomic formulas in a given world.
    *
    * Atomic formulas are restricted to the following predicate symbols:
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
    * @param ng
    *   A map of named objects, and their blocks and positions.
    * @return
    *   true if the atomic formula holds in the given board and blocks, false otherwise.
    */
  private def evalAtom(a: FOLAtom)(using ng: NameMap): Boolean =
    import Pos.*, Sizes.*, Tone.*, Shape.*
    a match
      case FOLAtom("Sml", Seq(FOLConst(c)))              => ng(c).block.size == Sml
      case FOLAtom("Mid", Seq(FOLConst(c)))              => ng(c).block.size == Mid
      case FOLAtom("Big", Seq(FOLConst(c)))              => ng(c).block.size == Big
      case FOLAtom("Cir", Seq(FOLConst(c)))              => ng(c).block.shape == Cir
      case FOLAtom("Tri", Seq(FOLConst(c)))              => ng(c).block.shape == Tri
      case FOLAtom("Sqr", Seq(FOLConst(c)))              => ng(c).block.shape == Sqr
      case FOLAtom("Blu", Seq(FOLConst(c)))              => ng(c).block.tone == Blu
      case FOLAtom("Lim", Seq(FOLConst(c)))              => ng(c).block.tone == Lim
      case FOLAtom("Red", Seq(FOLConst(c)))              => ng(c).block.tone == Red
      case FOLAtom("Lft", Seq(FOLConst(c), FOLConst(d))) => ng(c).pos.leftOf(ng(d).pos)
      case FOLAtom("Rgt", Seq(FOLConst(c), FOLConst(d))) => ng(c).pos.rightOf(ng(d).pos)
      case FOLAtom("Bel", Seq(FOLConst(c), FOLConst(d))) => ng(c).pos.below(ng(d).pos)
      case FOLAtom("Abv", Seq(FOLConst(c), FOLConst(d))) => ng(c).pos.above(ng(d).pos)
      case FOLAtom("Adj", Seq(FOLConst(c), FOLConst(d))) => ng(c).pos.adjoins(ng(d).pos)
      case FOLAtom("Les", Seq(FOLConst(c), FOLConst(d))) => ng(c).block.size < ng(d).block.size
      case FOLAtom("Mor", Seq(FOLConst(c), FOLConst(d))) => ng(d).block.size < ng(c).block.size
      case FOLAtom("=", Seq(FOLConst(c), FOLConst(d)))   => ng(c).pos == ng(d).pos
      case FOLAtom("Row", Seq(FOLConst(c), FOLConst(d))) => ng(c).pos.sameRow(ng(d).pos)
      case FOLAtom("Col", Seq(FOLConst(c), FOLConst(d))) => ng(c).pos.sameCol(ng(d).pos)
      case FOLAtom("Eq", Seq(FOLConst(c), FOLConst(d)))  => ng(c).block.removeLabel == ng(d).block.removeLabel
      case FOLAtom("Siz", Seq(FOLConst(c), FOLConst(d))) => ng(c).block.size == ng(d).block.size
      case FOLAtom("Shp", Seq(FOLConst(c), FOLConst(d))) => ng(c).block.shape == ng(d).block.shape
      case FOLAtom("Ton", Seq(FOLConst(c), FOLConst(d))) => ng(c).block.tone == ng(d).block.tone
      case FOLAtom("Btw", Seq(FOLConst(c), FOLConst(d), FOLConst(e))) => ng(c).pos.between(ng(d).pos, ng(e).pos)
      case _ => throw IllegalArgumentException(s"Atom $a is parsed incorrectly")
  end evalAtom
end Interpreter
