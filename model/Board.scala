package tarski
package model

/** The chess board that holds the blocks. Used by [[World]].
  *
  * @param grid
  * @param gs
  */
case class Board(grid: NameGrid, gs: GridSize = BoardSize):
  require:
    grid.keys.forall: pos =>
      0 <= pos.row && pos.row < gs.rows && 0 <= pos.col && pos.col < gs.cols

  /** Useful when we only want to update `grid` but not the grid size.
    *
    * @param newGrid
    *   An updated map of position -> (block, name) pairs.
    * @return
    *   A new board with its grid updated and the grid size unchanged.
    */
  def apply(newGrid: NameGrid) = copy(grid = newGrid)
end Board

/** Contains helper methods for [[Board]]. */
object Board:
  /** Converts a user-provided [[Grid]] to a [[Board]] so that it can be internally used by a [[World]].
    *
    * @param grid
    *   The grid of positions mapped to blocks, provided by the user.
    * @return
    *   The same grid that also accounts for the names of blocks (with fake names generated if needed).
    */
  def fromGrid(grid: Grid, gs: GridSize = BoardSize) =
    val nameMap = grid.map: (pos, block) =>
      val name = if block.label.isEmpty then Name.generateFake else block.label
      pos -> (block, name)
    Board(nameMap, gs)

  /** Defines an empty board. Convenient for initializing a [[World]].
    *
    * @return
    *   A board with default [[BoardSize]] and no blocks on it.
    */
  def empty: Board = Board(Map())
end Board
