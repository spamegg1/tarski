package tarski
package view

/** Buttons to select tones or to change the tone of a selected block.
  *
  * @param c
  *   A given instance of [[Constants]], needed to derive [[Utility]] and [[UI]] instances.
  */
class ColorButtons(using c: Constants):
  /** An instance of [[Utility]] summned here for width and height calculations. */
  private val util = summon[Utility]

  /** An instance of [[UI]] summoned here to calculate positions of the color buttons. */
  private val ui = summon[UI]

  /** Creates a single color button image.
    *
    * @param tone
    *   The tone we want for this button.
    * @param point
    *   The point where we want to display this button.
    * @return
    *   A rectangle image of the color button at the given point, with the given tone.
    */
  private def colorBox(tone: Tone, point: Point) =
    Image
      .rectangle(util.wid, util.hgh)
      .fillColor(tone)
      .at(point)

  import Tone.*

  /** Button for the blue tone. */
  private val blueBox = colorBox(Blue, ui.bluePt)

  /** Button for the green tone. */
  private val greenBox = colorBox(Green, ui.greenPt)

  /** Button for the gray tone. */
  private val grayBox = colorBox(Gray, ui.grayPt)

  /** All three color buttons together. */
  private val colors = blueBox on greenBox on grayBox

  /** Draws a red indicator rectangle around a color button, if it is selected / clicked.
    *
    * @param tone
    *   Tone of the button that's clicked / selected.
    * @return
    *   A red-edged, empty rectangle that fits around the color button.
    */
  private def colorIndicator(tone: Tone) = tone match
    case Blue  => util.indicator(ui.bluePt, 1)
    case Green => util.indicator(ui.greenPt, 1)
    case Gray  => util.indicator(ui.grayPt, 1)

  /** Draws all three color buttons together, with indicator.
    *
    * @param colorOpt
    *   An optional tone, in case one is selected in the user interface. This normally comes from the [[Controls]]
    *   instance of a [[World]].
    * @return
    *   An image of the three color buttons, along with the red indicator rectangle for the one that is selected.
    */
  def colorBoxes(colorOpt: Option[Tone]) = colorOpt match
    case None       => colors
    case Some(tone) => colorIndicator(tone).on(colors)
