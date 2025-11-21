package tarski
package view

case class ShapeButtons(util: Utility)(using Constants):
  val shapeButtons =
    Image
      .triangle(util.wid, util.hgh)
      .fillColor(black)
      .at(UI.triPt)
      .on:
        Image
          .square(util.wid)
          .fillColor(black)
          .at(UI.squPt)
      .on:
        Image
          .circle(util.wid)
          .fillColor(black)
          .at(UI.cirPt)

  def shapeIndicator(shape: Shape) =
    import Shape.*
    shape match
      case Tri => util.indicator(UI.triPt)
      case Squ => util.indicator(UI.squPt)
      case Cir => util.indicator(UI.cirPt)

  def shapes(shapeOpt: Option[Shape]) =
    shapeOpt match
      case None        => shapeButtons
      case Some(shape) => shapeIndicator(shape).on(shapeButtons)
