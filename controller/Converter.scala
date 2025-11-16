package tarski
package controller

case class Converter(dims: Dims, gs: GridSize):
  val blockHeight: Double = dims.h / gs.rows
  val blockWidth: Double  = dims.w / gs.cols
  val top: Double         = dims.h / 2
  val left: Double        = -dims.w / 2

  def toPoint(pos: Pos): Point =
    val x = left + (0.5 + pos.col) * blockWidth
    val y = top - (0.5 + pos.row) * blockHeight
    Point(x, y)

  def toPointX(pos: Pos): Point =
    val x = left + (1.0 + pos.col) * blockWidth
    val y = top - (0.5 + pos.row) * blockHeight
    Point(x, y)

  def toPointY(pos: Pos): Point =
    val x = left + (0.5 + pos.col) * blockWidth
    val y = top - (1.0 + pos.row) * blockHeight
    Point(x, y)

  def toPos(point: Point): Pos =
    val row = (top - point.y) / blockHeight  // - 0.5
    val col = (-left + point.x) / blockWidth // - 0.5
    (row.toInt, col.toInt)

object Converter:
  def board(using c: Constants) = Converter(c.BoardDims, BoardSize)
  def ui(using c: Constants)    = Converter(c.UIDims, UISize)

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
  (0, 12) -> "Gray",
  (0, 13) -> "Block",
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
  (1, 13) -> "Block",
  (1, 14) -> "Block",
  (1, 15) -> "Block"
)
