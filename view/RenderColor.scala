package tarski
package view

def colorBox(color: Color, point: Point) =
  Image.rectangle(wid, hgh).fillColor(color).at(point)

val renderBlue  = colorBox(Blue, bluePt)
val renderGreen = colorBox(Green, greenPt)
val renderGray  = colorBox(Gray, grayPt)
val renderColor = renderBlue on renderGreen on renderGray

def colorIndicator(color: Color) = color match
  case Blue  => renderIndicator(bluePt, 1)
  case Green => renderIndicator(greenPt, 1)
  case Gray  => renderIndicator(grayPt, 1)
  case _     => Image.empty

def renderColors(using c: Controls) = c.color match
  case None        => renderColor
  case Some(color) => colorIndicator(color).on(renderColor)
