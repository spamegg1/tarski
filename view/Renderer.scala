package tarski
package view

def renderEval(using Constants) = renderButton("Eval", UI.evalPt, 2)
def renderAdd(using Constants)  = renderButton("Add", UI.addPt, 2)
def renderDel(using Constants)  = renderButton("Del", UI.delPt, 2)

def renderBlock(c: Controls)(using Constants) = Imager(Block.fromControls(c)).at(UI.blockPt)

def renderMove(move: Boolean)(using Constants) =
  val button = renderButton("Move", UI.movePt, 2)
  if move then renderIndicator(UI.movePt, 2).on(button) else button

def renderControls(world: World)(using Constants) =
  renderEval
    .on(renderMove(world.controls.move))
    .on(renderAdd)
    .on(renderDel)
    .on(renderNames(world.names))
    .on(renderSizes(world.controls.size))
    .on(renderColors(world.controls.color))
    .on(renderShapes(world.controls.shape))
    .on(renderBlock(world.controls))

def renderFormulas(formulas: Formulas)(using Constants) = formulas
  .foldLeft[Image](Image.empty):
    case (image, (formula, result)) =>
      image.above(Imager(formula).beside(Imager(result)))

def renderSelectedPos(selectedPos: Option[Pos])(using c: Constants): Image =
  selectedPos match
    case None => Image.empty
    case Some(pos) =>
      Image
        .rectangle(BoardConverter.blockWidth, BoardConverter.blockHeight)
        .strokeColor(red)
        .strokeWidth(c.SmallStroke)
        .at(BoardConverter.toPoint(pos))

def renderBlocks(grid: Grid)(using c: Constants): Image = grid
  .foldLeft[Image](c.Board):
    case (image, (pos, (block, name))) =>
      Imager(block)
        .at(BoardConverter.toPoint(pos))
        .on(image)

def render(world: World)(using c: Constants): Image =
  renderSelectedPos(world.controls.pos)
    .on(renderBlocks(world.grid))
    .at(c.BoardOrigin)
    .on(renderControls(world).at(c.ControlsOrigin))
    .on(renderFormulas(world.formulas).at(c.FormulasOrigin))

extension (d: Double) def isCloseTo(e: Double) = Math.abs(d - e) < 0.0001
