package tarski
package view

case class Render(
    ob: OpButtons,
    nb: NameButtons,
    szb: SizeButtons,
    cb: ColorButtons,
    shb: ShapeButtons
)(using c: Constants):
  def selectedBlock(ct: Controls) = Imager(Block.fromControls(ct)).at(UI.blockPt)

  def ui(world: World) =
    ob.evalButton
      .on(ob.moveButton(world.controls.move))
      .on(ob.addButton)
      .on(ob.delButton)
      .on(nb.allNames(world.names))
      .on(szb.sizes(world.controls.size))
      .on(cb.colorBoxes(world.controls.tone))
      .on(shb.shapes(world.controls.shape))
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
          .at(c.UIOrigin)
      .on:
        formulaDisplay(world.formulas)
          .at(c.FormulaOrigin)

object Render:
  def apply(using c: Constants): Render =
    val u   = Utility(using c)
    val ob  = OpButtons(u)
    val nb  = NameButtons(u)
    val szb = SizeButtons(u)
    val cb  = ColorButtons(u)
    val shb = ShapeButtons(u)
    Render(ob, nb, szb, cb, shb)
