package tarski
package model

/** A type alias for the union of all 3 attributes of a [[Block]]: size, shape, tone. This helps to handle updating a
  * block in a generic, uniform way, reducing repeated code and logic.
  */
type Attr = Sizes | Shape | Tone

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

/** The core grid type used by [[Block]] and subsequently [[World]]. */
type NameGrid = Map[Pos, (block: Block, name: Name)]

extension (grid: NameGrid)
  /** Extension method that converts a [[NameGrid]] to a [[NameMap]] by inverting the names and positions.
    *
    * @return
    *   The grid inverted as position -> (block, name).
    */
  def toNameMap: NameMap = grid.map:
    case (pos, (block, name)) => name -> (block, pos)

/** This is like the inverse of [[Board]], allowing us to look-up blocks by name instead of position. Needed for
  * [[controller.Interpreter.eval]].
  */
type NameMap = Map[Name, (block: Block, pos: Pos)]

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
