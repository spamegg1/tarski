package tarski
package view

case class OpButtons(u: Utility)(using Constants):
  val evalButton = u.button("Eval", UI.evalPt, 2)
  val addButton  = u.button("Add", UI.addPt, 2)
  val delButton  = u.button("Del", UI.delPt, 2)
  val moveBtn    = u.button("Move", UI.movePt, 2)
  def moveButton(move: Boolean) =
    if move then u.indicator(UI.movePt, 2).on(moveBtn) else moveBtn
