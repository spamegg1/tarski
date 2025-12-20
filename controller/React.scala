package tarski
package controller

/** Contains methods needed by [[doodle.reactor.Reactor]] to handle mouse clicks and moves, world "ticks", and the
  * stopping condition. Used in [[main]].
  */
object React:
  /** Reacts to mouse clicks. Uses [[Handler.boardPos]] and [[Handler.uiButtons]] to do this. Used by [[main.runWorld]].
    *
    * @param p
    *   Point on the interface that the user clicked on.
    * @param world
    *   The current state of the world.
    * @param c
    *   Implicit parameter, an instance of [[constants.Constants]].
    * @return
    *   New state of the world, updated according to what is clicked on.
    */
  def click(p: Point, world: World)(using c: Constants): World =
    if p.x < 0 then
      val pos = Converter.board.toPos((p - c.BoardOrigin).toPoint)
      Handler.boardPos(pos, world)
    else if p.y > c.UIBottom then
      val pos = Converter.ui.toPos((p - c.UIOrigin).toPoint)
      Handler.uiButtons(pos, world)
    else world

  /** Reacts to a "tick" to advance the world. It does nothing, because the program is static except for user clicks.
    * Used by [[main.runWorld]].
    *
    * @param world
    *   The current state of the world.
    * @return
    *   The same world, unchanged.
    */
  def tick(world: World): World = world

  /** Reacts to mouse movements. It does nothing, because the program is static except for user clicks. Used by
    * [[main.runWorld]].
    *
    * @param point
    *   The point to which the mouse cursor is moved.
    * @param world
    *   The current state of the world.
    * @return
    *   The same world, unchanged.
    */
  def move(point: Point, world: World) = world

  /** Determines when the program stops, which is, never. The user needs to stop the program manually. Used by
    * [[main.runWorld]].
    *
    * @param world
    *   The current state of the world.
    * @return
    *   false (the program never stops).
    */
  def stop(world: World): Boolean = false
end React
