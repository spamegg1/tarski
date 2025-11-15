package tarski
package view

case class Render(
    u: Utility,
    nb: NameButtons,
    sb: SizeButtons,
    cb: ColorButtons,
    shb: ShapeButtons
)(using c: Constants):
  def evalButton = u.button("Eval", UI.evalPt, 2)
  def addButton  = u.button("Add", UI.addPt, 2)
  def delButton  = u.button("Del", UI.delPt, 2)

  def selectedBlock(ct: Controls) = Imager(Block.fromControls(ct)).at(UI.blockPt)

  def moveButton(move: Boolean) =
    val button = u.button("Move", UI.movePt, 2)
    if move then u.indicator(UI.movePt, 2).on(button) else button

  def ui(world: World) =
    evalButton
      .on(moveButton(world.controls.move))
      .on(addButton)
      .on(delButton)
      .on(nb.allNames(world.names))
      .on(sb.sizes(world.controls.size))
      .on(cb.colorBoxes(world.controls.color))
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
          .at(c.ControlsOrigin)
      .on:
        formulaDisplay(world.formulas)
          .at(c.FormulasOrigin)

object Render:
  def apply(using c: Constants): Render =
    val u   = Utility(using c)
    val nb  = NameButtons(u)
    val sb  = SizeButtons(u)
    val cb  = ColorButtons(u)
    val shb = ShapeButtons(u)
    Render(u, nb, sb, cb, shb)
