package tarski
package testing

class GameHandlerTest2 extends munit.FunSuite:
  import GameHandler2TestData.*, Select.*

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

  test("Iff: clicking False + pa should work correctly"):
    assertEquals(bpa, gameBpa, "False + pa should work correctly, but does not")

  test("Iff: clicking False + pa + OK should work correctly"):
    assertEquals(d113, gameD113, "False + pa + OK should work correctly, but does not")

  test("Iff: clicking False + pa + OK + Right should work correctly"):
    assertEquals(e105, gameE105, "False+pa+OK+Right should work correctly, but does not")

  test("Iff: clicking False + pa + OK + Right + OK should work correctly"):
    assertEquals(f113, gameF113, "False+pa+OK+Right+OK should work correctly, but does not")

  test("Iff: clicking False + pa + OK + Right + OK + OK should work correctly"):
    assertEquals(h113, gameH113, "False+pa+OK+Right+OK+OK should work correctly, but does not")
