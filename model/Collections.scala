package tarski
package model

/** A type alias for the union of all 3 attributes of a [[Block]]: size, shape, tone. This helps to handle updating a
  * block in a generic, uniform way, reducing repeated code and logic.
  */
type Attr = Sizes | Shape | Tone

/** The type that defines a simpler version of the chess board. It is also the user-facing grid type. */
type Grid = Map[Pos, Block]

/** This is the core grid type used by [[World]]. Users don't directly use it, it's for internal usage. Evaluating
  * formulas requires also knowing the names of objects, so the names are also included.
  */
case class Board(grid: Map[Pos, (block: Block, name: Name)], gs: GridSize = BoardSize):
  require:
    grid.keys.forall: pos =>
      0 <= pos.row && pos.row < gs.rows && 0 <= pos.col && pos.col < gs.cols

  /** Converts a [[Board]] to a [[NameGrid]] by inverting the names and positions.
    *
    * @return
    *   The board's grid inverted as position -> (block, name).
    */
  def toNameGrid: NameGrid = grid.map:
    case (pos, (block, name)) => name -> (block, pos)

  /** Useful when we only want to update `grid` but not the grid size.
    *
    * @param newGrid
    *   An updated map of position -> (block, name) pairs.
    * @return
    *   A new board with its grid updated and the grid size unchanged.
    */
  def apply(newGrid: Map[Pos, (block: Block, name: Name)]) = copy(grid = newGrid)

/** Contains helper methods for the [[Board]] type alias. */
object Board:
  /** Converts a user-provided [[Grid]] to a [[Board]] so that it can be internally used by a [[World]].
    *
    * @param grid
    *   The grid of positions mapped to blocks, provided by the user.
    * @return
    *   The same grid that also accounts for the names of blocks (with fake names generated if needed).
    */
  def fromGrid(g: Grid, gs: GridSize = BoardSize) =
    val grid = g.map: (pos, block) =>
      val name = if block.label.isEmpty then Name.generateFake else block.label
      pos -> (block, name)
    Board(grid, gs)

  /** Defines an empty board. Convenient for initializing a [[World]].
    *
    * @return
    *   A board with default [[BoardSize]] and no blocks on it.
    */
  def empty: Board = Board(Map())

/** Contains helper methods for the [[Grid]] type alias. */
object Grid:
  /** Creates an empty [[Grid]]. Useful for initializing a [[World]]. Used by [[main.runWorld]].
    *
    * @return
    *   an empty [[Grid]].
    */
  def empty: Grid = Map.empty[Pos, Block]

/** This is like the inverse of [[Board]], allowing us to look-up blocks by name instead of position. Needed for
  * [[controller.Interpreter.eval]].
  */
type NameGrid = Map[Name, (block: Block, pos: Pos)]

/** A mapping between first-order formulas and their evaluation results. */
type Formulas = ListMap[FOLFormula, Result]

/** Contains helper methods for the [[Formulas]] type alias. */
object Formulas:
  /** Generates a map where every formula is yet pending, not evaluated.
    *
    * @param fs
    *   A sequence of first-order formulas.
    * @return
    *   A map from formulas to [[Result]]s where each formula is ready to be evaluated.
    */
  def fromSeq(fs: Seq[FOLFormula]): Formulas = ListMap.from:
    fs.map(f => f -> Result.Ready)

extension (formulas: Formulas)
  /** Extension method for the [[Formulas]] type alias that resets evaluation results.
    *
    * @return
    *   The same map, where every formula is set as not evaluated yet.
    */
  def reset = formulas.map((f, _) => f -> Result.Ready)

  /** Extension method for the [[Formulas]] type alias that adds one formula. Convenient for quickly working on
    * examples.
    *
    * @return
    *   The map plus the given formula, not yet evaluated. The results of the other formulas stay the same.
    */
  def add(f: FOLFormula) = formulas + (f -> Result.Ready)
