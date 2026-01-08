package tarski
package view

/** Utility class that draws buttons and indicators common to many button types.
  *
  * @param c
  *   A given instance of [[Constants]], needed to use [[Converter]].
  */
class Utility(using c: Constants):
  /** The width of one block unit of the user interface controls. */
  val wid = Converter.ui.blockWidth

  /** The height of one block unit of the user interface controls. */
  val hgh = Converter.ui.blockHeight

  /** A generic button that can be scaled wide.
    *
    * @param factor
    *   Can be 1 or 2 (for the double-sized, wide buttons).
    * @return
    *   An image of a button with a gray background, normally the size of one block, stretched by `factor`.
    */
  def btn(factor: Int = 1) = Image
    .rectangle(wid * factor, hgh)
    .fillColor(Color.lightGray)

  /** A generic indicator rectangle that can be scaled wide.
    *
    * @param point
    *   The point around which we want to draw an indicator rectangle.
    * @param factor
    *   Can be 1 or 2 (for the double-sized, wide buttons).
    * @return
    *   A red-edged, empty rectangle that fits around the button located at given point.
    */
  def indicator(point: Point, factor: Int = 1) = Image
    .rectangle(wid * factor, hgh)
    .strokeColor(Color.red)
    .strokeWidth(c.SmallStroke)
    .at(point)

  /** A generic button with text, which can be scaled wide.
    *
    * @param name
    *   The name of the button we want to display as text.
    * @param point
    *   The point at which we want to draw the button.
    * @param factor
    *   Used to scale the width for creating wider buttons.
    * @return
    *   An image of a button with a gray background, normally the size of one block, stretched by `factor`, with text
    *   displayed on it.
    */
  def button(name: String, point: Point, factor: Int = 1) =
    Text(name)
      .font(c.TheFont)
      .on(btn(factor))
      .at(point)
