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
    case BlueB  => deepSkyBlue
    case BlackB => black
    case GrayB  => lightGray

enum BlockShape:
  case TriangleB, SquareB, CircleB
  def toImage(size: Double, color: Color): Image = this match
    case TriangleB => Image.equilateralTriangle(size).fillColor(color)
    case SquareB   => Image.square(size).fillColor(color)
    case CircleB   => Image.circle(size).fillColor(color)

case class Block(
    size: BlockSize,
    shape: BlockShape,
    color: BlockColor,
    nameOpt: Option[Name] = None
):
  def nameless = shape.toImage(size.toImageSize, color.toImageColor)
  def toImage = nameOpt match
    case None       => nameless
    case Some(name) => Text(name.toString).font(FONT).on(nameless)
