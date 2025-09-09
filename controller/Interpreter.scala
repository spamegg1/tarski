package tarski

def eval(formula: FOLFormula)(using blocks: Blocks): Boolean = formula match
  case a: FOLAtom => evalAtom(a)
  case And(a, b)  => eval(a) && eval(b)
  case Or(a, b)   => eval(a) || eval(b)
  case Neg(a)     => !eval(a)
  case Imp(a, b)  => if eval(a) then eval(b) else true
  case Iff(a: FOLFormula, b: FOLFormula) =>
    val (ea, eb) = (eval(a), eval(b))
    ea && eb || !ea && !eb
  case All(x, f) => blocks.keys.forall(name => eval(f.substitute(x, FOLConst(name))))
  case Ex(x, f)  => blocks.keys.exists(name => eval(f.substitute(x, FOLConst(name))))

private def evalAtom(a: FOLAtom)(using b: Blocks): Boolean = a match
  case FOLAtom("Small", Seq(FOLConst(c)))                => b(c).block.size == Small
  case FOLAtom("Medium", Seq(FOLConst(c)))               => b(c).block.size == Medium
  case FOLAtom("Large", Seq(FOLConst(c)))                => b(c).block.size == Large
  case FOLAtom("Circle", Seq(FOLConst(c)))               => b(c).block.shape == Cir
  case FOLAtom("Triangle", Seq(FOLConst(c)))             => b(c).block.shape == Tri
  case FOLAtom("Square", Seq(FOLConst(c)))               => b(c).block.shape == Squ
  case FOLAtom("Blue", Seq(FOLConst(c)))                 => b(c).block.color == Blue
  case FOLAtom("Green", Seq(FOLConst(c)))                => b(c).block.color == Green
  case FOLAtom("Gray", Seq(FOLConst(c)))                 => b(c).block.color == Gray
  case FOLAtom("LeftOf", Seq(FOLConst(c), FOLConst(d)))  => b(c).pos.leftOf(b(d).pos)
  case FOLAtom("RightOf", Seq(FOLConst(c), FOLConst(d))) => b(c).pos.rightOf(b(d).pos)
  case FOLAtom("FrontOf", Seq(FOLConst(c), FOLConst(d))) => b(c).pos.frontOf(b(d).pos)
  case FOLAtom("BackOf", Seq(FOLConst(c), FOLConst(d)))  => b(c).pos.backOf(b(d).pos)
  case FOLAtom("Adjoins", Seq(FOLConst(c), FOLConst(d))) => b(c).pos.adjoins(b(d).pos)
  case FOLAtom("Smaller", Seq(FOLConst(c), FOLConst(d))) => b(c).block.smaller(b(d).block)
  case FOLAtom("Larger", Seq(FOLConst(c), FOLConst(d)))  => b(c).block.larger(b(d).block)
  case FOLAtom("=", Seq(FOLConst(c), FOLConst(d)))       => b(c).block == b(d).block
  case FOLAtom("SameRow", Seq(FOLConst(c), FOLConst(d))) => b(c).pos.sameRow(b(d).pos)
  case FOLAtom("SameColumn", Seq(FOLConst(c), FOLConst(d))) => b(c).pos.sameCol(b(d).pos)
  case FOLAtom("SameSize", Seq(FOLConst(c), FOLConst(d))) =>
    b(c).block.sameSize(b(d).block)
  case FOLAtom("SameShape", Seq(FOLConst(c), FOLConst(d))) =>
    b(c).block.sameShape(b(d).block)
  case FOLAtom("SameColor", Seq(FOLConst(c), FOLConst(d))) =>
    b(c).block.sameColor(b(d).block)
  case FOLAtom("Between", Seq(FOLConst(c), FOLConst(d), FOLConst(c3))) =>
    b(c).pos.between(b(d).pos, b(c3).pos)
  case _ => throw IllegalArgumentException(s"Atom $a is parsed incorrectly")

extension (f: FOLFormula)
  def substitute(x: FOLVar, c: FOLConst) = FOLSubstitution((x, c)).apply(f)
