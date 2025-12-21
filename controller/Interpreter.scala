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
    case a: FOLAtom                        => evalAtom(a)
    case And(a, b)                         => eval(a) && eval(b)
    case Or(a, b)                          => eval(a) || eval(b)
    case Neg(a)                            => !eval(a)
    case Imp(a, b)                         => if eval(a) then eval(b) else true
    case Iff(a: FOLFormula, b: FOLFormula) => eval(a) iff eval(b)
    case All(x, f)                         => ng.keys.forall(name => eval(f.sub(x, name)))
    case Ex(x, f)                          => ng.keys.exists(name => eval(f.sub(x, name)))

  /** Helper to evaluate atomic formulas in a given world.
    *
    * Atomic formulas are restricted to the following predicate symbols:
    *
    * Unary: `Sml`, `Mid`, `Big`, `Tri`, `Sqr`, `Cir`, `Blu`, `Lim`, `Red`
    *
    * Binary: `Left`, `Rgt`, `Bel`, `Abv`, `Adj`, `Less`, `More`, `=`, `Size`, `Shap`, `Tone`, `Row`, `Col`
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
      case FOLAtom("Sml", Seq(FOLConst(c)))                           => ng(c).block.size == Sml
      case FOLAtom("Mid", Seq(FOLConst(c)))                           => ng(c).block.size == Mid
      case FOLAtom("Big", Seq(FOLConst(c)))                           => ng(c).block.size == Big
      case FOLAtom("Cir", Seq(FOLConst(c)))                           => ng(c).block.shape == Cir
      case FOLAtom("Tri", Seq(FOLConst(c)))                           => ng(c).block.shape == Tri
      case FOLAtom("Sqr", Seq(FOLConst(c)))                           => ng(c).block.shape == Sqr
      case FOLAtom("Blu", Seq(FOLConst(c)))                           => ng(c).block.tone == Blu
      case FOLAtom("Lim", Seq(FOLConst(c)))                           => ng(c).block.tone == Lim
      case FOLAtom("Red", Seq(FOLConst(c)))                           => ng(c).block.tone == Red
      case FOLAtom("Left", Seq(FOLConst(c), FOLConst(d)))             => ng(c).pos.leftOf(ng(d).pos)
      case FOLAtom("Rgt", Seq(FOLConst(c), FOLConst(d)))              => ng(c).pos.rightOf(ng(d).pos)
      case FOLAtom("Bel", Seq(FOLConst(c), FOLConst(d)))              => ng(c).pos.below(ng(d).pos)
      case FOLAtom("Abv", Seq(FOLConst(c), FOLConst(d)))              => ng(c).pos.above(ng(d).pos)
      case FOLAtom("Adj", Seq(FOLConst(c), FOLConst(d)))              => ng(c).pos.adjoins(ng(d).pos)
      case FOLAtom("Less", Seq(FOLConst(c), FOLConst(d)))             => ng(c).block.smaller(ng(d).block)
      case FOLAtom("More", Seq(FOLConst(c), FOLConst(d)))             => ng(c).block.larger(ng(d).block)
      case FOLAtom("=", Seq(FOLConst(c), FOLConst(d)))                => ng(c).block == ng(d).block
      case FOLAtom("Row", Seq(FOLConst(c), FOLConst(d)))              => ng(c).pos.sameRow(ng(d).pos)
      case FOLAtom("Col", Seq(FOLConst(c), FOLConst(d)))              => ng(c).pos.sameCol(ng(d).pos)
      case FOLAtom("Size", Seq(FOLConst(c), FOLConst(d)))             => ng(c).block.sameSize(ng(d).block)
      case FOLAtom("Shap", Seq(FOLConst(c), FOLConst(d)))             => ng(c).block.sameShape(ng(d).block)
      case FOLAtom("Tone", Seq(FOLConst(c), FOLConst(d)))             => ng(c).block.sameColor(ng(d).block)
      case FOLAtom("Btw", Seq(FOLConst(c), FOLConst(d), FOLConst(e))) => ng(c).pos.between(ng(d).pos, ng(e).pos)
      case _ => throw IllegalArgumentException(s"Atom $a is parsed incorrectly")
  end evalAtom

  extension (bool: Boolean)
    /** Extension method for the biconditional connective.
      *
      * @param that
      *   Another [[Boolean]]
      * @return
      *   true if both booleans are true or both are false, false otherwise.
      */
    infix def iff(that: Boolean) = bool && that || !bool && !that
end Interpreter

extension (f: FOLFormula)
  /** Extension method to substitute a [[Name]] into a [[FOLFormula]] for all free occurrences of a variable. Used in
    * [[eval]].
    *
    * @param x
    *   A first-order variable
    * @param c
    *   A [[Name]] to be used as a first-order constant
    * @return
    *   the formula, with all free occurrences of `x` replaced by `c`.
    */
  def sub(x: FOLVar, c: Name) = FOLSubstitution((x, FOLConst(c))).apply(f)
