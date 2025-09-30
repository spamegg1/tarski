package tarski
package controller

case class Converter(dims: Dimensions, gs: GridSize):
  val blockHeight: Double = dims.h / gs.rows
  val blockWidth: Double  = dims.w / gs.cols
  val top: Double         = dims.h / 2
  val left: Double        = -dims.w / 2
  def toPoint(pos: Pos): Point =
    val x = left + (0.5 + pos.col) * blockWidth
    val y = top - (0.5 + pos.row) * blockHeight
    Point(x, y)
  def toPointShiftX(pos: Pos): Point =
    val x = left + (1.0 + pos.col) * blockWidth
    val y = top - (0.5 + pos.row) * blockHeight
    Point(x, y)
  def toPointShiftY(pos: Pos): Point =
    val x = left + (0.5 + pos.col) * blockWidth
    val y = top - (1.0 + pos.row) * blockHeight
    Point(x, y)
  def toPos(point: Point): Pos =
    val row = (top - point.y) / blockHeight  // - 0.5
    val col = (-left + point.x) / blockWidth // - 0.5
    (row.toInt, col.toInt)

val UIConverter       = Converter(UIDimensions, UIGridSize)
val BoardConverter    = Converter(BoardDimensions, BoardGridSize)
val ControlsConverter = Converter(ControlsDimensions, ControlsGridSize)

def convertPointConditionally(p: Point): Pos =
  if p.x < 0 then BoardConverter.toPos((p - BoardOrigin).toPoint)
  else if p.y > ControlsBottom then ControlsConverter.toPos((p - ControlsOrigin).toPoint)
  else UIConverter.toPos(p)
