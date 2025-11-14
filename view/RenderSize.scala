package tarski
package view

def renderSmall(using Constants)  = renderButton("Small", smallPt, 2)
def renderMedium(using Constants) = renderButton("Mid", midPt, 2)
def renderLarge(using Constants)  = renderButton("Large", largePt, 2)
def renderSize(using Constants)   = renderSmall on renderMedium on renderLarge

def sizeIndicator(size: Double)(using c: Constants) =
  import c.{Small, Mid, Large}
  if size.isCloseTo(Small) then renderIndicator(smallPt, 2)
  else if size.isCloseTo(Mid) then renderIndicator(midPt, 2)
  else if size.isCloseTo(Large) then renderIndicator(largePt, 2)
  else Image.empty

def renderSizes(sizeOpt: Option[Double])(using Constants) = sizeOpt match
  case None       => renderSize
  case Some(size) => sizeIndicator(size).on(renderSize)
