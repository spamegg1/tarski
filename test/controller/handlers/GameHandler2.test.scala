package tarski
package testing

class GameHandlerTest2 extends munit.FunSuite:
  import GameHandler2TestData.*, Select.*

  test("Iff True 1"):
    assertEquals(a011, gameA011, "error")

  test("Iff True 2"):
    assertEquals(b113, gameB113, "error")

  test("Iff True 3"):
    assertEquals(c113, gameC113, "error")

  test("Iff True 4"):
    assertEquals(e113, gameE113, "error")

  test("Iff True 5"):
    assertEquals(f105, gameF105, "error")

  test("Iff False 1"):
    assertEquals(a111, gameA111, "error")

  test("Iff False 2"):
    assertEquals(bpa, gameBpa, "error")

  test("Iff False 3"):
    assertEquals(d113, gameD113, "error")

  test("Iff False 4"):
    assertEquals(e105, gameE105, "error")

  test("Iff False 5"):
    assertEquals(f113, gameF113, "error")

  test("Iff False 6"):
    assertEquals(h113, gameH113, "error")
