package tarski
package view

/** Buttons to select sizes or to change the size of a selected block.
  *
  * @param util
  *   An instance of the [[Utility]] class needed for buttons.
  * @param ui
  *   An instance of the [[UI]] class needed for button positions.
  */
case class SizeButtons(util: Utility, ui: UI):
  /** Button for the small size. */
  private val smallButton = util.button("Sml", ui.smallPt, 2)

  /** Button for the medium size. */
  private val mediumButton = util.button("Mid", ui.midPt, 2)

  /** Button for the large size. */
  private val largeButton = util.button("Big", ui.largePt, 2)

  /** All 3 size buttons together. */
  private val sizeButtons = smallButton on mediumButton on largeButton

  /** Draws a red indicator rectangle around a size button, if it is selected / clicked.
    *
    * @param size
    *   Size value of the button that's clicked / selected.
    * @return
    *   A red-edged, empty rectangle that fits around the size button.
    */
  private def sizeIndicator(size: Sizes) = size match
    case Sizes.Sml => util.indicator(ui.smallPt, 2)
    case Sizes.Mid => util.indicator(ui.midPt, 2)
    case Sizes.Big => util.indicator(ui.largePt, 2)

  /** Draws all three size buttons together, with indicator.
    *
    * @param sizeOpt
    *   An optional size, in case one is selected in the user interface. This normally comes from the [[Controls]]
    *   instance of a [[World]].
    * @return
    *   An image of the three size buttons, along with the red indicator rectangle for the one that is selected.
    */
  def sizes(sizeOpt: Option[Sizes]) = sizeOpt match
    case None       => sizeButtons
    case Some(size) => sizeIndicator(size).on(sizeButtons)
