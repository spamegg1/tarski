package tarski
package view

class Utility(using c: Constants):
  def wid = Converter.ui.blockWidth
  def hgh = Converter.ui.blockHeight

  def btn(factor: Int = 1) = Image.rectangle(wid * factor, hgh).fillColor(Tone.Gray)

  def indicator(point: Point, factor: Int = 1) = Image
    .rectangle(wid * factor, hgh)
    .strokeColor(red)
    .strokeWidth(c.SmallStroke)
    .at(point)

  def button(name: String, point: Point, factor: Int = 1) =
    Text(name)
      .font(c.TheFont)
      .on(btn(factor))
      .at(point)
