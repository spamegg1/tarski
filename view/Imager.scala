package tarski
package view

object Imager:
  type Obj = Block | FOLFormula | Result

  def apply(o: Obj)(using c: Constants): Image =
    o match
      case b: Block =>
        val shapeImg =
          import Shape.*
          b.shape match
            case Tri => Image.triangle(b.size, b.size).fillColor(b.color)
            case Squ => Image.square(b.size).fillColor(b.color)
            case Cir => Image.circle(b.size).fillColor(b.color)
        Text(b.label).font(c.TheFont).on(shapeImg)
      case f: FOLFormula => Text(f.toString).font(c.TheFont)
      case r: Result =>
        import Result.*
        r match
          case Ready   => Text("  ?").font(c.TheFont).strokeColor(Blue)
          case Valid   => Text("  T").font(c.TheFont).strokeColor(green)
          case Invalid => Text("  F").font(c.TheFont).strokeColor(red)

  def apply(opt: Option[Obj])(using Constants): Image = opt match
    case None      => Image.empty
    case Some(obj) => Imager(obj)
