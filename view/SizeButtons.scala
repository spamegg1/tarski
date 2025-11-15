package tarski
package view

extension (r: Render.type)(using c: Constants)
  def smallButton  = button("Small", UI.smallPt, 2)
  def mediumButton = button("Mid", UI.midPt, 2)
  def largeButton  = button("Large", UI.largePt, 2)
  def sizeButtons  = smallButton on mediumButton on largeButton

  def sizeIndicator(size: Double) =
    import c.{Small, Mid, Large}
    if size.isCloseTo(Small) then indicator(UI.smallPt, 2)
    else if size.isCloseTo(Mid) then indicator(UI.midPt, 2)
    else if size.isCloseTo(Large) then indicator(UI.largePt, 2)
    else Image.empty

  def sizes(sizeOpt: Option[Double]) = sizeOpt match
    case None       => sizeButtons
    case Some(size) => sizeIndicator(size).on(sizeButtons)

extension (d: Double) def isCloseTo(e: Double) = Math.abs(d - e) < 0.0001
