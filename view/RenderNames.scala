package tarski
package view

extension (r: Render.type)(using Constants)
  def renderName = Seq("a", "b", "c", "d", "e", "f")
    .map: name =>
      val point = Converter.control.toPoint(controlGrid(name))
      Render.renderButton(name, point, 1)
    .foldLeft[Image](Image.empty)(_.on(_))

  def nameIndicator(name: String) =
    val point = Converter.control.toPoint(controlGrid(name))
    Render.renderIndicator(point, 1)

  def names(names: Names) =
    names.foldLeft(renderName):
      case (img, (name, status)) =>
        import Status.*
        status match
          case Available => img
          case Occupied  => nameIndicator(name).on(img)
