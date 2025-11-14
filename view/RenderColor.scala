package tarski
package view

def colorBox(color: Color, point: Point)(using Constants) =
  Image.rectangle(wid, hgh).fillColor(color).at(point)

def renderBlue(using Constants)  = colorBox(Blue, UI.bluePt)
def renderGreen(using Constants) = colorBox(Green, UI.greenPt)
def renderGray(using Constants)  = colorBox(Gray, UI.grayPt)
def renderColor(using Constants) = renderBlue on renderGreen on renderGray

def colorIndicator(color: Color)(using Constants) = color match
  case Blue  => renderIndicator(UI.bluePt, 1)
  case Green => renderIndicator(UI.greenPt, 1)
  case Gray  => renderIndicator(UI.grayPt, 1)
  case _     => Image.empty

def renderColors(colorOpt: Option[Color])(using Constants) = colorOpt match
  case None        => renderColor
  case Some(color) => colorIndicator(color).on(renderColor)
