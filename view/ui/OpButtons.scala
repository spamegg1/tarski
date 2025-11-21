package tarski
package view

class OpButtons(using c: Constants):
  val util       = summon[Utility]
  val ui         = summon[UI]
  val evalButton = util.button("Eval", ui.evalPt, 2)
  val addButton  = util.button("Add", ui.addPt, 2)
  val delButton  = util.button("Del", ui.delPt, 2)
  val moveBtn    = util.button("Move", ui.movePt, 2)
  def moveButton(move: Boolean) =
    if move then util.indicator(ui.movePt, 2).on(moveBtn) else moveBtn
