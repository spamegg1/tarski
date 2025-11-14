package tarski
package controller

def click(p: Point, world: World)(using c: Constants): World =
  if p.x < 0 then
    val pos = BoardConverter.toPos((p - c.BoardOrigin).toPoint)
    handlePos(pos, world)
  else if p.y > c.ControlsBottom then
    val pos = ControlsConverter.toPos((p - c.ControlsOrigin).toPoint)
    handleControls(pos, world)
  else world // do nothing for now

// These do nothing; the world is static except for clicking controls.
def tick(world: World): World        = world
def move(point: Point, world: World) = world
def stop(world: World): Boolean      = false
