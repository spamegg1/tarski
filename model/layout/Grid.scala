package tarski
package model

/** The type that defines a simpler version of the chess board. It is also the user-facing grid type. */
type Grid = Map[Pos, Block]

/** Contains helper methods for the [[Grid]] type alias. */
object Grid:
  /** Creates an empty [[Grid]]. Useful for initializing a [[World]]. Used by [[main.runWorld]].
    *
    * @return
    *   an empty [[Grid]].
    */
  def empty: Grid = Map.empty[Pos, Block]

extension (grid: Grid)
  def toNameGrid: NameGrid = grid.map: (pos, block) =>
    val name = if block.label.isEmpty then Name.generateFake else block.label
    pos -> (block, name)
