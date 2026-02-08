package tarski
package main

/** This method is how a user runs Tarski's world.
  *
  * @param grid
  *   A map from grid positions to blocks. Default is empty.
  * @param formulas
  *   A sequence of first-order formulas.
  * @param scaleFactor
  *   Scales the user interface size. Must be positive. Default is 1.0, which results in a 1600 x 800 window. Provide
  *   values < 1.0 to make it smaller, > 1.0 to make it bigger.
  */
inline def runWorld(inline grid: Grid = Grid.empty, inline formulas: Seq[FOLFormula], scaleFactor: Double = 1.0) =
  val title = s"${Title.show(formulas)} in ${Title.show(grid)}"
  runHelper(title, grid, formulas, scaleFactor)

/** The same as the user-facing [[runWorld]], except that it also handles the window title.
  *
  * @param title
  *   The title for the window, including the names of the world and the sentences currently being evaluated.
  * @param grid
  *   A map from grid positions to blocks. Default is empty.
  * @param formulas
  *   A sequence of first-order formulas.
  * @param scaleFactor
  *   Scales the user interface size. Must be positive. Default is 1.0, which results in a 1600 x 800 window. Provide
  *   values < 1.0 to make it smaller, > 1.0 to make it bigger.
  */
private def runHelper(title: String, grid: Grid, formulas: Seq[FOLFormula], scaleFactor: Double) =
  require(scaleFactor > 0.0)
  given c: Constants = Constants(DefaultSize * scaleFactor)
  val world          = World.from(grid, formulas)
  val render         = WorldRenderer.apply
  Reactor
    .init[World](world)
    .withOnTick(React.tick)
    .withOnMouseClick(React.clickWorld)
    .withOnMouseMove(React.move)
    .withStop(React.stop)
    .withRender(render.all)
    .withTickRate(TickRate)
    .animateWithFrame(c.MainFrame.withTitle(Constants.Title + title))

/** Plays the game.
  *
  * @param grid
  *   A map from grid positions to blocks.
  * @param formula
  *   A first-order formula.
  * @param scaleFactor
  *   Scales the user interface size. Must be positive. Default is 1.0, which results in a 1600 x 800 window. Provide
  *   values < 1.0 to make it smaller, > 1.0 to make it bigger.
  */
inline def playGame(inline grid: Grid, formula: FOLFormula, scaleFactor: Double = 1.0): Unit =
  val title = s"${formula.toUntypedString} in ${Title.show(grid)}"
  playHelper(title, grid, formula, scaleFactor)

/** Same as the user-facing [[playGame]], except that it also handles the window title.
  *
  * @param title
  *   The title for the window, including the names of the world and the sentence currently being evaluated.
  * @param grid
  *   A map from grid positions to blocks.
  * @param formula
  *   A first-order formula.
  * @param scaleFactor
  *   Scales the user interface size. Must be positive. Default is 1.0, which results in a 1600 x 800 window. Provide
  *   values < 1.0 to make it smaller, > 1.0 to make it bigger.
  */
private def playHelper(title: String, grid: Grid, formula: FOLFormula, scaleFactor: Double) =
  require(scaleFactor > 0.0)
  given c: Constants = Constants(DefaultSize * scaleFactor)
  val game           = Game(formula, grid)
  val render         = GameRenderer.apply
  Reactor
    .init[Game](game)
    .withOnTick(React.tick)
    .withOnMouseClick(React.clickGame)
    .withOnMouseMove(React.move)
    .withStop(React.stop)
    .withRender(render.all)
    .withTickRate(TickRate)
    .animateWithFrame(c.MainFrame.withTitle(Constants.Title + title))

/** An example of how Tarski's world is used.
  *
  * We need a grid (positions -> blocks), and a list of formulas, then call [[main.runWorld]] with them.
  *
  * To see the example, you can execute [[Example.runExample]].
  */
object Example:
  import Shape.*, Sizes.*, Tone.*

  /** An example grid with 4 blocks. Two of them are named. */
  private val exampleGrid: Grid = Map(
    (1, 2) -> Block(Sml, Tri, Lim, "a"),
    (4, 3) -> Block(Mid, Tri, Blu),
    (5, 6) -> Block(Big, Cir, Red, "d"),
    (6, 3) -> Block(Sml, Sqr, Blu)
  )

  /** An example list of first-order formulas, showcasing all logical connectives. */
  private val exampleSentences = Seq(
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

  private val exampleSentence = fof"∀x ∃y (More(x, y) ∨ Abv(y, x))"

  /** Runs the example world. */
  @main
  def runExample = runWorld(exampleGrid, exampleSentences)

  /** Plays the example game. */
  @main
  def playExample = playGame(exampleGrid, exampleSentence)
