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
      case Some(p) if pos == p => world.deselectPos
      case _                   => world.selectPos(pos)

def handleControls(pos: Pos, world: World): World = gridControl.get(pos) match
  case None => world
  case Some(value) =>
    value match
      case "Eval" =>
        val results = world.formulas.map: (formula, result) =>
          val bool = eval(formula)(using world.blocks)
          formula -> (if bool then Valid else Invalid)
        world.updateFormulas(results)
      case "Add"   => world
      case "a"     => world
      case "b"     => world
      case "c"     => world
      case "d"     => world
      case "e"     => world
      case "f"     => world
      case "Blue"  => world
      case "Green" => world
      case "Gray"  => world
      case "Block" => world
      case "Move"  => world
      case "Del"   => world
      case "Small" => world
      case "Mid"   => world
      case "Large" => world
      case "Tri"   => world
      case "Squ"   => world
      case "Cir"   => world

// These do nothing; the world is static except for clicking controls.
def tick(world: World): World        = world
def move(point: Point, world: World) = world
def stop(world: World): Boolean      = false
