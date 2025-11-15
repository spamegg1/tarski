package tarski
package view

object Render

extension (r: Render.type)(using c: Constants)
  def evalButton = r.button("Eval", UI.evalPt, 2)
  def addButton  = r.button("Add", UI.addPt, 2)
  def delButton  = r.button("Del", UI.delPt, 2)

  def selectedBlock(ct: Controls) = Imager(Block.fromControls(ct)).at(UI.blockPt)

  def moveButton(move: Boolean) =
    val button = r.button("Move", UI.movePt, 2)
    if move then r.indicator(UI.movePt, 2).on(button) else button

  def ui(world: World) =
    evalButton
      .on(moveButton(world.controls.move))
      .on(addButton)
      .on(delButton)
      .on(Render.allNames(world.names))
      .on(Render.sizes(world.controls.size))
      .on(Render.colorBoxes(world.controls.color))
      .on(Render.shapes(world.controls.shape))
      .on(selectedBlock(world.controls))

  def formulaDisplay(formulas: Formulas) = formulas
    .foldLeft[Image](Image.empty):
      case (image, (formula, result)) =>
        image.above(Imager(formula).beside(Imager(result)))

  def selectedPos(pos: Option[Pos]): Image =
    pos match
      case None => Image.empty
      case Some(pos) =>
        Image
          .rectangle(Converter.board.blockWidth, Converter.board.blockHeight)
          .strokeColor(red)
          .strokeWidth(c.SmallStroke)
          .at(Converter.board.toPoint(pos))

  def blocksOnBoard(grid: Grid): Image = grid
    .foldLeft[Image](c.Board):
      case (image, (pos, (block, name))) =>
        Imager(block)
          .at(Converter.board.toPoint(pos))
          .on(image)

  def all(world: World): Image =
    selectedPos(world.controls.pos)
      .on(blocksOnBoard(world.grid))
      .at(c.BoardOrigin)
      .on:
        ui(world)
          .at(c.ControlsOrigin)
      .on:
        formulaDisplay(world.formulas)
          .at(c.FormulasOrigin)
