package tarski

def renderNames        = Image.rectangle(800, 100).fillColor(Gray)
def renderBlockCreator = Image.rectangle(800, 100).fillColor(Green)

def renderFormBoxes(formBoxes: List[FormulaBox]) = formBoxes
  .foldLeft[Image](Image.empty):
    case (image, formBox) =>
      image.above(formBox.toImage)

def renderBlocks(world: World)(using Converter): Image = world.grid
  .foldLeft[Image](Board):
    case (image, (pos, (block, name))) =>
      block.toImage
        .at(pos.toPoint)
        .on(image)

def render(world: World)(using Converter): Image = renderBlocks(world)
  .beside(
    renderNames
      .above(renderBlockCreator)
      .above(renderFormBoxes(world.formBoxes))
  )
