package tarski
package view

case class OpButtons(u: Utility)(using Constants):
  def evalButton = u.button("Eval", UI.evalPt, 2)
  def addButton  = u.button("Add", UI.addPt, 2)
  def delButton  = u.button("Del", UI.delPt, 2)
  def moveBtn    = u.button("Move", UI.movePt, 2)
  def moveButton(move: Boolean) =
    if move then u.indicator(UI.movePt, 2).on(moveBtn) else moveBtn
