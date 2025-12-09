package tarski
package controller

/** Class containing methods to rotate any given grid's positions clockwise or counter-clockwise.
  *
  * @param gs
  *   A [[GridSize]] instance; the number of rows and columns of the grid we want to rotate.
  */
case class Rotator(gs: GridSize):
  /** Rotates given position 90 degrees counter-clockwise.
    *
    * @param pos
    *   The integer positions of the point inside the grid.
    * @return
    *   The integer positions of the point obtained by rotating the grid 90 degrees counter-clockwise.
    */
  def rotateLeft(pos: Pos): Pos = (row = gs.cols - pos.col - 1, col = pos.row)

  /** Rotates given position 90 degrees clockwise.
    *
    * @param pos
    *   The integer positions of the point inside the grid.
    * @return
    *   The integer positions of the point obtained by rotating the grid 90 degrees clockwise.
    */
  def rotateRight(pos: Pos): Pos = (row = pos.col, col = gs.rows - pos.row - 1)

  /** Selects the right rotation function based on input. It acts like a partially applied function.
    *
    * @param dir
    *   Can only be "Left" or "Right".
    * @return
    *   `rotateLeft` or `rotateRight`
    */
  def rotate(dir: String): Pos => Pos = dir match
    case "Left" => rotateLeft
    case "Rgt"  => rotateRight

/** Contains useful instances of [[Rotator]]. */
object Rotator:
  /** Rotator for the chess board.
    *
    * @return
    *   An instance of [[Rotator]] to be used for rotating the board.
    */
  def board = Rotator(BoardSize)
