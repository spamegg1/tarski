package tarski

enum Shape:
  case Tri, Squ, Cir
  def toImage(size: Double, color: Color): Image = this match
    case Tri => Image.equilateralTriangle(size).fillColor(color)
    case Squ => Image.square(size).fillColor(color)
    case Cir => Image.circle(size).fillColor(color)

// colors are: deepSkyBlue, black, lightGray
// sizes are: SMALL, MEDIUM, LARGE
case class Block(
    size: Double,
    shape: Shape,
    color: Color,
    nameOpt: Option[Name] = None
):
  def nameless = shape.toImage(size, color)
  def toImage = nameOpt match
    case None       => nameless
    case Some(name) => Text(name.toString).font(TheFont).on(nameless)
