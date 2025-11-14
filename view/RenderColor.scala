package tarski
package view

object RenderColor

extension (rc: RenderColor.type)(using Constants)
  def colorBox(color: Color, point: Point) =
    Image.rectangle(wid, hgh).fillColor(color).at(point)

  def renderBlue  = colorBox(Blue, UI.bluePt)
  def renderGreen = colorBox(Green, UI.greenPt)
  def renderGray  = colorBox(Gray, UI.grayPt)
  def renderColor = renderBlue on renderGreen on renderGray

  def colorIndicator(color: Color) = color match
    case Blue  => renderIndicator(UI.bluePt, 1)
    case Green => renderIndicator(UI.greenPt, 1)
    case Gray  => renderIndicator(UI.grayPt, 1)
    case _     => Image.empty

  def apply(colorOpt: Option[Color]) = colorOpt match
    case None        => renderColor
    case Some(color) => colorIndicator(color).on(renderColor)
