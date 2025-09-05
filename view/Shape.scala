package tarski

enum Shape:
  case Tri, Squ, Cir
  def toImage(size: Size, colour: Colour): Image = this match
    case Tri => Image.equilateralTriangle(size.value).fillColor(colour.value)
    case Squ => Image.square(size.value).fillColor(colour.value)
    case Cir => Image.circle(size.value).fillColor(colour.value)
export Shape.*
