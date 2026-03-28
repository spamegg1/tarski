package tarski
package controller

/** Class to rotate any given grid's positions 90 degrees clockwise or counter-clockwise.
  *
  * @param gs
  *   A [[GridSize]] instance; the number of rows and columns of the grid we want to rotate.
  */
case class Rotator(gs: GridSize):
  /** Rotates the given position 90 degrees, based on the given rotation.
    *
    * @param dir
    *   `Rotation.Left` (90 degrees counter-clockwise) or `Rotation.Right` (90 degrees clockwise).
    * @param pos
    *   The integer positions of the point inside the grid.
    * @return
    *   The integer positions of the point obtained by rotating the grid 90 degrees clockwise or counter-clockwise.
    */
  def rotate(dir: Rotation)(pos: Pos): Pos = dir match
    case Rotation.Left  => (row = gs.cols - pos.col - 1, col = pos.row)
    case Rotation.Right => (row = pos.col, col = gs.rows - pos.row - 1)
end Rotator

/** Contains a useful instance of [[Rotator]]. */
object Rotator:
  /** Rotator for the chess board.
    *
    * @return
    *   An instance of [[Rotator]] to be used for rotating the board.
    */
  def board = Rotator(BoardSize)
