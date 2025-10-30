package tarski
package view

object Imager:
  import Shape.*, Result.*
  type Obj = Block | FOLFormula | Result

  def apply(o: Obj): Image = o match
    case b: Block =>
      val shapeImg = b.shape match
        case Tri => Image.triangle(b.size, b.size).fillColor(b.color)
        case Squ => Image.square(b.size).fillColor(b.color)
        case Cir => Image.circle(b.size).fillColor(b.color)
      Text(b.label).font(TheFont).on(shapeImg)
    case f: FOLFormula => Text(f.toString).font(TheFont)
    case r: Result =>
      r match
        case Ready   => ReadyMark
        case Valid   => CheckMark
        case Invalid => CrossMark

  def apply(opt: Option[Obj]): Image = opt match
    case None      => Image.empty
    case Some(obj) => Imager(obj)
