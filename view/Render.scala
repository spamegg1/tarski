package tarski
package view

object Render:
  def apply(world: World)(using c: Constants): Image =
    Render
      .selectedPos(world.controls.pos)
      .on(Render.blocks(world.grid))
      .at(c.BoardOrigin)
      .on(Render.controls(world).at(c.ControlsOrigin))
      .on(Render.formulas(world.formulas).at(c.FormulasOrigin))

extension (r: Render.type)(using c: Constants)
  def renderEval = r.renderButton("Eval", UI.evalPt, 2)
  def renderAdd  = r.renderButton("Add", UI.addPt, 2)
  def renderDel  = r.renderButton("Del", UI.delPt, 2)

  def renderBlock(ct: Controls) = Imager(Block.fromControls(ct)).at(UI.blockPt)

  def renderMove(move: Boolean) =
    val button = r.renderButton("Move", UI.movePt, 2)
    if move then r.renderIndicator(UI.movePt, 2).on(button) else button

  def controls(world: World) =
    renderEval
      .on(renderMove(world.controls.move))
      .on(renderAdd)
      .on(renderDel)
      .on(Render.names(world.names))
      .on(Render.sizes(world.controls.size))
      .on(Render.colors(world.controls.color))
      .on(Render.shapes(world.controls.shape))
      .on(renderBlock(world.controls))

  def formulas(formulas: Formulas) = formulas
    .foldLeft[Image](Image.empty):
      case (image, (formula, result)) =>
        image.above(Imager(formula).beside(Imager(result)))

  def selectedPos(pos: Option[Pos]): Image =
    pos match
      case None => Image.empty
      case Some(pos) =>
        Image
          .rectangle(BoardConverter.blockWidth, BoardConverter.blockHeight)
          .strokeColor(red)
          .strokeWidth(c.SmallStroke)
          .at(BoardConverter.toPoint(pos))

  def blocks(grid: Grid): Image = grid
    .foldLeft[Image](c.Board):
      case (image, (pos, (block, name))) =>
        Imager(block)
          .at(BoardConverter.toPoint(pos))
          .on(image)

extension (d: Double) def isCloseTo(e: Double) = Math.abs(d - e) < 0.0001
