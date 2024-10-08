package tarski

enum Tsize:
  case Small, Medium, Large
  def toDouble: Double = this match
    case Small  => SMALL
    case Medium => MEDIUM
    case Large  => LARGE

enum Tcolor:
  case Blue, Black, Gray
  def toColor: Color = this match
    case Blue  => blue
    case Black => black
    case Gray  => gray

enum Tshape:
  case Triangle, Square, Circle
  def toImage(size: Double, color: Color): Image = this match
    case Triangle => Image.equilateralTriangle(size).fillColor(color)
    case Square   => Image.square(size).fillColor(color)
    case Circle   => Image.circle(size).fillColor(color)

case class Block(tSize: Tsize, tShape: Tshape, tColor: Tcolor):
  def toImage: Image = tShape.toImage(tSize.toDouble, tColor.toColor)
