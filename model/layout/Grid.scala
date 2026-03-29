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
    /** Converts given `grid` to a [[NameGrid]] by generating fake names for unlabeled [[Block]]s.
      *
      * @return
      *   A `NameGrid` version of this `grid`.
      */
    def toBoard: Board = grid.map: (pos, block) =>
      val name = if block.label.isEmpty then Name.generateFake else block.label
      pos -> (block, name)

    /** Converts given `grid` to a [[NameGrid]] by generating fake names for unlabeled [[Block]]s and adding these
      * labels to the blocks. This is needed by [[Game]] since all blocks need a label to be rendered during the game.
      *
      * @return
      *   A `NameGrid` version of this `grid` where all blocks are labeled with fake generated names if needed.
      */
    def toBoardWithLabels: Board = grid.map: (pos, block) =>
      val name         = if block.label.isEmpty then Name.generateFake else block.label
      val labeledBlock = block.copy(label = name)
      pos -> (labeledBlock, name)
  end extension
end Grid

export Grid.*
