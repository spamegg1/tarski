package tarski
package view

/** Buttons that toggle the names (a,b,c,d,e,f) for blocks on the board.
  *
  * @param util
  *   An instance of the [[Utility]] class needed for buttons.
  * @param _
  *   A given instance of [[Constants]] needed in [[controller.Converter.ui]].
  */
case class NameButtons(util: Utility)(using Constants):
  /** All 6 name buttons together. */
  private val nameButtons = Seq("a", "b", "c", "d", "e", "f")
    .map: name =>
      val point = Converter.ui.toPoint(UI.grid(name))
      util.button(name, point, 1)
    .foldLeft[Image](Image.empty)(_.on(_))

  /** Draws a red indicator rectangle around a name button, if it is selected.
    *
    * @param name
    *   One of the 6 names.
    * @return
    *   A red-edged, empty rectangle that fits around the name's button.
    */
  private def nameIndicator(name: String) =
    val point = Converter.ui.toPoint(UI.grid(name))
    util.indicator(point, 1)

  /** Draws all 6 name buttons together, along with their indicators.
    *
    * @param names
    *   A map from names to their availability status. This normally comes from a [[World]] instance.
    * @return
    *   An image of all 6 name buttons, along with indicators on them for the names that are currently occupied.
    */
  def allNames(names: Names) =
    names.foldLeft(nameButtons):
      case (img, (name, status)) =>
        status match
          case Status.Available => img
          case Status.Occupied  => nameIndicator(name).on(img)
