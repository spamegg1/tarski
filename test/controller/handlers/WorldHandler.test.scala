package tarski
package testing

import model.*

/** Tests for [[controller.WorldHandler]]. */
class WorldHandlerTest extends munit.FunSuite:
  import Shape.*, Status.*, Result.*, Sizes.*, Tone.*
  import constants.Constants, Constants.DefaultSize
  import WorldHandlerTestData.*

  given c: Constants = Constants(DefaultSize)

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

  test("Left button should rotate board counter-clockwise"):
    val msg1 = s"Left button should rotate $b0 correctly from $p0 to (5, 1), but does not"
    val msg2 = s"Left button should rotate $b1 correctly from $p1 to (3, 3), but does not"
    val msg3 = s"Left button should change selected position from $p0 to (5, 1), but does not"
    assertEquals(w013.board((5, 1)).block, Block(Sml, Cir, Blu, "f"), msg1)
    assertEquals(w013.board((3, 3)).block, b1, msg2)
    assertEquals(w013.controls.posOpt, Some((5, 1)), msg3)

  test("Right button should rotate board clockwise"):
    val msg1 = s"Right button should rotate $b0 correctly from $p0 to (2, 6), but does not"
    val msg2 = s"Right button should rotate $b1 correctly from $p1 to (4, 4), but does not"
    val msg3 = s"Right button should change selected position from $p1 to (4, 4), but does not"
    assertEquals(w113.board((2, 6)).block, Block(Sml, Cir, Blu, "f"), msg1)
    assertEquals(w113.board((4, 4)).block, b1, msg2)
    assertEquals(w113.controls.posOpt, Some((4, 4)), msg3)

  test("Block display should do nothing if clicked"):
    val msg = "Clicking the block display should not do anything, but does"
    assertEquals(w014, w100, msg)
    assertEquals(w015, w014, msg)
    assertEquals(w114, w015, msg)
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
    val (obt1, exp1) = (w010.controls.toneOpt, Some(Blu))
    val (obt2, exp2) = (w011.controls.toneOpt, Some(Lim))
    val (obt3, exp3) = (w012.controls.toneOpt, Some(Red))
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
    assertEquals(w104.controls.sizeOpt.get, Sml, msg1(Sml))
    assertEquals(w105, w104, msg2(Sml))
    assertEquals(w106.controls.sizeOpt.get, Mid, msg1(Mid))
    assertEquals(w107, w106, msg2(Mid))
    assertEquals(w108.controls.sizeOpt.get, Big, msg1(Big))
    assertEquals(w109, w108, msg2(Big))

  test("Shape buttons with no position selected"):
    def msg(obt: Option[Shape], exp: Option[Shape]) =
      s"Shape should be $exp but is $obt"
    val (obt1, exp1) = (w110.controls.shapeOpt, Some(Tri))
    val (obt2, exp2) = (w111.controls.shapeOpt, Some(Sqr))
    val (obt3, exp3) = (w112.controls.shapeOpt, Some(Cir))
    assertEquals(obt1, exp1, msg(obt1, exp1))
    assertEquals(obt2, exp2, msg(obt2, exp2))
    assertEquals(obt3, exp3, msg(obt3, exp3))

  test("Add button with an empty position selected"):
    val msg1 = s"Pos $p2 should have block $b2 added, but does not"
    val msg2 = "Adding a block should reset all formulas, but does not"
    assertEquals(x002.board(p2).block, b2, msg1)
    assert(x002.formulas.values.forall(_ == Ready), msg2)
    assertEquals(x003, x002_, "Adding a None block shouldn't do anything, but does")

  test("Add button with a block at selected position"):
    assertEquals(y002, y, "Adding with a block at selected pos should not work, but does")

  test("Name button with a block at selected position"):
    val b             = b2.setLabel("a")
    val (block, name) = y004.board(p2)
    assertEquals(block, b, s"Block at $p2 should be $b, but is $block")
    assertEquals(name, "a", s"Name of block should be a, but is $name")
    assertEquals(y004.names("a"), Occupied, "Naming a block should occupy the name, but does not")
    assert(y004.formulas.values.forall(_ == Ready), "Adding a name to a block should reset all formulas, but does not")

  test("Color buttons with a block at selected position"):
    val b   = y010.board(p2).block
    val msg = "Changing the color of a block should reset all formulas, but does not"
    assertEquals(b.tone, Blu, s"Block $b at position $p2 should be Blu, but is not")
    assertEquals(y010.controls.toneOpt, Some(Blu), "Color should be Blu, but is not")
    assert(y010.formulas.values.forall(_ == Ready), msg)

  test("Size buttons with a block at selected position"):
    val b   = y104.board(p2).block
    val msg = "Changing the size of a block should reset all formulas, but does not"
    assertEquals(b.size, Sml, s"Block $b at position $p2 should be Sml, but is not")
    assertEquals(y104.controls.sizeOpt, Some(Sml), "Size should be Sml, but is not")
    assert(y104.formulas.values.forall(_ == Ready), msg)

  test("Shape buttons with a block at selected position"):
    val b   = y110.board(p2).block
    val msg = "Changing the shape of a block should reset all formulas, but does not"
    assertEquals(b.shape, Tri, s"Block $b at position $p2 should be Tri, but is not")
    assertEquals(y110.controls.shapeOpt, Some(Tri), "Shape should be Tri, but is not")
    assert(y110.formulas.values.forall(_ == Ready), msg)

  test("Delete button with a block at selected position"):
    assertEquals(y102.board.get(p2), None, s"Block at pos $p2 should be deleted, but is not")
    assertEquals(y102.names("a"), Available, "Name a of deleted block should be Available, but is not")
    assert(y102.formulas.values.forall(_ == Ready), "Deleting a block should reset all formulas, but does not")

  test("Block display and selected pos should not change after deletion"):
    assertEquals(y102.controls.shapeOpt, Some(Tri))
    assertEquals(y102.controls.sizeOpt, Some(Sml))
    assertEquals(y102.controls.toneOpt, Some(Blu))
    assertEquals(y102.controls.posOpt, Some(p2))

  // Board controls tests
  test("Board position, with no position selected"):
    // Clicked pos has a block
    val ctl = Controls(Some(Sml), Some(Cir), Some(Blu), Some(p0), false)
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
    val ctlz0e11 = Controls(Some(Mid), Some(Tri), Some(Lim), Some(p1), true)
    assertEquals(z0e11.controls, ctlz0e11, msgz0e11)

    val msgz0e12 = s"Clicking $p2 should move $b0 to it and disable move, but does not"
    val ctlz0e12 = Controls(Some(Sml), Some(Cir), Some(Blu), Some(p2), false)
    assertEquals(z0e12.controls, ctlz0e12, msgz0e12)
    assert(z0e12.formulas.values.forall(_ == Ready), "Moving a block should reset all formulas, but does not")

    val msgz0e21 = s"Clicking $p1 should select it, but does not"
    val ctlz0e21 = Controls(Some(Mid), Some(Tri), Some(Lim), Some(p1), true)
    assertEquals(z0e21.controls, ctlz0e21, msgz0e21)

    val msgz0e23 = s"Clicking $p3 should select it, but does not"
    val ctlz0e23 = Controls(Some(Sml), Some(Cir), Some(Blu), Some(p3), true)
    assertEquals(z0e23.controls, ctlz0e23, msgz0e23)

  // Move disabled
  test("Board position, selected differs from clicked pos and move is disabled"):
    val msgz0d11 = s"Clicking $p1 should select it, but does not"
    val ctlz0d11 = Controls(Some(Mid), Some(Tri), Some(Lim), Some(p1), false)
    assertEquals(z0d11.controls, ctlz0d11, msgz0d11)

    val msgz0d12 = s"Clicking $p2 should select it, but does not"
    val ctlz0d12 = Controls(Some(Sml), Some(Cir), Some(Blu), Some(p2), false)
    assertEquals(z0d12.controls, ctlz0d12, msgz0d12)

    val msgz0d21 = s"Clicking $p1 should select it, but does not"
    val ctlz0d21 = Controls(Some(Mid), Some(Tri), Some(Lim), Some(p1), false)
    assertEquals(z0d21.controls, ctlz0d21, msgz0d21)

    val msgz0d23 = s"Clicking $p3 should select it, but does not"
    val ctlz0d23 = Controls(Some(Sml), Some(Cir), Some(Blu), Some(p3), false)
    assertEquals(z0d23.controls, ctlz0d23, msgz0d23)
