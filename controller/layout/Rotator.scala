package tarski
package controller

import constants.Constants.GridSize

/** Class to rotate any given grid's positions 90 degrees clockwise or counter-clockwise.
  *
  * @param gs
  *   A [[constants.Constants.GridSize]] instance; the number of rows and columns of the grid we want to rotate. After a
  *   rotation, rows and columns swap, so the numbers also have to be swapped.
  */
case class Rotator(var gs: GridSize):
  import model.{Pos, Rotation}

  /** Rotates the given position 90 degrees, based on the given rotation.
    *
    * Has the side effect of swapping the numbers of rows and columns of `gs`.
    *
    * @param dir
    *   `Rotation.Left` (90 degrees counter-clockwise) or `Rotation.Right` (90 degrees clockwise).
    * @param pos
    *   The integer positions of the point inside the grid.
    * @return
    *   The integer positions of the point obtained by rotating the grid 90 degrees clockwise or counter-clockwise.
    */
  def rotate(dir: Rotation)(pos: Pos): Pos =
    val oldGs = gs // save current row, col sizes for this rotation
    gs = (rows = gs.cols, cols = gs.rows) // swap row and col sizes for next rotation
    dir match
      case Rotation.Left  => (row = oldGs.cols - pos.col - 1, col = pos.row)
      case Rotation.Right => (row = pos.col, col = oldGs.rows - pos.row - 1)
end Rotator

/** Contains a useful instance of [[Rotator]]. */
object Rotator:
  /** Rotator for the chess board.
    *
    * @return
    *   An instance of [[Rotator]] to be used for rotating the board.
    */
  def board = Rotator(constants.Constants.BoardSize)
