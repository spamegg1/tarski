package tarski

enum Shape:
  case Tri, Squ, Cir
  def toImage(size: Double, color: Color): Image = this match
    case Tri => Image.equilateralTriangle(size * 1.15).fillColor(color)
    case Squ => Image.square(size).fillColor(color)
    case Cir => Image.circle(size).fillColor(color)
export Shape.*
