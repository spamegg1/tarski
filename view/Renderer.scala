package tarski
package view

val renderEval = renderButton("Eval", evalPt, 2)
val renderAdd  = renderButton("Add", addPt, 2)
val renderDel  = renderButton("Del", delPt, 2)

def renderBlock(c: Controls) = Imager(Block.fromControls(c)).at(blockPt)

def renderMove(move: Boolean) =
  val button = renderButton("Move", movePt, 2)
  if move then renderIndicator(movePt, 2).on(button) else button

def renderControls(world: World) =
  renderEval
    .on(renderMove(world.controls.move))
    .on(renderAdd)
    .on(renderDel)
    .on(renderNames(world.names))
    .on(renderSizes(world.controls.size))
    .on(renderColors(world.controls.color))
    .on(renderShapes(world.controls.shape))
    .on(renderBlock(world.controls))

def renderFormulas(formulas: Formulas) = formulas
  .foldLeft[Image](Image.empty):
    case (image, (formula, result)) =>
      image.above(Imager(formula).beside(Imager(result)))

def renderSelectedPos(selectedPos: Option[Pos]): Image = selectedPos match
  case None => Image.empty
  case Some(pos) =>
    Image
      .rectangle(BoardConverter.blockWidth, BoardConverter.blockHeight)
      .strokeColor(red)
      .strokeWidth(SmallStroke)
      .at(BoardConverter.toPoint(pos))

def renderBlocks(grid: Grid): Image = grid
  .foldLeft[Image](Board):
    case (image, (pos, (block, name))) =>
      Imager(block)
        .at(BoardConverter.toPoint(pos))
        .on(image)

def render(world: World): Image =
  renderSelectedPos(world.controls.pos)
    .on(renderBlocks(world.grid))
    .at(BoardOrigin)
    .on(renderControls(world).at(ControlsOrigin))
    .on(renderFormulas(world.formulas).at(FormulasOrigin))

extension (d: Double) def isCloseTo(e: Double) = Math.abs(d - e) < 0.0001
