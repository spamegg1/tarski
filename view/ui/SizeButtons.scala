package tarski
package view

case class SizeButtons(u: Utility)(using c: Constants):
  val smallButton  = u.button("Small", UI.smallPt, 2)
  val mediumButton = u.button("Mid", UI.midPt, 2)
  val largeButton  = u.button("Large", UI.largePt, 2)
  val sizeButtons  = smallButton on mediumButton on largeButton

  def sizeIndicator(size: Double) =
    import c.{Small, Mid, Large}
    if size.isCloseTo(Small) then u.indicator(UI.smallPt, 2)
    else if size.isCloseTo(Mid) then u.indicator(UI.midPt, 2)
    else if size.isCloseTo(Large) then u.indicator(UI.largePt, 2)
    else Image.empty

  def sizes(sizeOpt: Option[Sizes]) = sizeOpt match
    case None       => sizeButtons
    case Some(size) => sizeIndicator(size).on(sizeButtons)

extension (d: Double) def isCloseTo(e: Double) = Math.abs(d - e) < 0.0001
