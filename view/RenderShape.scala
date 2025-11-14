package tarski
package view

def renderShape(using Constants) =
  Image
    .triangle(wid, hgh)
    .fillColor(black)
    .at(UI.triPt)
    .on:
      Image
        .square(wid)
        .fillColor(black)
        .at(UI.squPt)
    .on:
      Image
        .circle(wid)
        .fillColor(black)
        .at(UI.cirPt)

def shapeIndicator(shape: Shape)(using Constants) =
  import Shape.*
  shape match
    case Tri => renderIndicator(UI.triPt)
    case Squ => renderIndicator(UI.squPt)
    case Cir => renderIndicator(UI.cirPt)

def renderShapes(shapeOpt: Option[Shape])(using Constants) =
  shapeOpt match
    case None        => renderShape
    case Some(shape) => shapeIndicator(shape).on(renderShape)
