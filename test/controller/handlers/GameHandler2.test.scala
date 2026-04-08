package tarski
package testing

/** More tests for [[controller.GameHandler]]. */
class GameHandlerTest2 extends munit.FunSuite:
  import GameHandler2TestData.*, model.Select, Select.*

  test("Iff: committing to True should work correctly"):
    assertEquals(a011, gameA011, "commit True should work correctly, but does not")

  test("Iff: clicking True + OK should work correctly"):
    assertEquals(b113, gameB113, "True + OK should work correctly, but does not")

  test("Iff: clicking True + OK + OK should work correctly"):
    assertEquals(c113, gameC113, "True + OK + OK should work correctly, but does not")

  test("Iff: clicking True + OK + OK + OK should work correctly"):
    assertEquals(e113, gameE113, "True+OK+OK+OK should work correctly, but does not")

  test("Iff: clicking True + OK + OK + OK + Right should work correctly"):
    assertEquals(f105, gameF105, "True+OK+OK+OK+Right should work correctly, but does not")

  test("Iff: committing False should work correctly"):
    assertEquals(a111, gameA111, "commit False should work correctly, but does not")

  test("Iff: clicking False + (3, 3) should work correctly"):
    assertEquals(bpa, gameBpa, "False + (3, 3) should work correctly, but does not")

  test("Iff: clicking False + (3, 3) + OK should work correctly"):
    assertEquals(d113, gameD113, "False + (3, 3) + OK should work correctly, but does not")

  test("Iff: clicking False + (3, 3) + OK + Right should work correctly"):
    assertEquals(e105, gameE105, "False+(3,3)+OK+Right should work correctly, but does not")

  test("Iff: clicking False + (3, 3) + OK + Right + OK should work correctly"):
    assertEquals(f113, gameF113, "False+(3,3)+OK+Right+OK should work correctly, but does not")

  test("Iff: clicking False + (3, 3) + OK + Right + OK + OK should work correctly"):
    assertEquals(h113, gameH113, "False+(3,3)+OK+Right+OK+OK should work correctly, but does not")
