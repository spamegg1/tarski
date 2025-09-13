package tarski

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
