package tarski
package controller

def click(p: Point, world: World): World =
  if p.x < 0 then
    val pos = BoardConverter.toPos((p - BoardOrigin).toPoint)
    handlePos(pos, world)
  else if p.y > ControlsBottom then
    val pos = ControlsConverter.toPos((p - ControlsOrigin).toPoint)
    handleControls(pos, world)
  else world // do nothing for now

def handlePos(pos: Pos, world: World): World =
  if world.controls.move then // make sure move is enabled
    world.controls.pos match
      case Some(p) => world.moveBlock(from = p, to = pos)
      case None    => world
  else // move is disabled, select another pos or de-select current
    world.controls.pos match
      case Some(p) if pos == p => world.copy(controls = world.controls.deselectPos)
      case _                   => world.copy(controls = world.controls.selectPos(pos))

def handleControls(pos: Pos, world: World): World = gridControl.get(pos) match
  case None => world
  case Some(value) =>
    value match
      case "Eval" =>
        val results = world.formulas.map: (formula, result) =>
          val bool = eval(formula)(using world.blocks)
          formula -> (if bool then Valid else Invalid)
        world.copy(formulas = results)
      case "Add" =>
        Block.fromControls(world.controls) match
          case None => world
          case Some(block) =>
            world.controls.pos match
              case None    => world
              case Some(p) => world.addBlockAt(p, block)
      case "a" | "b" | "c" | "d" | "e" | "f" =>
        world.controls.name match
          case None       => world
          case Some(name) => world
      case "Del"   => world.removeBlockAt(pos)
      case "Move"  => world.copy(controls = world.controls.toggleMove)
      case "Blue"  => world.copy(controls = world.controls.setColor(Blue))
      case "Green" => world.copy(controls = world.controls.setColor(Green))
      case "Gray"  => world.copy(controls = world.controls.setColor(Gray))
      case "Small" => world.copy(controls = world.controls.setSize(Small))
      case "Mid"   => world.copy(controls = world.controls.setSize(Medium))
      case "Large" => world.copy(controls = world.controls.setSize(Large))
      case "Tri"   => world.copy(controls = world.controls.setShape(Tri))
      case "Squ"   => world.copy(controls = world.controls.setShape(Squ))
      case "Cir"   => world.copy(controls = world.controls.setShape(Cir))
      case "Block" => world

// These do nothing; the world is static except for clicking controls.
def tick(world: World): World        = world
def move(point: Point, world: World) = world
def stop(world: World): Boolean      = false
