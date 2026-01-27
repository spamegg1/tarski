package tarski
package view

/** Buttons to select shapes or to change the shape of a selected block.
  *
  * @param util
  *   An instance of the [[Utility]] class needed for buttons.
  * @param ui
  *   An instance of the [[UI]] class needed for button positions.
  */
case class ShapeButtons(util: Utility, ui: UI):
  /** All 3 shape buttons, together. */
  private val shapeButtons =
    Image
      .triangle(util.wid, util.hgh)
      .fillColor(Color.black)
      .at(ui.triPt)
      .on:
        Image
          .square(util.wid)
          .fillColor(Color.black)
          .at(ui.sqrPt)
      .on:
        Image
          .circle(util.wid)
          .fillColor(Color.black)
          .at(ui.cirPt)
  end shapeButtons

  /** Draws a red indicator rectangle around a shape button, if it is selected / clicked.
    *
    * @param shape
    *   Shape of the button that's clicked / selected.
    * @return
    *   A red-edged, empty rectangle that fits around the shape button.
    */
  private def shapeIndicator(shape: Shape) = shape match
    case Shape.Tri => util.indicator(ui.triPt)
    case Shape.Sqr => util.indicator(ui.sqrPt)
    case Shape.Cir => util.indicator(ui.cirPt)

  /** Draws all three color buttons together, with indicator.
    *
    * @param shapeOpt
    *   An optional shape, in case one is selected in the user interface. This normally comes from the [[Controls]]
    *   instance of a [[World]].
    * @return
    *   An image of the three shape buttons, along with the red indicator rectangle for the one that is selected.
    */
  def shapes(shapeOpt: Option[Shape]) = shapeOpt match
    case None        => shapeButtons
    case Some(shape) => shapeIndicator(shape).on(shapeButtons)
