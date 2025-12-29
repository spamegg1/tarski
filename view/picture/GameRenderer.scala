package tarski
package view

/** Main renderer for the game that displays everything: the board, messages, and all the buttons.
  *
  * @param c
  *   A given instance of [[Constants]] needed for converters, all the buttons, utility and UI.
  */
class GameRenderer(using c: Constants):
  /** An instance of [[OPButtons]] summoned here for Eval, Add, Move, Del buttons. */
  private val opBtn = summon[OpButtons]

  /** An instance of [[NameButtons]] summoned here for the 6 name buttons. */
  private val nameBtn = summon[NameButtons]

  /** An instance of [[SizeButtons]] summoned here for the 3 size buttons. */
  private val sizeBtn = summon[SizeButtons]

  /** An instance of [[ColorButtons]] summoned here for the 3 color buttons. */
  private val colBtn = summon[ColorButtons]

  /** An instance of [[ShapeButtons]] summoned here for the 3 shape buttons. */
  private val shapeBtn = summon[ShapeButtons]

  /** An instance of [[UI]] summoned here to calculate positions. */
  private val ui = summon[UI]

  /** Displays the block on the user interface controls.
    *
    * @param ct
    *   A [[Controls]] instance, normally coming from a [[World]].
    * @return
    *   An image of the block obtained from the attributes in [[Controls]], or an empty image if not all attributes are
    *   set.
    */
  private def selectedBlock(ct: Controls): Image =
    Imager(Block.fromControls(ct)).at(ui.blockPt)

  /** Draws all the buttons of the user interface controls.
    *
    * @param world
    *   The world we want to render.
    * @return
    *   An image of Eval, Add, Move, Del, L/R, buttons, 6 name buttons, 3 color buttons, 3 shape buttons, 3 size buttons
    *   and the selected block, all together.
    */
  private def renderUI(world: World) =
    opBtn.evalButton
      .on(opBtn.moveButton(world.controls.move))
      .on(opBtn.addButton)
      .on(opBtn.delButton)
      .on(opBtn.leftBtn)
      .on(opBtn.rightBtn)
      .on(nameBtn.allNames(world.names))
      .on(sizeBtn.sizes(world.controls.sizeOpt))
      .on(colBtn.colorBoxes(world.controls.toneOpt))
      .on(shapeBtn.shapes(world.controls.shapeOpt))
      .on(selectedBlock(world.controls))

  /** Draws all the formulas and their evaluation results.
    *
    * @param formulas
    *   A map of formulas and evaluation results, normally coming from a [[World]].
    * @return
    *   An image of the formulas along with their results listed vertically.
    */
  private def formulaDisplay(formulas: Formulas) = formulas
    .foldLeft[Image](Image.empty):
      case (image, (formula, result)) =>
        image.above(Imager(formula).beside(Imager(result)))

  /** Draws a red indicator box around the selected position on the board.
    *
    * @param pos
    *   The position of the selected square on the chess board.
    * @return
    *   A red-edged, empty square to fit around the selected square on the chess board.
    */
  private def selectedPos(pos: Option[Pos]): Image =
    pos match
      case None      => Image.empty
      case Some(pos) =>
        Image
          .rectangle(Converter.board.blockWidth, Converter.board.blockHeight)
          .strokeColor(Color.red)
          .strokeWidth(c.SmallStroke)
          .at(Converter.board.toPoint(pos))

  /** Draws all the blocks on the chess board.
    *
    * @param grid
    *   A map of positions and blocks
    * @return
    *   An image of the chess board, with blocks at their positions.
    */
  private def blocksOnBoard(board: Board): Image = board.grid
    .foldLeft[Image](c.Board):
      case (image, (pos, (block, name))) =>
        Imager(block)
          .at(Converter.board.toPoint(pos))
          .on(image)

  /** Draws the entire Tarski's world application.
    *
    * @param world
    *   The state of the world we want to render.
    * @return
    *   An image of the chess board with all the blocks, and the UI controls and buttons, and all the formulas.
    */
  def all(world: World): Image =
    selectedPos(world.controls.posOpt)
      .on(blocksOnBoard(world.board))
      .at(c.BoardOrigin)
      .on:
        renderUI(world)
          .at(c.UIOrigin)
      .on:
        formulaDisplay(world.formulas)
          .at(c.FormulaOrigin)
