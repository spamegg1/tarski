package tarski
package view

/** Buttons for Eval, Add, Move, Del, Left and Right (rotations).
  *
  * @param util
  *   An instance of the [[Utility]] class needed for buttons.
  * @param ui
  *   An instance of the [[UI]] class needed for button positions.
  */
case class OpButtons(util: Utility, ui: UI):
  /** The Eval button. */
  val evalButton = util.button("Eval", ui.evalPt, 2)

  /** The Add button. */
  val addButton = util.button("Add", ui.addPt, 2)

  /** The Del button. */
  val delButton = util.button("Del", ui.delPt, 2)

  /** The Move button. */
  val moveBtn = util.button("Move", ui.movePt, 2)

  /** The left (counter-clockwise) rotation button. */
  val leftBtn = util.button("L", ui.leftPt)

  /** The right (clockwise) rotation button. */
  val rightBtn = util.button("R", ui.rightPt)

  /** Move button, with indicator if needed.
    *
    * @param move
    *   The toggle state of the Move ability. This normally comes from a [[Controls]] instance in a [[World]].
    * @return
    *   An image of the Move button along with a red indicator rectangle if Move is enabled.
    */
  def moveButton(move: Boolean) = if move then util.indicator(ui.movePt, 2).on(moveBtn) else moveBtn
