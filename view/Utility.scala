package tarski
package view

class Utility(using c: Constants):
  def wid = Converter.control.blockWidth
  def hgh = Converter.control.blockHeight

  def drawButton(factor: Int = 1) = Image.rectangle(wid * factor, hgh).fillColor(Gray)

  def indicator(point: Point, factor: Int = 1) = Image
    .rectangle(wid * factor, hgh)
    .strokeColor(red)
    .strokeWidth(c.SmallStroke)
    .at(point)

  def button(name: String, point: Point, factor: Int = 1) =
    Text(name)
      .font(c.TheFont)
      .on(drawButton(factor))
      .at(point)
