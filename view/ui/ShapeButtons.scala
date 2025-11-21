package tarski
package view

class ShapeButtons(using c: Constants):
  val util = summon[Utility]
  val ui   = summon[UI]

  val shapeButtons =
    Image
      .triangle(util.wid, util.hgh)
      .fillColor(black)
      .at(ui.triPt)
      .on:
        Image
          .square(util.wid)
          .fillColor(black)
          .at(ui.squPt)
      .on:
        Image
          .circle(util.wid)
          .fillColor(black)
          .at(ui.cirPt)

  def shapeIndicator(shape: Shape) =
    import Shape.*
    shape match
      case Tri => util.indicator(ui.triPt)
      case Squ => util.indicator(ui.squPt)
      case Cir => util.indicator(ui.cirPt)

  def shapes(shapeOpt: Option[Shape]) =
    shapeOpt match
      case None        => shapeButtons
      case Some(shape) => shapeIndicator(shape).on(shapeButtons)
