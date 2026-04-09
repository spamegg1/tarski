package tarski
package controller

/** Contains methods needed by [[doodle.reactor.Reactor]] to handle mouse clicks and moves, world "ticks", and the
  * stopping condition. Used in [[main]].
  */
object React:
  import doodle.core.Point, constants.Constants, model.{Game, World}

  /** Reacts to mouse clicks. Uses [[WorldHandler.boardPos]] and [[WorldHandler.uiButtons]] to do this. Used by
    * [[main.runWorld]].
    *
    * @param p
    *   Point on the interface that the user clicked on.
    * @param world
    *   The current state of the world.
    * @param c
    *   Context parameter, an instance of [[constants.Constants]].
    * @return
    *   New state of the world, updated according to what is clicked on.
    */
  def clickWorld(p: Point, world: World)(using c: Constants): World =
    if p.x < 0 then
      val pos = Converter.board.toPos((p - c.BoardOrigin).toPoint)
      WorldHandler.boardPos(pos, world)
    else if p.y > c.UIBottom then
      val pos = Converter.ui.toPos((p - c.UIOrigin).toPoint)
      WorldHandler.uiButtons(pos, world)
    else world

  /** Reacts to mouse clicks. Uses [[GameHandler.boardPos]] and [[GameHandler.controls]] to do this. Used by
    * [[main.playGame]].
    *
    * @param p
    *   Point on the interface that the user clicked on.
    * @param game
    *   The current state of the game.
    * @param c
    *   Context parameter, an instance of [[constants.Constants]].
    * @return
    *   New state of the game, updated according to what is clicked on.
    */
  def clickGame(p: Point, game: Game)(using c: Constants): Game =
    if p.x < 0 then
      val pos = Converter.board.toPos((p - c.BoardOrigin).toPoint)
      GameHandler.boardPos(pos, game)
    else if p.y > c.UIBottom then
      val pos = Converter.ui.toPos((p - c.UIOrigin).toPoint)
      GameHandler.controls(pos, game)
    else game

  /** Reacts to a "tick" to advance the world. It does nothing, because the program is static except for user clicks.
    * Used by [[main.runWorld]] and [[main.playGame]].
    *
    * @param t
    *   The current state of the [[model.World]] or the [[model.Game]].
    * @return
    *   The same world or game, unchanged.
    */
  def tick[T](t: T): T = t

  /** Reacts to mouse movements. It does nothing, because the program is static except for user clicks. Used by
    * [[main.runWorld]] and [[main.playGame]].
    *
    * @param point
    *   The point to which the mouse cursor is moved.
    * @param t
    *   The current state of the [[model.World]] or the [[model.Game]].
    * @return
    *   The same world or game, unchanged.
    */
  def move[T](point: Point, t: T) = t

  /** Determines when the program stops, which is, never. The user needs to stop the program manually. Used by
    * [[main.runWorld]] and [[main.playGame]].
    *
    * @param t
    *   The current state of the [[model.World]] or the [[model.Game]].
    * @return
    *   false (the program never stops).
    */
  def stop[T](t: T): Boolean = false
end React
