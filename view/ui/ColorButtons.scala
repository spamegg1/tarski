package tarski
package view

class ColorButtons(using c: Constants):
  private val util = summon[Utility]
  private val ui   = summon[UI]

  private def colorBox(tone: Tone, point: Point) =
    Image
      .rectangle(util.wid, util.hgh)
      .fillColor(tone)
      .at(point)

  import Tone.*
  private val blueBox  = colorBox(Blue, ui.bluePt)
  private val greenBox = colorBox(Green, ui.greenPt)
  private val grayBox  = colorBox(Gray, ui.grayPt)
  private val colors   = blueBox on greenBox on grayBox

  private def colorIndicator(tone: Tone) = tone match
    case Blue  => util.indicator(ui.bluePt, 1)
    case Green => util.indicator(ui.greenPt, 1)
    case Gray  => util.indicator(ui.grayPt, 1)

  def colorBoxes(colorOpt: Option[Tone]) = colorOpt match
    case None       => colors
    case Some(tone) => colorIndicator(tone).on(colors)
