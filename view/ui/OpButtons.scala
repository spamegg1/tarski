package tarski
package view

/** Buttons for Eval, Add, Move and Del.
  *
  * @param c
  *   A given instance of [[Constants]], needed to derive [[Utility]] and [[UI]] instances.
  */
class OpButtons(using c: Constants):
  /** An instance of [[Utility]] summoned here for drawing buttons and indicators. */
  private val util = summon[Utility]

  /** An instance of [[UI]] summoned here to calculate positions of buttons. */
  private val ui = summon[UI]

  /** The Eval button. */
  val evalButton = util.button("Eval", ui.evalPt, 2)

  /** The Add button. */
  val addButton = util.button("Add", ui.addPt, 2)

  /** The Del button. */
  val delButton = util.button("Del", ui.delPt, 2)

  /** The Move button. */
  val moveBtn = util.button("Move", ui.movePt, 2)

  /** Move button, with indicator if needed.
    *
    * @param move
    *   The toggle state of the Move ability. This normally comes from a [[Controls]] instance in a [[World]].
    * @return
    *   An image of the Move button along with a red indicator rectangle if Move is enabled.
    */
  def moveButton(move: Boolean) = if move then util.indicator(ui.movePt, 2).on(moveBtn) else moveBtn
