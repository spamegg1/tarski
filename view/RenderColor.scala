package tarski
package view

def colorBox(color: Color, point: Point)(using Constants) =
  Image.rectangle(wid, hgh).fillColor(color).at(point)

def renderBlue(using Constants)  = colorBox(Blue, bluePt)
def renderGreen(using Constants) = colorBox(Green, greenPt)
def renderGray(using Constants)  = colorBox(Gray, grayPt)
def renderColor(using Constants) = renderBlue on renderGreen on renderGray

def colorIndicator(color: Color)(using Constants) = color match
  case Blue  => renderIndicator(bluePt, 1)
  case Green => renderIndicator(greenPt, 1)
  case Gray  => renderIndicator(grayPt, 1)
  case _     => Image.empty

def renderColors(colorOpt: Option[Color])(using Constants) = colorOpt match
  case None        => renderColor
  case Some(color) => colorIndicator(color).on(renderColor)
