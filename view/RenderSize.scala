package tarski
package view

def sizeIndicator(size: Double) =
  if size.isCloseTo(Small) then renderIndicator(smallPt, 2)
  else if size.isCloseTo(Medium) then renderIndicator(medPt, 2)
  else if size.isCloseTo(Large) then renderIndicator(largePt, 2)
  else Image.empty

def renderSizes(using c: Controls) = c.size match
  case None       => renderSize
  case Some(size) => sizeIndicator(size).on(renderSize)
