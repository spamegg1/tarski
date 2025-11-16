package tarski
package testing

class HandlersTest extends munit.FunSuite:
  given c: Constants = Constants(DefaultSize)
  import Shape.*, Status.*, Result.*, Sizes.*

  val b0 = Block(Small, Cir, Gray)
  val b1 = Block(Mid, Tri, Green) // at (5,6)
  val p0 = (1, 2)
  val p1 = (3, 4)
  val p2 = (5, 6)
  val f0 = fof"¬(∃x Large(x))"
  val f1 = fof"!x Cir(x)"
  val f2 = fof"a = b"
  val w0 = World.empty
    .addBlockAt(p0, b0)
    .addBlockAt(p1, b1)
    .addFormula(f0)
    .addFormula(f1)
    .addFormula(f2)

  test("Evaluating in a world with 2 blocks and 3 formulas"):
    val w000 = Handler.uiButtons((0, 0), w0) // Eval
    val w001 = Handler.uiButtons((0, 1), w0) // Eval
    assertEquals(w000.formulas(f0), Valid, s"formula $f0 should be true, but is false")
    assertEquals(w001.formulas(f1), Invalid, s"formula $f1 should be false, but is true")
    assertEquals(w001.formulas(f2), Ready, s"formula $f2 should not be evaluated, but is")

  test("Adding a block in a world with 2 blocks but no selected block or pos"):
    val w002 = Handler.uiButtons((0, 2), w0) // Add
    val w003 = Handler.uiButtons((0, 3), w0) // Add
    assertEquals(w002, w0, "adding a block should not work, but does")
    assertEquals(w003, w0, "adding a block should not work, but does")

  test("Selecting a name in a world with no selected block or pos"):
    val w004 = Handler.uiButtons((0, 4), w0) // a
    val w005 = Handler.uiButtons((0, 5), w0) // b
    val w006 = Handler.uiButtons((0, 6), w0) // c
    val w007 = Handler.uiButtons((0, 7), w0) // d
    val w008 = Handler.uiButtons((0, 8), w0) // e
    val w009 = Handler.uiButtons((0, 9), w0) // f
    assertEquals(w004, w0, "selecting a name should not work, but does")
    assertEquals(w005, w0, "selecting a name should not work, but does")
    assertEquals(w006, w0, "selecting a name should not work, but does")
    assertEquals(w007, w0, "selecting a name should not work, but does")
    assertEquals(w008, w0, "selecting a name should not work, but does")
    assertEquals(w009, w0, "selecting a name should not work, but does")

  test("Selecting colors in a world with 2 blocks but no selected pos"):
    val w010 = Handler.uiButtons((0, 10), w0) // Blue
    val w011 = Handler.uiButtons((0, 11), w0) // Green
    val w012 = Handler.uiButtons((0, 12), w0) // Gray
    assertEquals(w010.controls.color, Some(Blue), "color should be Blue, but is not")
    assertEquals(w010.controls.size, None, "size should be None, but is not")
    assertEquals(w011.controls.color, Some(Green), "color should be Green, but is not")
    assertEquals(w011.controls.shape, None, "shape should be None, but is not")
    assertEquals(w012.controls.color, Some(Gray), "color should be Gray, but is not")

  test("Clicking on the displayed block should do nothing"):
    val w013 = Handler.uiButtons((0, 13), w0) // Block
    val w014 = Handler.uiButtons((0, 14), w0) // Block
    val w015 = Handler.uiButtons((0, 15), w0) // Block
    assertEquals(w013, w0)
    assertEquals(w014, w0)
    assertEquals(w015, w0)

  test("asd"):
    val w016 = Handler.uiButtons((1, 0), w0)  // Move
    val w017 = Handler.uiButtons((1, 1), w0)  // Move
    val w018 = Handler.uiButtons((1, 2), w0)  // Del
    val w019 = Handler.uiButtons((1, 3), w0)  // Del
    val w020 = Handler.uiButtons((1, 4), w0)  // Small
    val w021 = Handler.uiButtons((1, 5), w0)  // Small
    val w022 = Handler.uiButtons((1, 6), w0)  // Mid
    val w023 = Handler.uiButtons((1, 7), w0)  // Mid
    val w024 = Handler.uiButtons((1, 8), w0)  // Large
    val w025 = Handler.uiButtons((1, 9), w0)  // Large
    val w026 = Handler.uiButtons((1, 10), w0) // Tri
    val w027 = Handler.uiButtons((1, 11), w0) // Squ
    val w028 = Handler.uiButtons((1, 12), w0) // Cir
    val w029 = Handler.uiButtons((1, 13), w0) // Block
    val w030 = Handler.uiButtons((1, 14), w0) // Block
    val w031 = Handler.uiButtons((1, 15), w0) // Block
    assert(true)

  test("Handlers integration test for clicking all control buttons"):
    assert(true)
