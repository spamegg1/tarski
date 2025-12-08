package tarski
package main

/** This method is how a user runs Tarski's world.
  *
  * @param grid
  *   A map from grid positions to blocks. Default is empty.
  * @param formulas
  *   A sequence of first-order formulas.
  * @param scaleFactor
  *   Used to scale the user interface size. Must be positive. Default is 1.0, which results in a 1600 x 800 window.
  *   Provide values < 1.0 to make it smaller, > 1.0 to make it bigger.
  */
def runWorld(grid: Grid = Grid.empty, formulas: Seq[FOLFormula], scaleFactor: Double = 1.0) =
  require(scaleFactor > 0.0)
  given c: Constants = Constants(DefaultSize * scaleFactor)
  val world          = World.from(grid, formulas)
  val render         = new Render
  Reactor
    .init[World](world)
    .withOnTick(React.tick)
    .withOnMouseClick(React.click)
    .withOnMouseMove(React.move)
    .withStop(React.stop)
    .withRender(render.all)
    .withTickRate(TickRate)
    .animateWithFrame(c.MainFrame)

/** An example of how Tarski's world is used.
  *
  * We need a grid (positions -> blocks), and a list of formulas, then call [[main.runWorld]] with them.
  *
  * To see the example, you can execute [[Example.runExample]].
  */
object Example:
  import Shape.*, Sizes.*, Tone.*

  /** An example grid with 4 blocks. Two of them are named. */
  private val grid: Grid = Map(
    (1, 2) -> Block(Small, Tri, Green, "a"),
    (4, 3) -> Block(Mid, Tri, Blue),
    (5, 6) -> Block(Large, Cir, Coral, "d"),
    (6, 3) -> Block(Small, Squ, Blue)
  )

  /** An example list of first-order formulas, showcasing all logical connectives. */
  private val formulas = Seq(
    fof"¬(∃x Large(x))",
    fof"∀x Squ(x) ∨ Above(a, d)",
    fof"∀x ¬ Cir(x)",
    fof"¬(∀x (Small(x) ∨ Left(x, a)))",
    fof"∃x (Tri(x) → Right(x, d))",
    fof"∀x (¬(Shape(c, x) ∨ Less(x, c)) → ¬Tone(x, c))",
    fof"a = b",
    fof"∀x ∃y More(x, y)",
    fof"c != d",
    fof"∀x (Squ(x) ∨ Tri(x))",
    fof"∀x (Large(x) ∨ Squ(x))",
    fof"∃x (Tri(x) ∧ Mid(x))",
    fof"¬(∃x (Cir(x) → Small(x)))",
    fof"∃y (Squ(y) <-> Small(y))",
    fof"Mid(a)"
  )

  /** Runs the example (also the only entry point into the project). */
  @main
  def runExample = runWorld(grid, formulas)
