package tarski
package view

class NameButtons(using c: Constants):
  val util = summon[Utility]

  val nameButtons = Seq("a", "b", "c", "d", "e", "f")
    .map: name =>
      val point = Converter.ui.toPoint(UI.grid(name))
      util.button(name, point, 1)
    .foldLeft[Image](Image.empty)(_.on(_))

  def nameIndicator(name: String) =
    val point = Converter.ui.toPoint(UI.grid(name))
    util.indicator(point, 1)

  def allNames(names: Names) =
    names.foldLeft(nameButtons):
      case (img, (name, status)) =>
        import Status.*
        status match
          case Available => img
          case Occupied  => nameIndicator(name).on(img)
