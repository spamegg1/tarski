package tarski
package view

val renderEval = renderButton("Eval", evalPt, 2)
val renderMove = renderButton("Move", movePt, 2)
val renderAdd  = renderButton("Add", addPt, 2)
val renderDel  = renderButton("Del", delPt, 2)

def renderBlock(using c: Controls) = Imager(Block.fromControls(c)).at(blockPt)

def renderControls(using Controls) =
  renderEval
    .on(renderMove)
    .on(renderAdd)
    .on(renderDel)
    .on(renderNames)
    .on(renderSizes)
    .on(renderColors)
    .on(renderShapes)
    .on(renderBlock)

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
    .on(renderControls(using world.controls).at(ControlsOrigin))
    .on(renderFormulas(world.formulas).at(FormulasOrigin))

extension (d: Double) def isCloseTo(e: Double) = Math.abs(d - e) < 0.0001
