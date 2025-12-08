package tarski
package testing

class HandlersTest extends munit.FunSuite:
  given c: Constants = Constants(DefaultSize)
  import Shape.*, Status.*, Result.*, Sizes.*, Tone.*

  val b0 = Block(Small, Cir, Blue)
  val b1 = Block(Mid, Tri, Green)
  val b2 = Block(Large, Cir, Orange)
  val p0 = (1, 2)
  val p1 = (3, 4)
  val p2 = (5, 6)
  val p3 = (4, 1)
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

  // For UI controls tests
  // No position selected
  val w000  = Handler.uiButtons((0, 0), w0)    // Eval
  val w001  = Handler.uiButtons((0, 1), w000)  // Eval
  val w100  = Handler.uiButtons((1, 0), w001)  // Move
  val w101  = Handler.uiButtons((1, 1), w100)  // Move
  val w013  = Handler.uiButtons((0, 13), w101) // Block
  val w014  = Handler.uiButtons((0, 14), w013) // Block
  val w015  = Handler.uiButtons((0, 15), w014) // Block
  val w113  = Handler.uiButtons((1, 13), w015) // Block
  val w114  = Handler.uiButtons((1, 14), w113) // Block
  val w115  = Handler.uiButtons((1, 15), w114) // Block
  val w002  = Handler.uiButtons((0, 2), w115)  // Add
  val w003  = Handler.uiButtons((0, 3), w115)  // Add
  val w004  = Handler.uiButtons((0, 4), w003)  // a
  val w005  = Handler.uiButtons((0, 5), w004)  // b
  val w006  = Handler.uiButtons((0, 6), w005)  // c
  val w007  = Handler.uiButtons((0, 7), w006)  // d
  val w008  = Handler.uiButtons((0, 8), w007)  // e
  val w008_ = Handler.uiButtons((0, 0), w008)  // Eval
  val w009  = Handler.uiButtons((0, 9), w008_) // f
  val w010  = Handler.uiButtons((0, 10), w009) // Blue
  val w011  = Handler.uiButtons((0, 11), w010) // Green
  val w012  = Handler.uiButtons((0, 12), w011) // Orange
  val w102  = Handler.uiButtons((1, 2), w012)  // Del
  val w103  = Handler.uiButtons((1, 3), w102)  // Del
  val w104  = Handler.uiButtons((1, 4), w103)  // Small
  val w105  = Handler.uiButtons((1, 5), w104)  // Small
  val w106  = Handler.uiButtons((1, 6), w105)  // Mid
  val w107  = Handler.uiButtons((1, 7), w106)  // Mid
  val w108  = Handler.uiButtons((1, 8), w107)  // Large
  val w109  = Handler.uiButtons((1, 9), w108)  // Large
  val w110  = Handler.uiButtons((1, 10), w109) // Tri
  val w111  = Handler.uiButtons((1, 11), w110) // Squ
  val w112  = Handler.uiButtons((1, 12), w111) // Cir

  // Selected position is empty
  val x     = w112.selectPos(p2)
  val x000  = Handler.uiButtons((0, 0), x)     // Eval
  val x002  = Handler.uiButtons((0, 2), x000)  // Add Orange Large Cir
  val x002_ = x002.unsetBlock.selectPos(p3)
  val x003  = Handler.uiButtons((0, 3), x002_) // Add no block

  // Selected position has a block on it
  val y     = x002.selectPos(p2)
  val y002  = Handler.uiButtons((0, 2), y)      // Add
  val y002_ = Handler.uiButtons((0, 0), y002)   // Eval
  val y004  = Handler.uiButtons((0, 4), y002_)  // a
  val y004_ = Handler.uiButtons((0, 0), y004)   // Eval
  val y010  = Handler.uiButtons((0, 10), y004_) // Blue
  val y010_ = Handler.uiButtons((0, 0), y010)   // Eval
  val y104  = Handler.uiButtons((1, 4), y010_)  // Small
  val y104_ = Handler.uiButtons((0, 0), y104)   // Eval
  val y110  = Handler.uiButtons((1, 10), y104_) // Tri
  val y110_ = Handler.uiButtons((0, 0), y110)   // Eval
  val y102  = Handler.uiButtons((1, 2), y110_)  // Del

  // For board controls tests
  val z0  = Handler.boardPos(p0, w0) // no pos selected, clicked p0 has a block
  val z2  = Handler.boardPos(p2, w0) // no pos selected, clicked p2 has no block
  val z00 = Handler.boardPos(p0, z0) // selected p0 == clicked p0

  // Selected != clicked
  // Move enabled
  val z0e1  = z0.toggleMove                   // selected p0 has block b0
  val z0e11 = Handler.boardPos(p1, z0e1)      // block b1 at clicked p1
  val z0e1_ = Handler.uiButtons((0, 0), z0e1) // Eval
  val z0e12 = Handler.boardPos(p2, z0e1_)     // no block at clicked p2
  val z0e2  = z0e1.selectPos(p2)              // selected p2 has no block
  val z0e21 = Handler.boardPos(p1, z0e2)      // block b1 at clicked p1
  val z0e23 = Handler.boardPos(p3, z0e2)      // no block at clicked p3
  // Move disabled
  val z0d1  = z0                         // selected p0 has block b0
  val z0d11 = Handler.boardPos(p1, z0d1) // block b1 at clicked p1
  val z0d12 = Handler.boardPos(p2, z0d1) // no block at clicked p2
  val z0d2  = z0d1.selectPos(p2)         // selected p2 has no block
  val z0d21 = Handler.boardPos(p1, z0d2) // block b1 at clicked p1
  val z0d23 = Handler.boardPos(p3, z0d2) // no block at clicked p3

  // UI controls tests
  test("Eval button in a world with 2 blocks and 3 formulas"):
    assertEquals(w000.formulas(f0), Valid, s"formula $f0 should be true, but is false")
    assertEquals(w001.formulas(f1), Invalid, s"formula $f1 should be false, but is true")
    assertEquals(w001.formulas(f2), Ready, s"formula $f2 should not be evaluated, but is")

  test("Move button should toggle move"):
    val msg1 = "Move should be enabled, but is disabled"
    val msg2 = "Move should be disabled, but is enabled"
    assertEquals(w100, w001.toggleMove, msg1)
    assert(w100.controls.move, msg1)
    assertEquals(w101, w100.toggleMove, msg2)
    assert(!w101.controls.move, msg2)

  test("Block display should do nothing if clicked"):
    val msg = "Clicking the block display should not do anything, but does"
    assertEquals(w013, w101, msg)
    assertEquals(w014, w013, msg)
    assertEquals(w015, w014, msg)
    assertEquals(w113, w015, msg)
    assertEquals(w114, w113, msg)
    assertEquals(w115, w114, msg)

  test("Adding a block with no position selected"):
    val msg = "Add button with no selected pos should not do anything, but does"
    assertEquals(w002, w115, msg)
    assertEquals(w003, w115, msg)

  test("Name buttons with no position selected"):
    def msg(n: String) = s"Button for available name $n should not do anything, but does"
    val msgF           = "Button f should avail the occupied name, but does not"
    val msgFormulas    = "Availing an occupied name should reset all formulas, but does not"
    assertEquals(w004, w003, msg("a"))
    assertEquals(w005, w004, msg("b"))
    assertEquals(w006, w005, msg("c"))
    assertEquals(w007, w006, msg("d"))
    assertEquals(w008, w007, msg("e"))
    assertEquals(w009.names("f"), Available, msgF)
    assert(w009.formulas.values.forall(_ == Ready), msgFormulas)

  test("Color buttons with no position selected"):
    def msg(obt: Option[Tone], exp: Option[Tone]) =
      s"Color should be $exp but is $obt"
    val (obt1, exp1) = (w010.controls.toneOpt, Some(Blue))
    val (obt2, exp2) = (w011.controls.toneOpt, Some(Green))
    val (obt3, exp3) = (w012.controls.toneOpt, Some(Orange))
    assertEquals(obt1, exp1, msg(obt1, exp1))
    assertEquals(obt2, exp2, msg(obt2, exp2))
    assertEquals(obt3, exp3, msg(obt3, exp3))

  test("Delete button with no position selected"):
    val msg = "Delete button should not do anything, but does"
    assertEquals(w102, w012, msg)
    assertEquals(w103, w102, msg)

  test("Size buttons with no position selected"):
    def msg1(s: Sizes) = s"Size should be $s, but is not"
    def msg2(s: Sizes) = s"Clicking $s twice should not do anything, but does"
    assertEquals(w104.controls.sizeOpt.get, Small, msg1(Small))
    assertEquals(w105, w104, msg2(Small))
    assertEquals(w106.controls.sizeOpt.get, Mid, msg1(Mid))
    assertEquals(w107, w106, msg2(Mid))
    assertEquals(w108.controls.sizeOpt.get, Large, msg1(Large))
    assertEquals(w109, w108, msg2(Large))

  test("Shape buttons with no position selected"):
    def msg(obt: Option[Shape], exp: Option[Shape]) =
      s"Shape should be $exp but is $obt"
    val (obt1, exp1) = (w110.controls.shapeOpt, Some(Tri))
    val (obt2, exp2) = (w111.controls.shapeOpt, Some(Squ))
    val (obt3, exp3) = (w112.controls.shapeOpt, Some(Cir))
    assertEquals(obt1, exp1, msg(obt1, exp1))
    assertEquals(obt2, exp2, msg(obt2, exp2))
    assertEquals(obt3, exp3, msg(obt3, exp3))

  test("Add button with an empty position selected"):
    val msg1 = s"Pos $p2 should have block $b2 added, but does not"
    val msg2 = "Adding a block should reset all formulas, but does not"
    assertEquals(x002.posGrid(p2).block, b2, msg1)
    assert(x002.formulas.values.forall(_ == Ready), msg2)
    assertEquals(x003, x002_, "Adding a None block shouldn't do anything, but does")

  test("Add button with a block at selected position"):
    assertEquals(y002, y, "Adding with a block at selected pos should not work, but does")

  test("Name button with a block at selected position"):
    val b             = b2.setLabel("a")
    val (block, name) = y004.posGrid(p2)
    assertEquals(block, b, s"Block at $p2 should be $b, but is $block")
    assertEquals(name, "a", s"Name of block should be a, but is $name")
    assertEquals(y004.names("a"), Occupied, "Naming a block should occupy the name, but does not")
    assert(y004.formulas.values.forall(_ == Ready), "Adding a name to a block should reset all formulas, but does not")

  test("Color buttons with a block at selected position"):
    val b   = y010.posGrid(p2).block
    val msg = "Changing the color of a block should reset all formulas, but does not"
    assertEquals(b.tone, Blue, s"Block $b at position $p2 should be Blue, but is not")
    assertEquals(y010.controls.toneOpt, Some(Blue), "Color should be Blue, but is not")
    assert(y010.formulas.values.forall(_ == Ready), msg)

  test("Size buttons with a block at selected position"):
    val b   = y104.posGrid(p2).block
    val msg = "Changing the size of a block should reset all formulas, but does not"
    assertEquals(b.size, Small, s"Block $b at position $p2 should be Small, but is not")
    assertEquals(y104.controls.sizeOpt, Some(Small), "Size should be Small, but is not")
    assert(y104.formulas.values.forall(_ == Ready), msg)

  test("Shape buttons with a block at selected position"):
    val b   = y110.posGrid(p2).block
    val msg = "Changing the shape of a block should reset all formulas, but does not"
    assertEquals(b.shape, Tri, s"Block $b at position $p2 should be Tri, but is not")
    assertEquals(y110.controls.shapeOpt, Some(Tri), "Shape should be Tri, but is not")
    assert(y110.formulas.values.forall(_ == Ready), msg)

  test("Delete button with a block at selected position"):
    assertEquals(y102.posGrid.get(p2), None, s"Block at pos $p2 should be deleted, but is not")
    assertEquals(y102.names("a"), Available, "Name a of deleted block should be Available, but is not")
    assert(y102.formulas.values.forall(_ == Ready), "Deleting a block should reset all formulas, but does not")

  test("Block display and selected pos should not change after deletion"):
    assertEquals(y102.controls.shapeOpt, Some(Tri))
    assertEquals(y102.controls.sizeOpt, Some(Small))
    assertEquals(y102.controls.toneOpt, Some(Blue))
    assertEquals(y102.controls.posOpt, Some(p2))

  // Board controls tests
  test("Board position, with no position selected"):
    // Clicked pos has a block
    val ctl = Controls(Some(Small), Some(Cir), Some(Blue), Some(p0), false)
    val msg = "Clicking on a block should update controls and block display, but does not"
    assertEquals(z0.controls, ctl, msg)

    // Clicked pos has no block
    val msg2 = s"Clicking pos $p2 should select it and do nothing else, but does not"
    assertEquals(z2, w0.selectPos(p2), msg2)

  test("Board position, when selected and clicked positions are the same"):
    val ctl = Controls(None, None, None, None, false)
    val msg = s"Clicking pos $p0 should reset UI controls and block display, but does not"
    assertEquals(z00.controls, ctl, msg)

  test("Board position, selected differs from clicked pos and move is enabled"):
    val msgz0e11 = s"Clicking $p1 should select it, but does not"
    val ctlz0e11 = Controls(Some(Mid), Some(Tri), Some(Green), Some(p1), true)
    assertEquals(z0e11.controls, ctlz0e11, msgz0e11)

    val msgz0e12 = s"Clicking $p2 should move $b0 to it and disable move, but does not"
    val ctlz0e12 = Controls(Some(Small), Some(Cir), Some(Blue), Some(p2), false)
    assertEquals(z0e12.controls, ctlz0e12, msgz0e12)
    assert(z0e12.formulas.values.forall(_ == Ready), "Moving a block should reset all formulas, but does not")

    val msgz0e21 = s"Clicking $p1 should select it, but does not"
    val ctlz0e21 = Controls(Some(Mid), Some(Tri), Some(Green), Some(p1), true)
    assertEquals(z0e21.controls, ctlz0e21, msgz0e21)

    val msgz0e23 = s"Clicking $p3 should select it, but does not"
    val ctlz0e23 = Controls(Some(Small), Some(Cir), Some(Blue), Some(p3), true)
    assertEquals(z0e23.controls, ctlz0e23, msgz0e23)

  // Move disabled
  test("Board position, selected differs from clicked pos and move is disabled"):
    val msgz0d11 = s"Clicking $p1 should select it, but does not"
    val ctlz0d11 = Controls(Some(Mid), Some(Tri), Some(Green), Some(p1), false)
    assertEquals(z0d11.controls, ctlz0d11, msgz0d11)

    val msgz0d12 = s"Clicking $p2 should select it, but does not"
    val ctlz0d12 = Controls(Some(Small), Some(Cir), Some(Blue), Some(p2), false)
    assertEquals(z0d12.controls, ctlz0d12, msgz0d12)

    val msgz0d21 = s"Clicking $p1 should select it, but does not"
    val ctlz0d21 = Controls(Some(Mid), Some(Tri), Some(Green), Some(p1), false)
    assertEquals(z0d21.controls, ctlz0d21, msgz0d21)

    val msgz0d23 = s"Clicking $p3 should select it, but does not"
    val ctlz0d23 = Controls(Some(Small), Some(Cir), Some(Blue), Some(p3), false)
    assertEquals(z0d23.controls, ctlz0d23, msgz0d23)
