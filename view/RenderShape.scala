package tarski
package view

extension (r: Render.type)(using Constants)
  def renderShape =
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
      case Tri => renderIndicator(UI.triPt)
      case Squ => renderIndicator(UI.squPt)
      case Cir => renderIndicator(UI.cirPt)

  def shapes(shapeOpt: Option[Shape]) =
    shapeOpt match
      case None        => renderShape
      case Some(shape) => shapeIndicator(shape).on(renderShape)
