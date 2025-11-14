package tarski
package view

def renderSmall(using Constants)  = renderButton("Small", UI.smallPt, 2)
def renderMedium(using Constants) = renderButton("Mid", UI.midPt, 2)
def renderLarge(using Constants)  = renderButton("Large", UI.largePt, 2)
def renderSize(using Constants)   = renderSmall on renderMedium on renderLarge

def sizeIndicator(size: Double)(using c: Constants) =
  import c.{Small, Mid, Large}
  if size.isCloseTo(Small) then renderIndicator(UI.smallPt, 2)
  else if size.isCloseTo(Mid) then renderIndicator(UI.midPt, 2)
  else if size.isCloseTo(Large) then renderIndicator(UI.largePt, 2)
  else Image.empty

def renderSizes(sizeOpt: Option[Double])(using Constants) = sizeOpt match
  case None       => renderSize
  case Some(size) => sizeIndicator(size).on(renderSize)
