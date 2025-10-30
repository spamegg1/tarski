package tarski
package view

import Shape.*

val renderShape =
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

def shapeIndicator(shape: Shape) = shape match
  case Tri => renderIndicator(triPt)
  case Squ => renderIndicator(squPt)
  case Cir => renderIndicator(cirPt)

def renderShapes(shapeOpt: Option[Shape]) = shapeOpt match
  case None        => renderShape
  case Some(shape) => shapeIndicator(shape).on(renderShape)
