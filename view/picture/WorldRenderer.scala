package tarski
package view

/** Main renderer for the world that displays everything: the board, formulas, and all the buttons.
  *
  * @param opBtn
  *   An instance of [[OpButtons]].
  * @param nameBtn
  *   An instance of [[NameButtons]].
  * @param sizeBtn
  *   An instance of [[SizeButtons]].
  * @param colBtn
  *   An instance of [[ColorButtons]].
  * @param shapeBtn
  *   An instance of [[ShapeButtons]].
  * @param ui
  *   An instance of [[UI]] needed for block positions.
  * @param c
  *   A given instance of [[Constants]]
  */
case class WorldRenderer(
    opBtn: OpButtons,
    nameBtn: NameButtons,
    sizeBtn: SizeButtons,
    colBtn: ColorButtons,
    shapeBtn: ShapeButtons,
    ui: UI
)(using c: Constants)
    extends BoardRenderer(ui):
  /** Displays the selected block on the user interface controls.
    *
    * @param ct
    *   A [[Controls]] instance, normally coming from a [[World]].
    * @return
    *   An image of the selected block block, or an empty image if not all attributes are set.
    */
  private def selectedBlock(ct: Controls): Image = renderBlock(Block.fromControls(ct))

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

/** Companion object for [[WorldRenderer]] that contains a convenient alternate constructor.
  */
object WorldRenderer:
  /** Convenient alternate constructor that only requires [[Constants]] and sets up all the [[Utility]], [[UI]] and all
    * the button class instances.
    *
    * @param c
    *   A given instance of [[Constants]].
    * @return
    *   An instance of [[WorldRenderer]].
    */
  def apply(using c: Constants): WorldRenderer =
    val util     = new Utility
    val ui       = new UI
    val opBtn    = OpButtons(util, ui)
    val nameBtn  = NameButtons(util)
    val sizeBtn  = SizeButtons(util, ui)
    val colBtn   = ColorButtons(util, ui)
    val shapeBtn = ShapeButtons(util, ui)
    WorldRenderer(opBtn, nameBtn, sizeBtn, colBtn, shapeBtn, ui)
