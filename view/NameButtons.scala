package tarski
package view

extension (r: Render.type)(using Constants)
  def nameButtons = Seq("a", "b", "c", "d", "e", "f")
    .map: name =>
      val point = Converter.control.toPoint(controlGrid(name))
      r.button(name, point, 1)
    .foldLeft[Image](Image.empty)(_.on(_))

  def nameIndicator(name: String) =
    val point = Converter.control.toPoint(controlGrid(name))
    indicator(point, 1)

  def allNames(names: Names) =
    names.foldLeft(nameButtons):
      case (img, (name, status)) =>
        import Status.*
        status match
          case Available => img
          case Occupied  => nameIndicator(name).on(img)
