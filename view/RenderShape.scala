package tarski
package view

def renderShape(using Constants) =
  Image
    .triangle(wid, hgh)
    .fillColor(black)
    .at(triPt)
    .on:
      Image
        .square(wid)
        .fillColor(black)
        .at(squPt)
    .on:
      Image
        .circle(wid)
        .fillColor(black)
        .at(cirPt)

def shapeIndicator(shape: Shape)(using Constants) =
  import Shape.*
  shape match
    case Tri => renderIndicator(triPt)
    case Squ => renderIndicator(squPt)
    case Cir => renderIndicator(cirPt)

def renderShapes(shapeOpt: Option[Shape])(using Constants) =
  shapeOpt match
    case None        => renderShape
    case Some(shape) => shapeIndicator(shape).on(renderShape)
