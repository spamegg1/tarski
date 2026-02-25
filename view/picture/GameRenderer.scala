package tarski
package view

/** Main renderer for the game that displays everything: the board, messages, and all the buttons.
  *
  * @param gameBtn
  * @param ui
  * @param c
  */
case class GameRenderer(gameBtn: GameButtons, ui: UI)(using c: Constants) extends BoardRenderer(ui):
  /** Draws all the buttons of the game user interface controls.
    *
    * @param game
    *   The game we want to render.
    * @return
    *   An image of 2 choice buttons, 2 commitment buttons, back/OK buttons and the selected block, all together.
    */
  private def renderUI(game: Game) =
    gameBtn
      .top(game.step.play.left)
      .on(gameBtn.bot(game.step.play.show))
      .on(gameBtn.trueFalseButtons(game.step.play.commitment))
      .on(gameBtn.backButton)
      .on(gameBtn.okButton)
      .on(gameBtn.blockButton(game.getBlock))

  /** Displays all the messages.
    *
    * @param msgs
    *   A list of messages to be displayed, normally coming from a [[Game]].
    * @return
    *   An image of the messages listed vertically.
    */
  private def messages(msgs: Messages) = msgs
    .foldLeft[Image](Image.empty):
      case (image, msg) =>
        image.above(Imager(msg))

  /** Draws the entire game application.
    *
    * @param game
    *   The state of the game we want to render.
    * @return
    *   An image of the chess board with all the blocks, and the UI controls and buttons, and all the messages.
    */
  def all(game: Game): Image =
    selectedPos(game.pos.opt)
      .on(selectedPos(game.step.play.pos))
      .on(blocksOnBoard(game.board))
      .at(c.BoardOrigin)
      .on:
        renderUI(game)
          .at(c.UIOrigin)
      .on:
        messages(game.messages)
          .at(c.FormulaOrigin)

object GameRenderer:
  /** Convenient alternate constructor for [[GameRenderer]].
    *
    * @param c
    *   A given instance of [[Constants]].
    * @return
    *   An instance of [[GameRenderer]].
    */
  def apply(using c: Constants): GameRenderer =
    val util    = new Utility
    val ui      = new UI
    val gameUI  = new GameUI
    val gameBtn = GameButtons(util, gameUI)
    GameRenderer(gameBtn, ui)
