package tarski
package view

object Renderer:
  extension (s: Shape)
    def toImage(size: Double, color: Color): Image = this match
      case Tri => Image.equilateralTriangle(size * TriFactor).fillColor(color)
      case Squ => Image.square(size).fillColor(color)
      case Cir => Image.circle(size).fillColor(color)

  extension (b: Block)
    def toImage: Image =
      Text(b.label)
        .font(TheFont)
        .on(b.shape.toImage(b.size, b.color))

  def renderNames                        = Image.rectangle(800, 100).fillColor(Gray)
  def renderControls(controls: Controls) = controls.toImage

  def renderFormBoxes(formBoxes: List[FormulaBox]) = formBoxes
    .foldLeft[Image](Image.empty):
      case (image, formBox) =>
        image.above(formBox.toImage)

  def renderBlocks(grid: Grid): Image = grid
    .foldLeft[Image](Board):
      case (image, (pos, (block, name))) =>
        block.toImage
          .at(BoardConverter.toPoint(pos))
          .on(image)

  def render(world: World): Image = renderBlocks(world.grid)
    .beside:
      renderNames
        .above(renderControls(world.controls))
        .above(renderFormBoxes(world.formBoxes))
