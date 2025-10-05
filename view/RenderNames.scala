package tarski
package view

val renderName = Seq("a", "b", "c", "d", "e", "f")
  .map: name =>
    val point = ControlsConverter.toPoint(controlGrid(name))
    renderButton(name, point, 1)
  .foldLeft[Image](Image.empty)(_.on(_))

def nameIndicator(name: String) =
  val point = ControlsConverter.toPoint(controlGrid(name))
  renderIndicator(point, 1)

def renderNames(using c: Controls) = c.name match
  case None       => renderName
  case Some(name) => nameIndicator(name).on(renderName)
