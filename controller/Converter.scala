package tarski
package controller

trait Converter(dims: Dimensions, gs: GridSize):
  val blockHeight: Double = dims.h / gs.rows
  val blockWidth: Double  = dims.w / gs.cols
  val top: Double         = dims.h / 2
  val left: Double        = -dims.w / 2
  def toPoint(pos: Pos): Point =
    val x = left + (0.5 + pos.col) * blockWidth
    val y = top - (0.5 + pos.row) * blockHeight
    Point(x, y)
  def toPos(point: Point): Pos =
    val row = (top - point.y) / blockHeight  // - 0.5
    val col = (-left + point.x) / blockWidth // - 0.5
    (row.toInt, col.toInt)

object Converter:
  object UIConverter       extends Converter(UIDimensions, UIGridSize)
  object BoardConverter    extends Converter(BoardDimensions, BoardGridSize)
  object ControlsConverter extends Converter(ControlsDimensions, ControlsGridSize)

  def convertPointConditionally(p: Point): Pos =
    if p.x < 0 then BoardConverter.toPos((p - BoardOrigin).toPoint)
    else if p.y > ControlsBottom then
      ControlsConverter.toPos((p - ControlsOrigin).toPoint)
    else UIConverter.toPos(p)
