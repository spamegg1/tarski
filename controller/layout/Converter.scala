package tarski
package controller

import constants.Constants, Constants.{Dims, GridSize, UISize, BoardSize}
import model.*

/** Converts between [[model.Pos]] and [[doodle.core.Point]] on a rectangle that is divided into a grid of rows and
  * columns. The grid can be: the chess board, the UI controls, or the formula display.
  *
  * @param dims
  *   the total height and width of the grid.
  * @param gs
  *   the numbers of rows and columns of the grid.
  */
case class Converter(dims: Dims, gs: GridSize):
  import doodle.core.Point

  /** The height of one block unit on the grid. */
  val blockHeight = dims.h / gs.rows

  /** The width of one block unit on the grid. */
  val blockWidth = dims.w / gs.cols

  /** The top y-coordinate of the grid. */
  val top = dims.h / 2

  /** The left-most x-coordinate of the grid. */
  val left = -dims.w / 2

  /** Converts [[model.Pos]] to [[doodle.core.Point]] on a grid with given dimensions and grid size. Used by [[view]] to
    * calculate the center positions of buttons to be rendered to the screen. Some buttons can be wider and / or taller,
    * in which cases the center needs to be shifted right and / or down slightly.
    *
    * @param pos
    *   The integer positions of the point inside the grid.
    * @param xFactor
    *   The horizontal size scaling factor of the button we want to convert for. Can be 1 for normal sized buttons or 2
    *   for wider buttons (center is shifted right).
    * @param yFactor
    *   The vertical size scaling factor of the button we want to convert for. Can be 1 for normal sized buttons or 2
    *   for taller buttons (center is shifted down).
    * @return
    *   The Cartesian coordinates corresponding to `pos`.
    */
  def posToPoint(xFactor: Int, yFactor: Int)(pos: Pos): Point =
    val x = left + (0.5 * xFactor + pos.col) * blockWidth
    val y = top - (0.5 * yFactor + pos.row) * blockHeight
    Point(x, y)

  /** Converts [[model.Pos]] to [[doodle.core.Point]] for normal sized buttons. */
  val toPoint = posToPoint(1, 1)

  /** Converts [[model.Pos]] to [[doodle.core.Point]] for 2x wide buttons, with center shifted to the right. */
  val toPointX = posToPoint(2, 1)

  /** Converts [[model.Pos]] to [[doodle.core.Point]] for 2x wide and tall buttons, with center shifted down and to the
    * right.
    */
  val toPointXY = posToPoint(2, 2)

  /** Converts [[doodle.core.Point]] to [[model.Pos]]. All `Point`s inside the same block unit are converted to the same
    * `Pos`.
    *
    * @param point
    *   The Cartesian coordinates of the point inside the grid.
    * @return
    *   The integer grid positions that correspond to the point.
    */
  def toPos(point: Point): Pos =
    val row = (top - point.y) / blockHeight
    val col = (-left + point.x) / blockWidth
    (row.toInt, col.toInt)
end Converter

/** Contains [[Converter]] instances for the board and the user interface, along with a mapping between grid positions
  * and UI control buttons.
  */
object Converter:
  /** Converts between [[model.Pos]] and [[doodle.core.Point]] on the chess board. Used by [[React]] and
    * [[view.BoardRenderer]].
    */
  def board(using c: Constants) = Converter(c.BoardDims, BoardSize)

  /** Converts between [[model.Pos]] and [[doodle.core.Point]] for the user interface controls. Used in [[view]]. */
  def ui(using c: Constants) = Converter(c.UIDims, UISize)

  /** Used in [[WorldHandler]] to look up which grid position on the user interface controls corresponds to which
    * button.
    */
  val uiMap =
    Map[Pos, Click](
      (0, 0)  -> Action.Eval,
      (0, 1)  -> Action.Eval,
      (0, 2)  -> Action.Add,
      (0, 3)  -> Action.Add,
      (0, 4)  -> Letter.A,
      (0, 5)  -> Letter.B,
      (0, 6)  -> Letter.C,
      (0, 7)  -> Letter.D,
      (0, 8)  -> Letter.E,
      (0, 9)  -> Letter.F,
      (0, 10) -> Tone.Blu,
      (0, 11) -> Tone.Lim,
      (0, 12) -> Tone.Red,
      (0, 13) -> Rotation.Left,
      (0, 14) -> Action.Icon,
      (0, 15) -> Action.Icon,
      (1, 0)  -> Action.Move,
      (1, 1)  -> Action.Move,
      (1, 2)  -> Action.Del,
      (1, 3)  -> Action.Del,
      (1, 4)  -> Sizes.Sml,
      (1, 5)  -> Sizes.Sml,
      (1, 6)  -> Sizes.Mid,
      (1, 7)  -> Sizes.Mid,
      (1, 8)  -> Sizes.Big,
      (1, 9)  -> Sizes.Big,
      (1, 10) -> Shape.Tri,
      (1, 11) -> Shape.Sqr,
      (1, 12) -> Shape.Cir,
      (1, 13) -> Rotation.Right,
      (1, 14) -> Action.Icon,
      (1, 15) -> Action.Icon
    )

  /** Used in [[GameHandler]] to look up which grid position on the user interface controls corresponds to which button.
    */
  val gameMap =
    Map[Pos, GameClick](
      (0, 0)  -> Choice.Left,
      (0, 1)  -> Choice.Left,
      (0, 2)  -> Choice.Left,
      (0, 3)  -> Choice.Left,
      (0, 4)  -> Choice.Left,
      (0, 5)  -> Choice.Left,
      (0, 6)  -> Choice.Left,
      (0, 7)  -> Choice.Left,
      (0, 8)  -> Choice.Left,
      (0, 9)  -> Choice.Left,
      (0, 10) -> Choice.Left,
      (0, 11) -> Commit.True,
      (0, 12) -> Commit.True,
      (0, 13) -> GameAction.Back,
      (0, 14) -> GameAction.Display,
      (0, 15) -> GameAction.Display,
      (1, 0)  -> Choice.Right,
      (1, 1)  -> Choice.Right,
      (1, 2)  -> Choice.Right,
      (1, 3)  -> Choice.Right,
      (1, 4)  -> Choice.Right,
      (1, 5)  -> Choice.Right,
      (1, 6)  -> Choice.Right,
      (1, 7)  -> Choice.Right,
      (1, 8)  -> Choice.Right,
      (1, 9)  -> Choice.Right,
      (1, 10) -> Choice.Right,
      (1, 11) -> Commit.False,
      (1, 12) -> Commit.False,
      (1, 13) -> GameAction.OK,
      (1, 14) -> GameAction.Display,
      (1, 15) -> GameAction.Display
    )
end Converter
