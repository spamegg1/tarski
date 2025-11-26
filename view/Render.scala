package tarski
package view

class Render(using c: Constants):
  private val opBtn    = summon[OpButtons]
  private val nameBtn  = summon[NameButtons]
  private val sizeBtn  = summon[SizeButtons]
  private val colBtn   = summon[ColorButtons]
  private val shapeBtn = summon[ShapeButtons]
  private val ui       = summon[UI]

  private def selectedBlock(ct: Controls): Image =
    Imager(Block.fromControls(ct)).at(ui.blockPt)

  private def renderUI(world: World) =
    opBtn.evalButton
      .on(opBtn.moveButton(world.controls.move))
      .on(opBtn.addButton)
      .on(opBtn.delButton)
      .on(nameBtn.allNames(world.names))
      .on(sizeBtn.sizes(world.controls.sizeOpt))
      .on(colBtn.colorBoxes(world.controls.toneOpt))
      .on(shapeBtn.shapes(world.controls.shapeOpt))
      .on(selectedBlock(world.controls))

  private def formulaDisplay(formulas: Formulas) = formulas
    .foldLeft[Image](Image.empty):
      case (image, (formula, result)) =>
        image.above(Imager(formula).beside(Imager(result)))

  private def selectedPos(pos: Option[Pos]): Image =
    pos match
      case None => Image.empty
      case Some(pos) =>
        Image
          .rectangle(Converter.board.blockWidth, Converter.board.blockHeight)
          .strokeColor(red)
          .strokeWidth(c.SmallStroke)
          .at(Converter.board.toPoint(pos))

  private def blocksOnBoard(grid: PosGrid): Image = grid
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
