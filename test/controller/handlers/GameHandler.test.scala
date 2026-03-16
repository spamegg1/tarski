package tarski
package testing

class GameHandlerTest extends munit.FunSuite:
  given c: Constants = Constants(DefaultSize)
  import Shape.*, Sizes.*, Tone.*, Select.*, Choice.*, Commit.*

  private val ba         = Block(Sml, Tri, Lim, "a")
  private val b0         = Block(Mid, Sqr, Blu)
  private val pa         = (row = 1, col = 2)
  private val p0         = (row = 6, col = 3)
  private val p1         = (row = 4, col = 4)
  private val grid: Grid = Map(pa -> ba, p0 -> b0)
  private val f          = fof"∀x ∃y (Mor(x, y) ∨ Abv(y, x))"
  private val game       = Game(f, grid)

  private val a011 = GameHandler.controls((0, 11), game) // True
  private val a111 = GameHandler.controls((1, 11), game) // False
  private val a005 = GameHandler.controls((0, 5), game)  // Left
  private val a105 = GameHandler.controls((1, 5), game)  // Right
  private val a013 = GameHandler.controls((0, 13), game) // Back
  private val a113 = GameHandler.controls((1, 13), game) // OK
  private val a014 = GameHandler.controls((0, 13), game) // Display
  private val apa  = GameHandler.boardPos(pa, game)      // (1, 2) on board
  private val ap0  = GameHandler.boardPos(p0, game)      // (6, 3) on board
  private val ap1  = GameHandler.boardPos(p1, game)      // (4, 4) on board

  test("Clicking True at game start should set commitment to true and advance step"):
    val newMsgs = List(
      "You believe every object [x] satisfies:",
      "∃y (Mor(x, y) ∨ Abv(y, x))",
      "I choose a as my counterexample"
    )
    val newGame = Game(Step(Play(f, Some(true), None, None), newMsgs, Off), List(Step(f)), game.board)
    assertEquals(a011, newGame, "Clicking True should set commit and advance step, but does not")

  test("Clicking False at game start should set commitment to true and advance step"):
    val newMsgs = List(
      "You believe some object [x] falsifies:",
      "∃y (Mor(x, y) ∨ Abv(y, x))",
      "Click on a block, then click OK"
    )
    val newGame = Game(Step(Play(f, Some(false), None, None), newMsgs, Wait), List(Step(f)), game.board)
    assertEquals(a111, newGame, "Clicking True should set commit and advance step, but does not")

  test("Clicking any button other than True/False at game start should do nothing"):
    def msg(button: String) = s"Clicking $button should do nothing, but changes game state"
    assertEquals(a005, game, msg("Left"))
    assertEquals(a105, game, msg("Right"))
    assertEquals(a013, game, msg("Back"))
    assertEquals(a113, game, msg("OK"))
    assertEquals(a014, game, msg("Display"))
    assertEquals(apa, game, msg(s"$pa"))
    assertEquals(ap0, game, msg(s"$p0"))
    assertEquals(ap1, game, msg(s"$p1"))

// You believe some object [y] satisfies:
// Mor(a, y) ∨ Abv(y, a)
// Click on a block, then click OK

// You believe one of these is true:
// Mor(a, b0) or Abv(b0, a)
// Choose a true formula above.

// You lose.
// Mor(a, b0) is false in this world.

// You lose.
// Abv(b0, a) is false in this world.
