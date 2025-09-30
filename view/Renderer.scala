package tarski
package view

object Renderer:
  val renderEval   = Text("Eval").font(TheFont).on(bigButton).at(evalPt)
  val renderMove   = Text("Move").font(TheFont).on(bigButton).at(movePt)
  val renderAdd    = Text("Add").font(TheFont).on(bigButton).at(addPt)
  val renderRemove = Text("Remove").font(TheFont).on(bigButton).at(remPt)

  def renderSizes(using c: Controls)  = Image.empty
  def renderShapes(using c: Controls) = Image.empty
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
