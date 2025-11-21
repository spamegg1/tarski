package tarski
package view

class SizeButtons(using c: Constants):
  val util = summon[Utility]
  val ui   = summon[UI]

  val smallButton  = util.button("Small", ui.smallPt, 2)
  val mediumButton = util.button("Mid", ui.midPt, 2)
  val largeButton  = util.button("Large", ui.largePt, 2)
  val sizeButtons  = smallButton on mediumButton on largeButton

  def sizeIndicator(size: Double) =
    import c.{Small, Mid, Large}
    if size.isCloseTo(Small) then util.indicator(ui.smallPt, 2)
    else if size.isCloseTo(Mid) then util.indicator(ui.midPt, 2)
    else if size.isCloseTo(Large) then util.indicator(ui.largePt, 2)
    else Image.empty

  def sizes(sizeOpt: Option[Sizes]) = sizeOpt match
    case None       => sizeButtons
    case Some(size) => sizeIndicator(size).on(sizeButtons)

extension (d: Double) def isCloseTo(e: Double) = Math.abs(d - e) < 0.0001
