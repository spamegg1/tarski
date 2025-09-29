package tarski
package view

object Renderer:
  def renderNames                        = Image.rectangle(800, 100).fillColor(Gray)
  def renderControls(controls: Controls) = controls.toImage

  def renderFormulas(formulas: Formulas) = formulas
    .foldLeft[Image](Image.empty):
      case (image, (formula, result)) =>
        image.above(Imager(formula).beside(Imager(result)))

  def renderBlocks(grid: Grid): Image = grid
    .foldLeft[Image](Board):
      case (image, (pos, (block, name))) =>
        Imager(block)
          .at(BoardConverter.toPoint(pos))
          .on(image)

  def render(world: World): Image = renderBlocks(world.grid)
    .beside:
      renderNames
        .above(renderControls(world.controls))
        .above(renderFormulas(world.formulas))
