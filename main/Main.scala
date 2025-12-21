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
    .withOnMouseClick(React.clickWorld)
    .withOnMouseMove(React.move)
    .withStop(React.stop)
    .withRender(render.all)
    .withTickRate(TickRate)
    .animateWithFrame(c.MainFrame)

def playGame(grid: Grid, formula: FOLFormula, scaleFactor: Double = 1.0): Unit =
  require(scaleFactor > 0.0)
  // val nameMap        = grid.toNameGrid.toNameMap
  given c: Constants = Constants(DefaultSize * scaleFactor)
  val game: Game     = ???
  val render         = new Render
  Reactor
    .init[Game](game)
    .withOnTick(React.tick)
    .withOnMouseClick(???)
    .withOnMouseMove(React.move)
    .withStop(React.stop)
    .withRender(???)
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
    (1, 2) -> Block(Sml, Tri, Lim, "a"),
    (4, 3) -> Block(Mid, Tri, Blu),
    (5, 6) -> Block(Big, Cir, Red, "d"),
    (6, 3) -> Block(Sml, Sqr, Blu)
  )

  /** An example list of first-order formulas, showcasing all logical connectives. */
  private val formulas = Seq(
    fof"¬(∃x Big(x))",
    fof"∀x Sqr(x) ∨ Abv(a, d)",
    fof"∀x ¬ Cir(x)",
    fof"¬(∀x (Sml(x) ∨ Left(x, a)))",
    fof"∃x (Tri(x) → Rgt(x, d))",
    fof"∀x (¬(Shap(c, x) ∨ Less(x, c)) → ¬Tone(x, c))",
    fof"a = b",
    fof"∀x ∃y More(x, y)",
    fof"c != d",
    fof"∀x (Sqr(x) ∨ Tri(x))",
    fof"∀x (Big(x) ∨ Sqr(x))",
    fof"∃x (Tri(x) ∧ Mid(x))",
    fof"¬(∃x (Cir(x) → Sml(x)))",
    fof"∃y (Sqr(y) <-> Sml(y))",
    fof"Mid(a)",
    fof"Happy(c)" // error
  )

  private val formula = fof"∀x ∃y More(x, y)"

  /** Runs the example world. */
  @main
  def runExample = runWorld(grid, formulas)

  /** Plays the example game. */
  @main
  def playExample = playGame(grid, formula)
