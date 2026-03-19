package tarski
package testing

class GameHandlerTest extends munit.FunSuite:
  import GameHandlerTestData.*, Select.*

  def msg(button: String) = s"Clicking $button should not do anyhing, but does"

  test("Clicking True at game start should set commitment to true and advance step"):
    val msgA011 = "Clicking True should set true/Off and advance step, but does not"
    assertEquals(a011, gameA011, msgA011)

  test("Clicking False at game start should set commitment to false, Select to Wait and advance step"):
    val msgA111 = "Clicking True should set false/Wait and advance step, but does not"
    assertEquals(a111, gameA111, msgA111)

  test("Clicking any button other than True/False at game start should do nothing"):
    assertEquals(a005, game, msg("Left"))
    assertEquals(a105, game, msg("Right"))
    assertEquals(a013, game, msg("Back"))
    assertEquals(a113, game, msg("OK"))
    assertEquals(a014, game, msg("Display"))
    assertEquals(apa, game, msg(s"$pa"))
    assertEquals(ap0, game, msg(s"$p0"))
    assertEquals(ap1, game, msg(s"$p1"))

  test("Clicking OK after computer chose a counterexample should set Wait and advance step"):
    val msgB113 = "Clicking OK should set Wait and advance step, but does not"
    assertEquals(b113, gameB113, msgB113)

  test("Clicking Back after committing to True should rewind game to beginning"):
    val msg = "Clicking Back after True should rewind step to game start, but does not"
    assertEquals(b013, game, msg)

  test("Clicking any button other than Back/OK after committing to True should do nothing"):
    assertEquals(b011, a011, msg("True"))
    assertEquals(b111, a011, msg("False"))
    assertEquals(b005, a011, msg("Left"))
    assertEquals(b105, a011, msg("Right"))
    assertEquals(b014, a011, msg("Display"))
    assertEquals(bpa, a011, msg(s"$pa"))
    assertEquals(bp0, a011, msg(s"$p0"))
    assertEquals(bp1, a011, msg(s"$p1"))

  test("Clicking Back after committing to False should rewind game to beginning"):
    val msg = "Clicking Back after False should rewind step to game start, but does not"
    assertEquals(c013, game, msg)

  test("Clicking a named block after False should set `On(_)` without advancing step"):
    val msgCpa = s"Clicking $pa should set ${On(pa)} w/o advancing step, but does not"
    assertEquals(cpa, gameCpa, msgCpa)

  test("Clicking an unnamed block after False should set `On(_)` without advancing step"):
    val msgCp0 = s"Clicking $p0 should set ${On(p0)} w/o advancing step, but does not"
    assertEquals(cp0, gameCp0, msgCp0)

  test("Clicking an empty spot on the board after False should do nothing"):
    val msgCp1 = s"Clicking $p1 should not do something, but does"
    assertEquals(cp1, gameA111, msgCp1)

  test("Clicking any button other than OK/pos after committing to False should do nothing"):
    assertEquals(c011, a111, msg("True"))
    assertEquals(c111, a111, msg("False"))
    assertEquals(c005, a111, msg("Left"))
    assertEquals(c105, a111, msg("Right"))
    assertEquals(c113, a111, msg("OK"))
    assertEquals(c014, a111, msg("Display"))

  test("Clicking a named block after True + OK should set `On(_)` without advancing step"):
    val msgDpa = s"Clicking $pa should set ${On(pa)} w/o advancing step, but does not"
    assertEquals(dpa, gameDpa, msgDpa)

  test("Clicking an unnamed block after True + OK should set `On(_)` without advancing step"):
    val msgDp0 = s"Clicking $p0 should set ${On(p0)} w/o advancing step, but does not"
    assertEquals(dp0, gameDp0, msgDp0)

  test("Clicking an empty spot on the board after True + OK should do nothing"):
    val msgDp1 = s"Clicking $p1 should not do something, but does"
    assertEquals(dp1, gameB113, msgDp1)

  test("Clicking any button other than Back/pos after True + OK should do nothing"):
    assertEquals(d011, gameB113, msg("True"))
    assertEquals(d111, gameB113, msg("False"))
    assertEquals(d005, gameB113, msg("Left"))
    assertEquals(d105, gameB113, msg("Right"))
    assertEquals(d113, gameB113, msg("OK"))
    assertEquals(d014, gameB113, msg("Display"))

  test("Clicking OK after False + (1,2) should substitute name into formula and advance step"):
    val msgE113 = s"Clicking OK should sub name `a` into formula and advance step, but does not"
    assertEquals(e113, gameE113, msgE113)

  test("Clicking Back after False + (1,2) should rewind back to game beginning"):
    val msgE013 = s"Clicking Back after False + $p0 should rewind step, but does not"
    assertEquals(e013, game, msgE013)

  test("Clicking the same block after False + (1,2) should de-select it and not advance step"):
    val msgEpa = s"Clicking $p0 after False + $p0 should set `Wait`, but does not"
    assertEquals(epa, gameA111, msgEpa)

  test("Clicking a block after False + (1,2) should set `On(_)` and not advance step"):
    val msgEp0 = s"Clicking $p0 after False + $p0 should set `On(6,3)`, but does not"
    assertEquals(ep0, gameEp0, msgEp0)

  test("Clicking an empty spot after False + (1,2) should set `Wait` and not advance step"):
    val msgEpa = s"Clicking $p1 after False + $p0 should set `Wait`, but does not"
    assertEquals(epa, gameA111, msgEpa)

  test("Clicking any button except Back/OK/pos after False + (1,2) should do nothing"):
    assertEquals(e011, gameCpa, msg("True"))
    assertEquals(e111, gameCpa, msg("False"))
    assertEquals(e005, gameCpa, msg("Left"))
    assertEquals(e105, gameCpa, msg("Right"))
    assertEquals(e014, gameCpa, msg("Display"))

  test("Clicking the same block after True+OK+(1,2) should de-select it w/o advancing step"):
    val msgFpa = s"Clicking $pa should switch from ${On(pa)} to Wait w/o advancing step, but does not"
    assertEquals(fpa, gameFpa, msgFpa)

  test("Clicking a block after True+OK+(1,2) should change from `On(_)` to `On(_)` w/o advancing step"):
    val msgFp0 = s"Clicking $p0 should switch from ${On(pa)} to ${On(p0)} w/o advancing step, but does not"
    assertEquals(fp0, gameFp0, msgFp0)

  test("Clicking an empty spot after True+OK+(1,2) should change from `On(_)` to `Wait` w/o advancing step"):
    val msgFp1 = s"Clicking $p1 should switch from ${On(pa)} to Wait w/o advancing step, but does not"
    assertEquals(fp1, gameFp1, msgFp1)

  test("Clicking OK after True+OK+(1,2) should advance step and setup Or formula choice"):
    val msgF113 = s"Clicking OK should substitute `a`, setup Left/Right and advance step, but does not"
    assertEquals(f113, gameF113, msgF113)

  test("Clicking Left after True+OK+(1,2)+OK should advance step and lose the game"):
    val msgG005 = s"Clicking Left should reset Left/Right to None and advance step, but does not"
    assertEquals(g005, gameG005, msgG005)

  test("Clicking Right after True+OK+(1,2)+OK should advance step and lose the game"):
    val msgG105 = s"Clicking Right should reset Left/Right to None and advance step, but does not"
    assertEquals(g105, gameG105, msgG105)

  test("Clicking OK after False+(1,2)+OK should make computer choose one disjunct of `Or`"):
    val msgH113 = "Clicking OK on a false `Or` should make computer choose, but does not"
    assertEquals(h113, gameH113, msgH113)

  test("Clicking OK after False+(1,2)+OK+OK should end the game with a win"):
    val msgI113 = "Clicking OK on a false sentence should make user win, but does not"
    assertEquals(i113, gameI113, msgI113)

  test("Clicking any button except Back after game over with a loss should do nothing"):
    assertEquals(j011, g005, msg("True"))
    assertEquals(j111, g005, msg("False"))
    assertEquals(j005, g005, msg("Left"))
    assertEquals(j105, g005, msg("Right"))
    assertEquals(j113, g005, msg("OK"))
    assertEquals(j014, g005, msg("Display"))
    assertEquals(jpa, g005, msg(s"$pa"))
    assertEquals(jp0, g005, msg(s"$p0"))
    assertEquals(jp1, g005, msg(s"$p1"))

  test("Clicking any button except Back after game over with a win should do nothing"):
    assertEquals(k011, i113, msg("True"))
    assertEquals(k111, i113, msg("False"))
    assertEquals(k005, i113, msg("Left"))
    assertEquals(k105, i113, msg("Right"))
    assertEquals(k113, i113, msg("OK"))
    assertEquals(k014, i113, msg("Display"))
    assertEquals(kpa, i113, msg(s"$pa"))
    assertEquals(kp0, i113, msg(s"$p0"))
    assertEquals(kp1, i113, msg(s"$p1"))
