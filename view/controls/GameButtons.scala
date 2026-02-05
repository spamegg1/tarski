package tarski
package view

/** Buttons for the [[Game]].
  *
  * @param util
  *   An instance of the [[Utility]] class needed for buttons.
  * @param gameUI
  *   An instance of the [[GameUI]] class needed for button positions.
  * @param _
  *   A given instance of [[Constants]] needed for [[Imager]].
  */
case class GameButtons(util: Utility, gameUI: GameUI)(using Constants):
  /** The top formula choice button, for the `left` formula in a [[model.Play]].
    *
    * @param leftOpt
    *   An optional `FOLFormula`. Normally comes from [[model.Play.left]].
    * @return
    *   A button displaying the formula at the top left quarter of the game UI.
    */
  def top(leftOpt: Option[FOLFormula]) = util.button(leftOpt.show, gameUI.leftPt, 11)

  /** The bottom formula choice button, for the `right` formula in a [[model.Play]]. It does double duty by also
    * displaying the current formula when a choice is not present.
    *
    * @param rightOrF
    *   An `FOLFormula`. Normally comes from [[model.Play.right]] or [[model.Play.formula]].
    * @return
    *   A button displaying either one of the 2 choices, or the current formula, at the bottom left quarter of the game
    *   UI.
    */
  def bot(rightOrF: FOLFormula) = util.button(rightOrF.toUntypedString, gameUI.rightPt, 11)

  /** The button for the `True` commitment. */
  val trueButton = util.button("True", gameUI.truePt, 2)

  /** The button for the `False` commitment. */
  val falseButton = util.button("False", gameUI.falsePt, 2)

  /** The Back button. */
  val backButton = util.button("←", gameUI.backPt)

  /** The OK button. */
  val okButton = util.button("OK", gameUI.okPt)

  /** Displays the block from the selected board position, if any.
    *
    * @param blockOpt
    *   An optional [[Block]] (depending on the selected board position).
    * @return
    *   An image of the block, displayed at the right-most of the game UI.
    */
  def blockButton(blockOpt: Option[Block]) = Imager(blockOpt).at(gameUI.blockPt)

  extension (fOpt: Option[FOLFormula])
    def show = fOpt match
      case None        => ""
      case Some(value) => value.toUntypedString
