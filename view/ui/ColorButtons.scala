package tarski
package view

case class ColorButtons(u: Utility)(using Constants):
  def colorBox(color: Color, point: Point) =
    Image
      .rectangle(u.wid, u.hgh)
      .fillColor(color)
      .at(point)

  def blueBox  = colorBox(Blue, UI.bluePt)
  def greenBox = colorBox(Green, UI.greenPt)
  def grayBox  = colorBox(Gray, UI.grayPt)
  def colors   = blueBox on greenBox on grayBox

  def colorIndicator(color: Color) = color match
    case Blue  => u.indicator(UI.bluePt, 1)
    case Green => u.indicator(UI.greenPt, 1)
    case Gray  => u.indicator(UI.grayPt, 1)
    case _     => Image.empty

  def colorBoxes(colorOpt: Option[Color]) = colorOpt match
    case None        => colors
    case Some(color) => colorIndicator(color).on(colors)
