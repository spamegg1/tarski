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
    case Iff(a: FOLFormula, b: FOLFormula) =>
      val (ea, eb) = (eval(a), eval(b))
      ea && eb || !ea && !eb
    case All(x, f) => ng.keys.forall(name => eval(f.sub(x, FOLConst(name))))
    case Ex(x, f)  => ng.keys.exists(name => eval(f.sub(x, FOLConst(name))))

  /** Helper to evaluate atomic formulas in a given world.
    *
    * Atomic formulas are restricted to the following predicate symbols:
    *
    * Unary: `Small`, `Mid`, `Large`, `Tri`, `Squ`, `Cir`, `Blue`, `Green`, `Coral`
    *
    * Binary: `Left`, `Right`, `Below`, `Above`, `Adj`, `Less`, `More`, `=`, `Size`, `Shape`, `Tone`, `Row`, `Col`
    *
    * Ternary: `Betw`
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
      case FOLAtom("Small", Seq(FOLConst(c)))                          => ng(c).block.size == Small
      case FOLAtom("Mid", Seq(FOLConst(c)))                            => ng(c).block.size == Mid
      case FOLAtom("Large", Seq(FOLConst(c)))                          => ng(c).block.size == Large
      case FOLAtom("Cir", Seq(FOLConst(c)))                            => ng(c).block.shape == Cir
      case FOLAtom("Tri", Seq(FOLConst(c)))                            => ng(c).block.shape == Tri
      case FOLAtom("Squ", Seq(FOLConst(c)))                            => ng(c).block.shape == Squ
      case FOLAtom("Blue", Seq(FOLConst(c)))                           => ng(c).block.tone == Blue
      case FOLAtom("Green", Seq(FOLConst(c)))                          => ng(c).block.tone == Green
      case FOLAtom("Coral", Seq(FOLConst(c)))                          => ng(c).block.tone == Coral
      case FOLAtom("Left", Seq(FOLConst(c), FOLConst(d)))              => ng(c).pos.leftOf(ng(d).pos)
      case FOLAtom("Right", Seq(FOLConst(c), FOLConst(d)))             => ng(c).pos.rightOf(ng(d).pos)
      case FOLAtom("Below", Seq(FOLConst(c), FOLConst(d)))             => ng(c).pos.below(ng(d).pos)
      case FOLAtom("Above", Seq(FOLConst(c), FOLConst(d)))             => ng(c).pos.above(ng(d).pos)
      case FOLAtom("Adj", Seq(FOLConst(c), FOLConst(d)))               => ng(c).pos.adjoins(ng(d).pos)
      case FOLAtom("Less", Seq(FOLConst(c), FOLConst(d)))              => ng(c).block.smaller(ng(d).block)
      case FOLAtom("More", Seq(FOLConst(c), FOLConst(d)))              => ng(c).block.larger(ng(d).block)
      case FOLAtom("=", Seq(FOLConst(c), FOLConst(d)))                 => ng(c).block == ng(d).block
      case FOLAtom("Row", Seq(FOLConst(c), FOLConst(d)))               => ng(c).pos.sameRow(ng(d).pos)
      case FOLAtom("Col", Seq(FOLConst(c), FOLConst(d)))               => ng(c).pos.sameCol(ng(d).pos)
      case FOLAtom("Size", Seq(FOLConst(c), FOLConst(d)))              => ng(c).block.sameSize(ng(d).block)
      case FOLAtom("Shape", Seq(FOLConst(c), FOLConst(d)))             => ng(c).block.sameShape(ng(d).block)
      case FOLAtom("Tone", Seq(FOLConst(c), FOLConst(d)))              => ng(c).block.sameColor(ng(d).block)
      case FOLAtom("Betw", Seq(FOLConst(c), FOLConst(d), FOLConst(e))) => ng(c).pos.between(ng(d).pos, ng(e).pos)
      case _ => throw IllegalArgumentException(s"Atom $a is parsed incorrectly")

  extension (f: FOLFormula)
    /** Extension method to substitute a [[FOLConst]] into a [[FOLFormula]] for all free occurrences of a variable. Used
      * in [[eval]].
      *
      * @param x
      *   A first-order variable
      * @param c
      *   A first-order constant
      * @return
      *   the formula, with all free occurrences of `x` replaced by `c`.
      */
    def sub(x: FOLVar, c: FOLConst) = FOLSubstitution((x, c)).apply(f)
