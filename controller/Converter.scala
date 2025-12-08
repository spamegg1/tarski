package tarski
package controller

/** Converts between [[model.Pos]] and [[doodle.core.Point]] on a rectangle that is divided into a grid of rows and
  * columns. The grid can be: the chess board, the UI controls, or the formula display.
  *
  * @param dims
  *   the total height and width of the grid.
  * @param gs
  *   the numbers of rows and columns of the grid.
  */
case class Converter(dims: Dims, gs: GridSize):
  /** The height of one block unit on the grid. */
  val blockHeight = dims.h / gs.rows

  /** The width of one block unit on the grid. */
  val blockWidth = dims.w / gs.cols

  /** The top y-coordinate of the grid. */
  val top = dims.h / 2

  /** The left-most x-coordinate of the grid. */
  val left = -dims.w / 2

  /** Converts [[model.Pos]] to [[doodle.core.Point]] on a grid with given dimensions and grid size.
    *
    * @param pos
    *   The integer positions of the point inside the grid.
    * @return
    *   The Cartesian coordinates corresponding to `pos`.
    */
  def toPoint(pos: Pos): Point =
    val x = left + (0.5 + pos.col) * blockWidth
    val y = top - (0.5 + pos.row) * blockHeight
    Point(x, y)

  /** Converts [[model.Pos]] to [[doodle.core.Point]] on a grid with given dimensions and grid size. Used for buttons
    * that are wider, so the center is slightly shifted right.
    *
    * @param pos
    *   The integer positions of the point inside the grid.
    * @return
    *   The Cartesian coordinates corresponding to `pos`, slightly shifted right for a wider button so that it is
    *   centered correctly.
    */
  def toPointX(pos: Pos): Point =
    val x = left + (1.0 + pos.col) * blockWidth
    val y = top - (0.5 + pos.row) * blockHeight
    Point(x, y)

  /** Converts [[model.Pos]] to [[doodle.core.Point]] on a grid with given dimensions and grid size. Used for buttons
    * that are wider and taller, so the center is slightly shifted down and right.
    *
    * @param pos
    *   The integer positions of the point inside the grid.
    * @return
    *   The Cartesian coordinates corresponding to `pos`, slightly shifted down and right for a wider and taller button
    *   so that it is centered correctly.
    */
  def toPointXY(pos: Pos): Point =
    val x = left + (1.0 + pos.col) * blockWidth
    val y = top - (1.0 + pos.row) * blockHeight
    Point(x, y)

  /** Converts [[doodle.core.Point]] to [[model.Pos]]. All `Point`s inside the same block unit are converted to the same
    * `Pos`.
    *
    * @param point
    *   The Cartesian coordinates of the point inside the grid.
    * @return
    *   The integer grid positions that corresponds to the point.
    */
  def toPos(point: Point): Pos =
    val row = (top - point.y) / blockHeight
    val col = (-left + point.x) / blockWidth
    (row.toInt, col.toInt)

/** Contains [[Converter]] instances for the board and the user interface, along with a mapping between grid positions
  * and UI control buttons.
  */
object Converter:
  /** Converts between [[model.Pos]] and [[doodle.core.Point]] on the chess board. Used by [[React.click]] and
    * [[view.Render]].
    */
  def board(using c: Constants) = Converter(c.BoardDims, BoardSize)

  /** Converts between [[model.Pos]] and [[doodle.core.Point]] for the user interface controls. Used in [[view]]. */
  def ui(using c: Constants) = Converter(c.UIDims, UISize)

  /** Used in [[Handler]] to look up which grid position on the user interface controls corresponds to which button. */
  val uiMap = Map[Pos, String](
    (0, 0)  -> "Eval",
    (0, 1)  -> "Eval",
    (0, 2)  -> "Add",
    (0, 3)  -> "Add",
    (0, 4)  -> "a",
    (0, 5)  -> "b",
    (0, 6)  -> "c",
    (0, 7)  -> "d",
    (0, 8)  -> "e",
    (0, 9)  -> "f",
    (0, 10) -> "Blue",
    (0, 11) -> "Green",
    (0, 12) -> "Coral",
    (0, 13) -> "Left",
    (0, 14) -> "Block",
    (0, 15) -> "Block",
    (1, 0)  -> "Move",
    (1, 1)  -> "Move",
    (1, 2)  -> "Del",
    (1, 3)  -> "Del",
    (1, 4)  -> "Small",
    (1, 5)  -> "Small",
    (1, 6)  -> "Mid",
    (1, 7)  -> "Mid",
    (1, 8)  -> "Large",
    (1, 9)  -> "Large",
    (1, 10) -> "Tri",
    (1, 11) -> "Squ",
    (1, 12) -> "Cir",
    (1, 13) -> "Right",
    (1, 14) -> "Block",
    (1, 15) -> "Block"
  )
