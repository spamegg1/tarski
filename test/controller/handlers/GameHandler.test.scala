package tarski
package testing

class GameHandlerTest extends munit.FunSuite:
  given c: Constants = Constants(DefaultSize)
  import GameHandlerTestData.*

  def msg(button: String) = s"Clicking $button should not do anyhing, but does"

  // True
  test("Clicking True at game start should set commitment to true and advance step"):
    val msgA011 = "Clicking True should set true/Off and advance step, but does not"
    assertEquals(a011, gameA011, msgA011)

  // False
  test("Clicking False at game start should set commitment to false, Select to Wait and advance step"):
    val msgA111 = "Clicking True should set false/Wait and advance step, but does not"
    assertEquals(a111, gameA111, msgA111)

  // Any other button
  test("Clicking any button other than True/False at game start should do nothing"):
    assertEquals(a005, game, msg("Left"))
    assertEquals(a105, game, msg("Right"))
    assertEquals(a013, game, msg("Back"))
    assertEquals(a113, game, msg("OK"))
    assertEquals(a014, game, msg("Display"))
    assertEquals(apa, game, msg(s"$pa"))
    assertEquals(ap0, game, msg(s"$p0"))
    assertEquals(ap1, game, msg(s"$p1"))

  // True + OK
  test("Clicking OK after computer chose a counterexample should set Wait and advance step"):
    val msgB113 = "Clicking OK should set Wait and advance step, but does not"
    assertEquals(b113, gameB113, msgB113)

  // True + Back
  test("Clicking Back after committing to True should rewind game to beginning"):
    val msg = "Clicking Back after True should rewind step to game start, but does not"
    assertEquals(b013, game, msg)

  // True + any other button
  test("Clicking any button other than OK after committing to True should do nothing"):
    assertEquals(b011, a011, msg("True"))
    assertEquals(b111, a011, msg("False"))
    assertEquals(b005, a011, msg("Left"))
    assertEquals(b105, a011, msg("Right"))
    assertEquals(b014, a011, msg("Display"))
    assertEquals(bpa, a011, msg(s"$pa"))
    assertEquals(bp0, a011, msg(s"$p0"))
    assertEquals(bp1, a011, msg(s"$p1"))

  // False + Back
  test("Clicking Back after committing to False should rewind game to beginning"):
    val msg = "Clicking Back after False should rewind step to game start, but does not"
    assertEquals(c013, game, msg)

  // False + block position (named)
  test("Clicking a named block after False should set `On(_)` without advancing step"):
    val msgCpa = s"Clicking $pa should set On w/o advancing step, but does not"
    assertEquals(cpa, gameCpa, msgCpa)

  // False + block position (unnamed)
  test("Clicking an unnamed block after False should set `On(_)` without advancing step"):
    val msgCp0 = s"Clicking $p0 should set On w/o advancing step, but does not"
    assertEquals(cp0, gameCp0, msgCp0)

  // False + empty position
  test("Clicking an empty spot on the board after False should do nothing"):
    val msgCp1 = s"Clicking $p1 should not do something, but does"
    assertEquals(cp1, gameA111, msgCp1)

  // False + any other button
  test("Clicking any button other than OK/pos after committing to False should do nothing"):
    assertEquals(c011, a111, msg("True"))
    assertEquals(c111, a111, msg("False"))
    assertEquals(c005, a111, msg("Left"))
    assertEquals(c105, a111, msg("Right"))
    assertEquals(c113, a111, msg("OK"))
    assertEquals(c014, a111, msg("Display"))

// You believe one of these is true:
// Mor(a, b0) or Abv(b0, a)
// Choose a true formula above.

// You lose.
// Mor(a, b0) is false in this world.

// You lose.
// Abv(b0, a) is false in this world.
