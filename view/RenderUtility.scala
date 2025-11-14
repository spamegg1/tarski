package tarski
package view

extension (r: Render.type)(using c: Constants)
  def wid = Converter.control.blockWidth
  def hgh = Converter.control.blockHeight

  def button(factor: Int = 1) = Image.rectangle(wid * factor, hgh).fillColor(Gray)

  def renderIndicator(point: Point, factor: Int = 1) = Image
    .rectangle(wid * factor, hgh)
    .strokeColor(red)
    .strokeWidth(c.SmallStroke)
    .at(point)

  def renderButton(name: String, point: Point, factor: Int = 1) =
    Text(name)
      .font(c.TheFont)
      .on(button(factor))
      .at(point)
