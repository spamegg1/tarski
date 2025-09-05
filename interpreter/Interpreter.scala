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

private def evalAtom(a: FOLAtom)(using blocks: Blocks): Boolean = a match
  case FOLAtom("Small", List(FOLConst(c)))    => blocks(c).block.size == Small
  case FOLAtom("Medium", List(FOLConst(c)))   => blocks(c).block.size == Medium
  case FOLAtom("Large", List(FOLConst(c)))    => blocks(c).block.size == Large
  case FOLAtom("Circle", List(FOLConst(c)))   => blocks(c).block.shape == Cir
  case FOLAtom("Triangle", List(FOLConst(c))) => blocks(c).block.shape == Tri
  case FOLAtom("Square", List(FOLConst(c)))   => blocks(c).block.shape == Squ
  case FOLAtom("Blue", List(FOLConst(c)))     => blocks(c).block.colour == Blue
  case FOLAtom("Black", List(FOLConst(c)))    => blocks(c).block.colour == Black
  case FOLAtom("Gray", List(FOLConst(c)))     => blocks(c).block.colour == Gray
  case FOLAtom("LeftOf", List(FOLConst(c1), FOLConst(c2))) =>
    blocks(c1).pos.leftOf(blocks(c2).pos)
  case FOLAtom("RightOf", List(FOLConst(c1), FOLConst(c2))) =>
    blocks(c1).pos.rightOf(blocks(c2).pos)
  case FOLAtom("FrontOf", List(FOLConst(c1), FOLConst(c2))) =>
    blocks(c1).pos.frontOf(blocks(c2).pos)
  case FOLAtom("BackOf", List(FOLConst(c1), FOLConst(c2))) =>
    blocks(c1).pos.backOf(blocks(c2).pos)
  case FOLAtom("Adjoins", List(FOLConst(c1), FOLConst(c2))) =>
    blocks(c1).pos.adjoins(blocks(c2).pos)
  case FOLAtom("Smaller", List(FOLConst(c1), FOLConst(c2))) =>
    blocks(c1).block.smaller(blocks(c2).block)
  case FOLAtom("Larger", List(FOLConst(c1), FOLConst(c2))) =>
    blocks(c2).block.smaller(blocks(c1).block)
  case FOLAtom("=", List(FOLConst(c1), FOLConst(c2))) =>
    blocks(c1).block == blocks(c2).block
  case FOLAtom("SameSize", List(FOLConst(c1), FOLConst(c2))) =>
    blocks(c1).block.sameSize(blocks(c2).block)
  case FOLAtom("SameShape", List(FOLConst(c1), FOLConst(c2))) =>
    blocks(c1).block.sameShape(blocks(c2).block)
  case FOLAtom("SameColor", List(FOLConst(c1), FOLConst(c2))) =>
    blocks(c1).block.sameColour(blocks(c2).block)
  case FOLAtom("SameRow", List(FOLConst(c1), FOLConst(c2))) =>
    blocks(c1).pos.sameRow(blocks(c2).pos)
  case FOLAtom("SameColumn", List(FOLConst(c1), FOLConst(c2))) =>
    blocks(c1).pos.sameCol(blocks(c2).pos)
  case FOLAtom("Between", List(FOLConst(c1), FOLConst(c2), FOLConst(c3))) =>
    blocks(c1).pos.between(blocks(c2).pos, blocks(c3).pos)
  case _ => throw IllegalArgumentException(s"Atom $a is parsed incorrectly")

extension (f: FOLFormula)
  def substitute(x: FOLVar, c: FOLConst) = FOLSubstitution((x, c)).apply(f)
