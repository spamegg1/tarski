package tarski
package view

def renderName = Seq("a", "b", "c", "d", "e", "f")
  .map: name =>
    val point = ControlsConverter.toPoint(controlGrid(name))
    renderButton(name, point, 1)
  .foldLeft[Image](Image.empty)(_.on(_))

def nameIndicator(name: String) =
  val point = ControlsConverter.toPoint(controlGrid(name))
  renderIndicator(point, 1)

def renderNames(names: Names) =
  names.foldLeft(renderName):
    case (img, (name, status)) =>
      import Status.*
      status match
        case Available => img
        case Occupied  => nameIndicator(name).on(img)
