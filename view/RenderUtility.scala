package tarski
package view

val (wid, hgh)              = (ControlsConverter.blockWidth, ControlsConverter.blockHeight)
def button(factor: Int = 1) = Image.rectangle(wid * factor, hgh).fillColor(Gray)

def renderIndicator(point: Point, factor: Int = 1) = Image
  .rectangle(wid * factor, hgh)
  .strokeColor(red)
  .strokeWidth(SmallStroke)
  .at(point)

def renderButton(name: String, point: Point, factor: Int = 1) = Text(name)
  .font(TheFont)
  .on(button(factor))
  .at(point)
