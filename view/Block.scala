package tarski

enum BlockSize:
  case SmallB, MediumB, LargeB
  def toImageSize: Double = this match
    case SmallB  => SMALL
    case MediumB => MEDIUM
    case LargeB  => LARGE

enum BlockColor:
  case BlueB, BlackB, GrayB
  def toImageColor: Color = this match
    case BlueB  => blue
    case BlackB => black
    case GrayB  => gray

enum BlockShape:
  case TriangleB, SquareB, CircleB
  def toImage(size: Double, color: Color): Image = this match
    case TriangleB => Image.equilateralTriangle(size).fillColor(color)
    case SquareB   => Image.square(size).fillColor(color)
    case CircleB   => Image.circle(size).fillColor(color)

case class Block(size: BlockSize, shape: BlockShape, color: BlockColor):
  def toImage: Image = shape.toImage(size.toImageSize, color.toImageColor)
