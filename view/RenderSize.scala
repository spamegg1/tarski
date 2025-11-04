package tarski
package view

val renderSmall  = renderButton("Small", smallPt, 2)
val renderMedium = renderButton("Mid", midPt, 2)
val renderLarge  = renderButton("Large", largePt, 2)
val renderSize   = renderSmall on renderMedium on renderLarge

def sizeIndicator(size: Double) =
  if size.isCloseTo(Small) then renderIndicator(smallPt, 2)
  else if size.isCloseTo(Med) then renderIndicator(midPt, 2)
  else if size.isCloseTo(Large) then renderIndicator(largePt, 2)
  else Image.empty

def renderSizes(sizeOpt: Option[Double]) = sizeOpt match
  case None       => renderSize
  case Some(size) => sizeIndicator(size).on(renderSize)
