package tarski
package view

extension (r: Render.type)(using Constants)
  def colorBox(color: Color, point: Point) = Image
    .rectangle(wid, hgh)
    .fillColor(color)
    .at(point)

  def blueBox  = colorBox(Blue, UI.bluePt)
  def greenBox = colorBox(Green, UI.greenPt)
  def grayBox  = colorBox(Gray, UI.grayPt)
  def colors   = blueBox on greenBox on grayBox

  def colorIndicator(color: Color) = color match
    case Blue  => indicator(UI.bluePt, 1)
    case Green => indicator(UI.greenPt, 1)
    case Gray  => indicator(UI.grayPt, 1)
    case _     => Image.empty

  def colorBoxes(colorOpt: Option[Color]) = colorOpt match
    case None        => colors
    case Some(color) => colorIndicator(color).on(colors)
