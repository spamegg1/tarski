package tarski
package view

val (wid, hgh) = (ControlsConverter.blockWidth, ControlsConverter.blockHeight)
def button(factor: Int = 1) = Image.rectangle(wid * factor, hgh).fillColor(Gray)

def renderIndicator(point: Point, factor: Int = 1) = Image
  .rectangle(wid * factor, hgh)
  .strokeColor(red)
  .strokeWidth(StrokeW)
  .at(point)

def renderButton(name: String, point: Point, factor: Int = 1) = Text(name)
  .font(TheFont)
  .on(button(factor))
  .at(point)

val renderEval   = renderButton("Eval", evalPt, 2)
val renderMove   = renderButton("Move", movePt, 2)
val renderAdd    = renderButton("Add", addPt, 2)
val renderRemove = renderButton("Remove", remPt, 2)

val renderSmall  = renderButton("Small", smallPt, 2)
val renderMedium = renderButton("Medium", medPt, 2)
val renderLarge  = renderButton("Large", largePt, 2)
val renderSize   = renderSmall on renderMedium on renderLarge

def renderColors(using c: Controls) = Image.empty

def renderBlock(using c: Controls) = Imager(Block.fromControls(c))
def renderNames                    = Image.empty

def renderControls(using Controls) =
  renderEval
    .above(renderMove)
    .beside(renderAdd.above(renderRemove))
    .beside(renderNames.above(renderSizes))
    .beside(renderColors.above(renderShapes))
    .beside(renderBlock)

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
      .strokeWidth(StrokeW)
      .at(BoardConverter.toPoint(pos))

def renderBlocks(grid: Grid): Image = grid
  .foldLeft[Image](Board):
    case (image, (pos, (block, name))) =>
      Imager(block)
        .at(BoardConverter.toPoint(pos))
        .on(image)

def render(world: World): Image =
  renderSelectedPos(world.selectedPos)
    .on(renderBlocks(world.grid))
    .beside:
      renderNames
        .above(renderControls(using world.controls))
        .above(renderFormulas(world.formulas))

extension (d: Double) def isCloseTo(e: Double) = Math.abs(d - e) < 0.0001
