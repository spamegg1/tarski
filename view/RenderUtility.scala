package tarski
package view

def wid(using Constants) = ControlsConverter.blockWidth
def hgh(using Constants) = ControlsConverter.blockHeight

def button(factor: Int = 1)(using Constants) = Image.rectangle(wid * factor, hgh).fillColor(Gray)

def renderIndicator(point: Point, factor: Int = 1)(using c: Constants) = Image
  .rectangle(wid * factor, hgh)
  .strokeColor(red)
  .strokeWidth(c.SmallStroke)
  .at(point)

def renderButton(name: String, point: Point, factor: Int = 1)(using c: Constants) =
  Text(name)
    .font(c.TheFont)
    .on(button(factor))
    .at(point)
