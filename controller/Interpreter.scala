package tarski
package controller

def eval(formula: FOLFormula)(using ng: NameGrid): Boolean = formula match
  case a: FOLAtom => evalAtom(a)
  case And(a, b)  => eval(a) && eval(b)
  case Or(a, b)   => eval(a) || eval(b)
  case Neg(a)     => !eval(a)
  case Imp(a, b)  => if eval(a) then eval(b) else true
  case Iff(a: FOLFormula, b: FOLFormula) =>
    val (ea, eb) = (eval(a), eval(b))
    ea && eb || !ea && !eb
  case All(x, f) => ng.keys.forall(name => eval(f.sub(x, FOLConst(name))))
  case Ex(x, f)  => ng.keys.exists(name => eval(f.sub(x, FOLConst(name))))

private def evalAtom(a: FOLAtom)(using ng: NameGrid): Boolean =
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
    case FOLAtom("Gray", Seq(FOLConst(c)))                           => ng(c).block.tone == Gray
    case FOLAtom("Left", Seq(FOLConst(c), FOLConst(d)))              => ng(c).pos.leftOf(ng(d).pos)
    case FOLAtom("Right", Seq(FOLConst(c), FOLConst(d)))             => ng(c).pos.rightOf(ng(d).pos)
    case FOLAtom("Below", Seq(FOLConst(c), FOLConst(d)))             => ng(c).pos.below(ng(d).pos)
    case FOLAtom("Above", Seq(FOLConst(c), FOLConst(d)))             => ng(c).pos.above(ng(d).pos)
    case FOLAtom("Adjoins", Seq(FOLConst(c), FOLConst(d)))           => ng(c).pos.adjoins(ng(d).pos)
    case FOLAtom("Smaller", Seq(FOLConst(c), FOLConst(d)))           => ng(c).block.smaller(ng(d).block)
    case FOLAtom("Larger", Seq(FOLConst(c), FOLConst(d)))            => ng(c).block.larger(ng(d).block)
    case FOLAtom("=", Seq(FOLConst(c), FOLConst(d)))                 => ng(c).block == ng(d).block
    case FOLAtom("SameRow", Seq(FOLConst(c), FOLConst(d)))           => ng(c).pos.sameRow(ng(d).pos)
    case FOLAtom("SameCol", Seq(FOLConst(c), FOLConst(d)))           => ng(c).pos.sameCol(ng(d).pos)
    case FOLAtom("SameSize", Seq(FOLConst(c), FOLConst(d)))          => ng(c).block.sameSize(ng(d).block)
    case FOLAtom("SameShape", Seq(FOLConst(c), FOLConst(d)))         => ng(c).block.sameShape(ng(d).block)
    case FOLAtom("SameTone", Seq(FOLConst(c), FOLConst(d)))          => ng(c).block.sameColor(ng(d).block)
    case FOLAtom("Betw", Seq(FOLConst(c), FOLConst(d), FOLConst(e))) => ng(c).pos.between(ng(d).pos, ng(e).pos)
    case _ => throw IllegalArgumentException(s"Atom $a is parsed incorrectly")

extension (f: FOLFormula) def sub(x: FOLVar, c: FOLConst) = FOLSubstitution((x, c)).apply(f)
