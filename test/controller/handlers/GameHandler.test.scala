package tarski
package testing

class GameHandlerTest extends munit.FunSuite:
  given c: Constants = Constants(DefaultSize)
  import Shape.*, Sizes.*, Tone.*, Select.*, Choice.*, Commit.*

  val ba         = Block(Sml, Tri, Lim, "a")
  val b0         = Block(Mid, Sqr, Blu)
  val pa         = (row = 1, col = 2)
  val p0         = (row = 6, col = 3)
  val p1         = (row = 4, col = 4)
  val grid: Grid = Map(pa -> ba, p0 -> b0)
  val f          = fof"∀x ∃y (Mor(x, y) ∨ Abv(y, x))"
  val step0      = Step(f)
  val game       = Game(f, grid)

  val playA011 = Play(f, Some(true), None, None) // after committing to true
  val msgsA011 = List(
    "You believe every object [x] satisfies:",
    "∃y (Mor(x, y) ∨ Abv(y, x))",
    "I choose a as my counterexample"
  )
  val stepA011 = Step(playA011, msgsA011, Off)

  val playA111 = Play(f, Some(false), None, None) // after committing to false
  val msgsA111 = List(
    "You believe some object [x] falsifies:",
    "∃y (Mor(x, y) ∨ Abv(y, x))",
    "Click on a block, then click OK"
  )
  val stepA111 = Step(playA111, msgsA111, Wait)

  val fB113    = fof"∃y (Mor(a, y) ∨ Abv(y, a))"
  val playB113 = Play(fB113, Some(true), None, None) // True + OK
  val msgsB113 = List(
    "You believe some object [y] satisfies:",
    "Mor(a, y) ∨ Abv(y, a)",
    "Click on a block, then click OK"
  )
  val stepB113 = Step(playB113, msgsB113, Wait)

  // Beginning of the game
  val a011 = GameHandler.controls((0, 11), game) // True
  val a111 = GameHandler.controls((1, 11), game) // False
  val a005 = GameHandler.controls((0, 5), game)  // Left
  val a105 = GameHandler.controls((1, 5), game)  // Right
  val a013 = GameHandler.controls((0, 13), game) // Back
  val a113 = GameHandler.controls((1, 13), game) // OK
  val a014 = GameHandler.controls((0, 13), game) // Display
  val apa  = GameHandler.boardPos(pa, game)      // (1, 2) on board
  val ap0  = GameHandler.boardPos(p0, game)      // (6, 3) on board
  val ap1  = GameHandler.boardPos(p1, game)      // (4, 4) on board

  // After clicking True:
  val b011 = GameHandler.controls((0, 11), a011) // True
  val b111 = GameHandler.controls((1, 11), a011) // False
  val b005 = GameHandler.controls((0, 5), a011)  // Left
  val b105 = GameHandler.controls((1, 5), a011)  // Right
  val b013 = GameHandler.controls((0, 13), a011) // Back
  val b113 = GameHandler.controls((1, 13), a011) // OK
  val b014 = GameHandler.controls((0, 13), a011) // Display
  val bpa  = GameHandler.boardPos(pa, a011)      // (1, 2) on board
  val bp0  = GameHandler.boardPos(p0, a011)      // (6, 3) on board
  val bp1  = GameHandler.boardPos(p1, a011)      // (4, 4) on board

  // After clicking False:
  val c011 = GameHandler.controls((0, 11), a111) // True
  val c111 = GameHandler.controls((1, 11), a111) // False
  val c005 = GameHandler.controls((0, 5), a111)  // Left
  val c105 = GameHandler.controls((1, 5), a111)  // Right
  val c013 = GameHandler.controls((0, 13), a111) // Back
  val c113 = GameHandler.controls((1, 13), a111) // OK
  val c014 = GameHandler.controls((0, 13), a111) // Display
  val cpa  = GameHandler.boardPos(pa, a111)      // (1, 2) on board
  val cp0  = GameHandler.boardPos(p0, a111)      // (6, 3) on board
  val cp1  = GameHandler.boardPos(p1, a111)      // (4, 4) on board

  test("Clicking True at game start should set commitment to true and advance step"):
    val newGame = Game(stepA011, List(step0), game.board)
    val msg     = "Clicking True should set true/Off and advance step, but does not"
    assertEquals(a011, newGame, msg)

  test("Clicking False at game start should set commitment to false, Select to Wait and advance step"):
    val newGame = Game(stepA111, List(step0), game.board)
    val msg     = "Clicking True should set false/Wait and advance step, but does not"
    assertEquals(a111, newGame, msg)

  test("Clicking any button other than True/False at game start should do nothing"):
    def msg(button: String) = s"Clicking $button should not do anyhing, but does"
    assertEquals(a005, game, msg("Left"))
    assertEquals(a105, game, msg("Right"))
    assertEquals(a013, game, msg("Back"))
    assertEquals(a113, game, msg("OK"))
    assertEquals(a014, game, msg("Display"))
    assertEquals(apa, game, msg(s"$pa"))
    assertEquals(ap0, game, msg(s"$p0"))
    assertEquals(ap1, game, msg(s"$p1"))

  test("Clicking OK after computer chose a counterexample should set Wait and advance step"):
    val newGame = Game(stepB113, List(stepA011, step0), game.board)
    assertEquals(b113, newGame, "Clicking OK should set Wait and advance step, but does not")

// You believe one of these is true:
// Mor(a, b0) or Abv(b0, a)
// Choose a true formula above.

// You lose.
// Mor(a, b0) is false in this world.

// You lose.
// Abv(b0, a) is false in this world.
