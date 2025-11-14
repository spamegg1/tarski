package tarski
package view

extension (r: Render.type)(using c: Constants)
  def renderSmall  = renderButton("Small", UI.smallPt, 2)
  def renderMedium = renderButton("Mid", UI.midPt, 2)
  def renderLarge  = renderButton("Large", UI.largePt, 2)
  def renderSize   = renderSmall on renderMedium on renderLarge

  def sizeIndicator(size: Double) =
    import c.{Small, Mid, Large}
    if size.isCloseTo(Small) then renderIndicator(UI.smallPt, 2)
    else if size.isCloseTo(Mid) then renderIndicator(UI.midPt, 2)
    else if size.isCloseTo(Large) then renderIndicator(UI.largePt, 2)
    else Image.empty

  def sizes(sizeOpt: Option[Double]) = sizeOpt match
    case None       => renderSize
    case Some(size) => sizeIndicator(size).on(renderSize)
