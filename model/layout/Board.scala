package tarski
package model

/** The chess board that holds the blocks. Used by [[World]] and [[Game]]. A map of pos -> (block, name) pairs. */
type Board = Map[Pos, (block: Block, name: Name)]

/** This is like the inverse of [[Board]], allowing us to look-up blocks by name instead of position. Used in *
  * [[controller]].
  */
type Panel = Map[Name, (block: Block, pos: Pos)]

/** Contains helper methods for [[Board]]. */
object Board:
  import constants.Constants, Constants.{GridSize, BoardSize}

  extension (b: Board)
    /** Extension method that converts a [[Board]] to a [[Panel]] by inverting the names and positions.
      *
      * @return
      *   The name grid inverted as position -> (block, name).
      */
    def toPanel: Panel = b.map:
      case (pos, (block, name)) => name -> (block, pos)
  end extension

  /** Converts a user-provided [[Grid]] to a [[Board]] so that it can be internally used by a [[World]].
    *
    * @param grid
    *   The grid of positions mapped to blocks, provided by the user.
    * @return
    *   The same grid that also accounts for the names of blocks (with fake names generated if needed).
    */
  def fromGrid(grid: Grid, gs: GridSize = BoardSize) =
    import Grid.toBoard
    require:
      grid.keys.forall: pos =>
        0 <= pos.row && pos.row < gs.rows && 0 <= pos.col && pos.col < gs.cols
    grid.toBoard

  /** Converts a user-provided [[Grid]] to a [[Board]] so that it can be internally used by a [[Game]], where all
    * [[Block]]s are labeled.
    *
    * @param grid
    *   The grid of positions mapped to blocks, provided by the user.
    * @return
    *   The same grid that also accounts for the names of blocks (with fake names generated if needed), added to the
    *   blocks as labels to be rendered.
    */
  def fromGridWithLabels(grid: Grid, gs: GridSize = BoardSize) =
    import Grid.toBoardWithLabels
    require:
      grid.keys.forall: pos =>
        0 <= pos.row && pos.row < gs.rows && 0 <= pos.col && pos.col < gs.cols
    grid.toBoardWithLabels

  /** Defines an empty board. Convenient for initializing a [[World]].
    *
    * @return
    *   A board with no blocks on it.
    */
  def empty: Board = Map()
end Board
