package tarski
package view

extension (r: Render.type)(using Constants)
  def shapeButtons =
    Image
      .triangle(r.wid, r.hgh)
      .fillColor(black)
      .at(UI.triPt)
      .on:
        Image
          .square(r.wid)
          .fillColor(black)
          .at(UI.squPt)
      .on:
        Image
          .circle(r.wid)
          .fillColor(black)
          .at(UI.cirPt)

  def shapeIndicator(shape: Shape) =
    import Shape.*
    shape match
      case Tri => indicator(UI.triPt)
      case Squ => indicator(UI.squPt)
      case Cir => indicator(UI.cirPt)

  def shapes(shapeOpt: Option[Shape]) =
    shapeOpt match
      case None        => shapeButtons
      case Some(shape) => shapeIndicator(shape).on(shapeButtons)
