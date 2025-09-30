package tarski
package controller

def click(p: Point, world: World) =
  if p.x < 0 then
    val pos = BoardConverter.toPos((p - BoardOrigin).toPoint)
    world.handlePos(pos)
  else if p.y > ControlsBottom then
    val pos = ControlsConverter.toPos((p - ControlsOrigin).toPoint)
    handleControls(pos, world)
  else
    val pos = UIConverter.toPos(p)
    world // do nothing for now

def handleControls(pos: Pos, world: World) = world

def tick(world: World): World        = world
def move(point: Point, world: World) = world
def stop(world: World): Boolean      = true
