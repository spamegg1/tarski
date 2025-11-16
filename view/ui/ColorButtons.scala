package tarski
package view

case class ColorButtons(u: Utility)(using Constants):
  def colorBox(tone: Tone, point: Point) =
    Image
      .rectangle(u.wid, u.hgh)
      .fillColor(tone)
      .at(point)

  import Tone.*
  def blueBox  = colorBox(Blue, UI.bluePt)
  def greenBox = colorBox(Green, UI.greenPt)
  def grayBox  = colorBox(Gray, UI.grayPt)
  def colors   = blueBox on greenBox on grayBox

  def colorIndicator(tone: Tone) = tone match
    case Blue  => u.indicator(UI.bluePt, 1)
    case Green => u.indicator(UI.greenPt, 1)
    case Gray  => u.indicator(UI.grayPt, 1)

  def colorBoxes(colorOpt: Option[Tone]) = colorOpt match
    case None       => colors
    case Some(tone) => colorIndicator(tone).on(colors)
