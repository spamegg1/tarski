package tarski
package view

/** Buttons to select sizes or to change the size of a selected block.
  *
  * @param c
  *   A given instance of [[Constants]], needed to derive [[Utility]] and [[UI]] instances.
  */
class SizeButtons(using c: Constants):
  /** An instance of [[Utility]] summoned here to draw buttons and indicators. */
  private val util = summon[Utility]

  /** An instance of [[UI]] summoned here to calculate positions of the size buttons. */
  private val ui = summon[UI]

  /** Button for the Small size. */
  private val smallButton = util.button("Small", ui.smallPt, 2)

  /** Button for the Mid size. */
  private val mediumButton = util.button("Mid", ui.midPt, 2)

  /** Button for the Large size. */
  private val largeButton = util.button("Large", ui.largePt, 2)

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
    case Sizes.Small => util.indicator(ui.smallPt, 2)
    case Sizes.Mid   => util.indicator(ui.midPt, 2)
    case Sizes.Large => util.indicator(ui.largePt, 2)

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
