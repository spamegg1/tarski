package tarski
package view

class Render(using c: Constants):
  val opBtn    = summon[OpButtons]
  val nameBtn  = summon[NameButtons]
  val sizeBtn  = summon[SizeButtons]
  val colBtn   = summon[ColorButtons]
  val shapeBtn = summon[ShapeButtons]
  val ui       = summon[UI]

  def selectedBlock(ct: Controls): Image =
    Imager(Block.fromControls(ct)).at(ui.blockPt)

  def renderUI(world: World) =
    opBtn.evalButton
      .on(opBtn.moveButton(world.controls.move))
      .on(opBtn.addButton)
      .on(opBtn.delButton)
      .on(nameBtn.allNames(world.names))
      .on(sizeBtn.sizes(world.controls.sizeOpt))
      .on(colBtn.colorBoxes(world.controls.toneOpt))
      .on(shapeBtn.shapes(world.controls.shapeOpt))
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

  def blocksOnBoard(grid: PosGrid): Image = grid
    .foldLeft[Image](c.Board):
      case (image, (pos, (block, name))) =>
        Imager(block)
          .at(Converter.board.toPoint(pos))
          .on(image)

  def all(world: World): Image =
    selectedPos(world.controls.posOpt)
      .on(blocksOnBoard(world.posGrid))
      .at(c.BoardOrigin)
      .on:
        renderUI(world)
          .at(c.UIOrigin)
      .on:
        formulaDisplay(world.formulas)
          .at(c.FormulaOrigin)
