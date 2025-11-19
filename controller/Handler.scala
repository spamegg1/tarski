package tarski
package controller

object Handler:
  def boardPos(pos: Pos, world: World): World =
    world.controls.posOpt match
      case Some(p) if p == pos =>
        val newControls = world.controls.deselectPos.unsetBlock
        world.copy(controls = newControls)
      case Some(p) if world.controls.move => world.moveBlock(from = p, to = pos)
      case _ =>
        val newControls = world.controls.selectPos(pos).setBlock(world.posGrid.get(pos))
        world.copy(controls = newControls)

  def uiButtons(pos: Pos, world: World): World =
    Converter.uiMap.get(pos) match
      case None => world
      case Some(value) => // make sure a button is clicked
        value match
          case "Eval"                            => handleEval(world)
          case "Add"                             => world.addBlockFromControls
          case "Del"                             => world.removeSelectedBlock
          case "Move"                            => world.toggleMove
          case "Block"                           => world
          case "a" | "b" | "c" | "d" | "e" | "f" => handleName(value, world)
          case "Blue" | "Green" | "Gray"         => handleAttr(value, world)
          case "Small" | "Mid" | "Large"         => handleAttr(value, world)
          case "Tri" | "Squ" | "Cir"             => handleAttr(value, world)
          case _                                 => world

  private def handleEval(world: World): World =
    import Result.*
    val results = world.formulas.map: (formula, result) =>
      var status = Ready
      try
        val bool = eval(formula)(using world.nameGrid)
        status = if bool then Valid else Invalid
      catch case _ => ()
      formula -> status
    world.copy(formulas = results)

  private def handleName(name: String, world: World): World =
    import Status.*
    world.names.get(name) match
      case None => world
      case Some(Available) =>
        world.controls.posOpt match
          case None      => world
          case Some(pos) => world.addNameToBlockAt(pos, name)
      case Some(Occupied) =>
        world.nameGrid.get(name) match
          case None           => world
          case Some((_, pos)) => world.removeNameFromBlockAt(pos)

  private def handleAttr(attr: String, world: World): World =
    val newAttr     = attr.toAttr
    val newControls = world.controls.setAttr(newAttr)
    val newGrid = world.controls.posOpt match
      case None => world.posGrid
      case Some(pos) =>
        world.posGrid.get(pos) match
          case None => world.posGrid
          case Some((block, name)) =>
            val newBlock = block.setAttr(newAttr)
            world.posGrid.updated(pos, (newBlock, name))
    world.copy(controls = newControls, posGrid = newGrid)

  extension (s: String)
    def toAttr: Attr = s match
      case "Blue"  => Tone.Blue
      case "Gray"  => Tone.Gray
      case "Green" => Tone.Green
      case "Small" => Sizes.Small
      case "Mid"   => Sizes.Mid
      case "Large" => Sizes.Large
      case "Tri"   => Shape.Tri
      case "Squ"   => Shape.Squ
      case "Cir"   => Shape.Cir
