package tarski
package view

val renderShape =
  Image
    .equilateralTriangle(wid)
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

def shapeIndicator(shape: Shape) = shape match
  case Tri => renderIndicator(triPt)
  case Squ => renderIndicator(squPt)
  case Cir => renderIndicator(cirPt)

def renderShapes(using c: Controls) = c.shape match
  case None        => renderShape
  case Some(shape) => shapeIndicator(shape).on(renderShape)
