package tarski

trait Converter:
  val blockHeight: Double
  val blockWidth: Double
  val top: Double
  val left: Double
  extension (pos: Pos) def toPoint(using Converter): Point
  extension (point: Point) def toPos(using Converter): Pos

given (dims: Dimensions) => (gs: GridSize) => Converter:
  val blockHeight: Double = dims.h / gs.rows
  val blockWidth: Double = dims.w / gs.cols
  val top: Double = dims.h / 2
  val left: Double = dims.w / 2

  extension (pos: Pos)
    def toPoint(using c: Converter): Point =
      val x = -c.left + (0.5 + pos.col) * c.blockWidth
      val y = c.top - (0.5 + pos.row) * c.blockHeight
      Point(x, y)

  extension (point: Point)
    def toPos(using c: Converter): Pos =
      val row = (c.top - point.y) / c.blockHeight - 0.5
      val col = (c.left + point.x) / c.blockWidth - 0.5
      (row.toInt, col.toInt)
