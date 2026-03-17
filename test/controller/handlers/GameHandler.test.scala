package tarski
package testing

class GameHandlerTest extends munit.FunSuite:
  given c: Constants = Constants(DefaultSize)
  import GameHandlerTestData.*, Select.*

  def msg(button: String) = s"Clicking $button should not do anyhing, but does"

  // Clicking True
  test("Clicking True at game start should set commitment to true and advance step"):
    val msgA011 = "Clicking True should set true/Off and advance step, but does not"
    assertEquals(a011, gameA011, msgA011)

  // Clicking False
  test("Clicking False at game start should set commitment to false, Select to Wait and advance step"):
    val msgA111 = "Clicking True should set false/Wait and advance step, but does not"
    assertEquals(a111, gameA111, msgA111)

  // Clicking any other button
  test("Clicking any button other than True/False at game start should do nothing"):
    assertEquals(a005, game, msg("Left"))
    assertEquals(a105, game, msg("Right"))
    assertEquals(a013, game, msg("Back"))
    assertEquals(a113, game, msg("OK"))
    assertEquals(a014, game, msg("Display"))
    assertEquals(apa, game, msg(s"$pa"))
    assertEquals(ap0, game, msg(s"$p0"))
    assertEquals(ap1, game, msg(s"$p1"))

  // Clicking True + OK
  test("Clicking OK after computer chose a counterexample should set Wait and advance step"):
    val msgB113 = "Clicking OK should set Wait and advance step, but does not"
    assertEquals(b113, gameB113, msgB113)

  // Clicking True + Back
  test("Clicking Back after committing to True should rewind game to beginning"):
    val msg = "Clicking Back after True should rewind step to game start, but does not"
    assertEquals(b013, game, msg)

  // Clicking True + any other button
  test("Clicking any button other than OK after committing to True should do nothing"):
    assertEquals(b011, a011, msg("True"))
    assertEquals(b111, a011, msg("False"))
    assertEquals(b005, a011, msg("Left"))
    assertEquals(b105, a011, msg("Right"))
    assertEquals(b014, a011, msg("Display"))
    assertEquals(bpa, a011, msg(s"$pa"))
    assertEquals(bp0, a011, msg(s"$p0"))
    assertEquals(bp1, a011, msg(s"$p1"))

  // Clicking False + Back
  test("Clicking Back after committing to False should rewind game to beginning"):
    val msg = "Clicking Back after False should rewind step to game start, but does not"
    assertEquals(c013, game, msg)

  // Clicking False + block position (named)
  test("Clicking a named block after False should set `On(_)` without advancing step"):
    val msgCpa = s"Clicking $pa should set ${On(pa)} w/o advancing step, but does not"
    assertEquals(cpa, gameCpa, msgCpa)

  // Clicking False + block position (unnamed)
  test("Clicking an unnamed block after False should set `On(_)` without advancing step"):
    val msgCp0 = s"Clicking $p0 should set ${On(p0)} w/o advancing step, but does not"
    assertEquals(cp0, gameCp0, msgCp0)

  // Clicking False + empty position
  test("Clicking an empty spot on the board after False should do nothing"):
    val msgCp1 = s"Clicking $p1 should not do something, but does"
    assertEquals(cp1, gameA111, msgCp1)

  // Clicking False + any other button
  test("Clicking any button other than OK/pos after committing to False should do nothing"):
    assertEquals(c011, a111, msg("True"))
    assertEquals(c111, a111, msg("False"))
    assertEquals(c005, a111, msg("Left"))
    assertEquals(c105, a111, msg("Right"))
    assertEquals(c113, a111, msg("OK"))
    assertEquals(c014, a111, msg("Display"))

  // Clicking True + OK + pa
  test("Clicking a named block after True + OK should set `On(_)` without advancing step"):
    val msgDpa = s"Clicking $pa should set ${On(pa)} w/o advancing step, but does not"
    assertEquals(dpa, gameDpa, msgDpa)

  // Clicking True + OK + p0
  test("Clicking an unnamed block after True + OK should set `On(_)` without advancing step"):
    val msgDp0 = s"Clicking $p0 should set ${On(p0)} w/o advancing step, but does not"
    assertEquals(dp0, gameDp0, msgDp0)

  // Clicking True + OK + p1
  test("Clicking an empty spot on the board after True + OK should do nothing"):
    val msgDp1 = s"Clicking $p1 should not do something, but does"
    assertEquals(dp1, gameB113, msgDp1)

  // Clicking True + OK + any other button (except Back, which we won't re-test here)
  test("Clicking any button other than Back/pos after True + OK should do nothing"):
    assertEquals(d011, gameB113, msg("True"))
    assertEquals(d111, gameB113, msg("False"))
    assertEquals(d005, gameB113, msg("Left"))
    assertEquals(d105, gameB113, msg("Right"))
    assertEquals(d113, gameB113, msg("OK"))
    assertEquals(d014, gameB113, msg("Display"))

  // Clicking False + (1, 2) + OK
  test("Clicking OK after False + (1,2) should substitute name into formula and advance step"):
    val msgE113 = s"Clicking OK should sub name `a` formula and advance step, but does not"
    assertEquals(e113, gameE113, msgE113)

// You believe one of these is true:
// Mor(a, b0) or Abv(b0, a)
// Choose a true formula above.

// You lose.
// Mor(a, b0) is false in this world.

// You lose.
// Abv(b0, a) is false in this world.

// ---

// You believe both are false:
// Mor(a, a) and Abv(a, a)
// I choose Abv(a, a) as true

// You win!
// Abv(a, a) is false in this world.
