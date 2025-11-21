package tarski
package testing

class HandlersTest extends munit.FunSuite:
  given c: Constants = Constants(DefaultSize)
  import Shape.*, Status.*, Result.*, Sizes.*, Tone.*

  val b0 = Block(Small, Cir, Gray)
  val b1 = Block(Mid, Tri, Green)
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
    .addNameToBlockAt(p0, "f")

  val w000 = Handler.uiButtons((0, 0), w0)    // Eval
  val w001 = Handler.uiButtons((0, 1), w000)  // Eval
  val w100 = Handler.uiButtons((1, 0), w001)  // Move
  val w101 = Handler.uiButtons((1, 1), w100)  // Move
  val w013 = Handler.uiButtons((0, 13), w101) // Block
  val w014 = Handler.uiButtons((0, 14), w013) // Block
  val w015 = Handler.uiButtons((0, 15), w014) // Block
  val w113 = Handler.uiButtons((1, 13), w015) // Block
  val w114 = Handler.uiButtons((1, 14), w113) // Block
  val w115 = Handler.uiButtons((1, 15), w114) // Block
  val w002 = Handler.uiButtons((0, 2), w115)  // Add
  val w003 = Handler.uiButtons((0, 3), w115)  // Add
  val w004 = Handler.uiButtons((0, 4), w003)  // a
  val w005 = Handler.uiButtons((0, 5), w004)  // b
  val w006 = Handler.uiButtons((0, 6), w005)  // c
  val w007 = Handler.uiButtons((0, 7), w006)  // d
  val w008 = Handler.uiButtons((0, 8), w007)  // e
  val w009 = Handler.uiButtons((0, 9), w008)  // f
  val w010 = Handler.uiButtons((0, 10), w009) // Blue
  val w011 = Handler.uiButtons((0, 11), w010) // Green
  val w012 = Handler.uiButtons((0, 12), w011) // Gray
  val w102 = Handler.uiButtons((1, 2), w012)  // Del
  val w103 = Handler.uiButtons((1, 3), w102)  // Del

  test("Eval button in a world with 2 blocks and 3 formulas"):
    assertEquals(w000.formulas(f0), Valid, s"formula $f0 should be true, but is false")
    assertEquals(w001.formulas(f1), Invalid, s"formula $f1 should be false, but is true")
    assertEquals(w001.formulas(f2), Ready, s"formula $f2 should not be evaluated, but is")

  test("Move button should toggle move"):
    test("enabling move"):
      val msg = "Move should be enabled, but is disabled"
      assertEquals(w100, w001.toggleMove, msg)
      assert(w100.controls.move, msg)
    test("disabling move"):
      val msg = "Move should be disabled, but is enabled"
      assertEquals(w101, w100.toggleMove, msg)
      assert(!w101.controls.move, msg)

  test("Block display should do nothing if clicked"):
    val msg = "Clicking the block display should do nothing, but does something"
    assertEquals(w013, w101, msg)
    assertEquals(w014, w013, msg)
    assertEquals(w015, w014, msg)
    assertEquals(w113, w015, msg)
    assertEquals(w114, w113, msg)
    assertEquals(w115, w114, msg)

  test("Adding a block with no position selected"):
    val msg = "Add button with no selected pos should do nothing, but does something"
    assertEquals(w002, w115, msg)
    assertEquals(w003, w115, msg)

  test("Name buttons with no position selected"):
    def msg(n: String) = s"Button for available name $n should do nothing, but does something"
    val msgF           = "Button f should avail the occupied name, but does not"
    assertEquals(w004, w003, msg("a"))
    assertEquals(w005, w004, msg("b"))
    assertEquals(w006, w005, msg("c"))
    assertEquals(w007, w006, msg("d"))
    assertEquals(w008, w007, msg("e"))
    assertEquals(w009.names("f"), Available, msgF)

  test("Color buttons with no position selected"):
    def msg(obt: Option[Tone], exp: Option[Tone]) =
      s"Color should be $exp but is $obt"
    val (obt1, exp1) = (w010.controls.toneOpt, Some(Blue))
    val (obt2, exp2) = (w011.controls.toneOpt, Some(Green))
    val (obt3, exp3) = (w012.controls.toneOpt, Some(Gray))
    assertEquals(obt1, exp1, msg(obt1, exp1))
    assertEquals(obt2, exp2, msg(obt2, exp2))
    assertEquals(obt3, exp3, msg(obt3, exp3))

  test("Delete button with no position selected"):
    val msg = "Delete button should do nothing, but does something"
    assertEquals(w102, w012, msg)
    assertEquals(w103, w102, msg)

  // val w020 = Handler.uiButtons((1, 4), w019)  // Small
  // val w021 = Handler.uiButtons((1, 5), w019)  // Small
  // val w022 = Handler.uiButtons((1, 6), w021)  // Mid
  // val w023 = Handler.uiButtons((1, 7), w021)  // Mid
  // val w024 = Handler.uiButtons((1, 8), w023)  // Large
  // val w025 = Handler.uiButtons((1, 9), w023)  // Large
  // val w026 = Handler.uiButtons((1, 10), w025) // Tri
  // val w027 = Handler.uiButtons((1, 11), w025) // Squ
  // val w028 = Handler.uiButtons((1, 12), w025) // Cir

  // test("Adding a block in a world with 2 blocks but no selected block or pos"):
  //   assertEquals(w002, w001, "adding a block should not work, but does")
  //   assertEquals(w003, w001, "adding a block should not work, but does")

  // test("Selecting a name in a world with no selected block or pos"):
  //   assertEquals(w004, w003, "selecting a name should not work, but does")
  //   assertEquals(w005, w003, "selecting a name should not work, but does")
  //   assertEquals(w006, w003, "selecting a name should not work, but does")
  //   assertEquals(w007, w003, "selecting a name should not work, but does")
  //   assertEquals(w008, w003, "selecting a name should not work, but does")
  //   assertEquals(w009, w003, "selecting a name should not work, but does")

  // test("Selecting colors in a world with 2 blocks but no selected pos"):
  //   assertEquals(w010.controls.toneOpt, Some(Blue), "color should be Blue, but is not")
  //   assertEquals(w010.controls.sizeOpt, None, "size should be None, but is not")
  //   assertEquals(w011.controls.toneOpt, Some(Green), "color should be Green, but is not")
  //   assertEquals(w011.controls.shapeOpt, None, "shape should be None, but is not")
  //   assertEquals(w012.controls.toneOpt, Some(Gray), "color should be Gray, but is not")

  // test("Clicking on the displayed block should do nothing"):
  //   assertEquals(w013, w012)
  //   assertEquals(w014, w012)
  //   assertEquals(w015, w012)
