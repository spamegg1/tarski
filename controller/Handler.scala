package tarski
package controller

object Handler:
  def boardPos(pos: Pos, world: World): World =
    world.controls.pos match
      case Some(p) if p == pos =>
        val newControls = world.controls.deselectPos.unsetBlock
        world.copy(controls = newControls)
      case Some(p) if world.controls.move => world.moveBlock(from = p, to = pos)
      case _ =>
        val newControls = world.controls.selectPos(pos).setBlock(world.grid.get(pos))
        world.copy(controls = newControls)

  def uiButtons(pos: Pos, world: World): World =
    uiMap.get(pos) match
      case None => world
      case Some(value) => // make sure a button is clicked
        value match
          case "Eval"                            => handleEval(world)
          case "Add"                             => world.addBlockFromControls
          case "Del"                             => world.removeSelectedBlock
          case "Move"                            => world.toggleMove
          case "Block"                           => world
          case "a" | "b" | "c" | "d" | "e" | "f" => handleName(value, world)
          case "Blue" | "Green" | "Gray"         => handleColor(value, world)
          case "Small" | "Mid" | "Large"         => handleSize(value, world)
          case "Tri" | "Squ" | "Cir"             => handleShape(value, world)
          case _                                 => world

  private def handleEval(world: World): World =
    import Result.*
    val results = world.formulas.map: (formula, result) =>
      var status = Ready
      try
        val bool = eval(formula)(using world.blocks)
        status = if bool then Valid else Invalid
      catch case _ => ()
      formula -> status
    world.copy(formulas = results)

  private def handleName(name: String, world: World): World =
    import Status.*
    world.names.get(name) match
      case None => world
      case Some(Available) =>
        world.controls.pos match
          case None      => world
          case Some(pos) => world.addNameToBlockAt(pos, name)
      case Some(Occupied) =>
        world.blocks.get(name) match
          case None           => world
          case Some((_, pos)) => world.removeNameFromBlockAt(pos)

  private def handleColor(tone: String, world: World): World =
    val newTone     = tone.toTone
    val newControls = world.controls.setTone(newTone)
    val newGrid = world.controls.pos match
      case None => world.grid
      case Some(pos) =>
        world.grid.get(pos) match
          case None => world.grid
          case Some((block, name)) =>
            val newBlock = block.copy(tone = newTone)
            world.grid.updated(pos, (newBlock, name))
    world.copy(controls = newControls, grid = newGrid)

  private def handleShape(shape: String, world: World): World =
    val newShape    = shape.toShape
    val newControls = world.controls.setShape(newShape)
    val newGrid = world.controls.pos match
      case None => world.grid
      case Some(pos) =>
        world.grid.get(pos) match
          case None => world.grid
          case Some((block, name)) =>
            val newBlock = block.copy(shape = newShape)
            world.grid.updated(pos, (newBlock, name))
    world.copy(controls = newControls, grid = newGrid)

  private def handleSize(size: String, world: World): World =
    val newSize     = size.toSize
    val newControls = world.controls.setSize(newSize)
    val newGrid = world.controls.pos match
      case None => world.grid
      case Some(pos) =>
        world.grid.get(pos) match
          case None => world.grid
          case Some((block, name)) =>
            val newBlock = block.copy(size = newSize)
            world.grid.updated(pos, (newBlock, name))
    world.copy(controls = newControls, grid = newGrid)

  extension (s: String)
    def toTone = s match
      case "Blue"  => Tone.Blue
      case "Gray"  => Tone.Gray
      case "Green" => Tone.Green
    def toSize = s match
      case "Small" => Sizes.Small
      case "Mid"   => Sizes.Mid
      case "Large" => Sizes.Large
    def toShape = s match
      case "Tri" => Shape.Tri
      case "Squ" => Shape.Squ
      case "Cir" => Shape.Cir
