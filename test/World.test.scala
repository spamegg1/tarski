package tarski
package testing

class WorldTest extends munit.FunSuite:
  private given c: Constants = Constants(DefaultSize)
  import Shape.*, Status.*, Sizes.*, Tone.*

  val b0  = Block(Small, Cir, Orange)                 // at (1,2), then (3,4)
  val b1  = Block(Mid, Tri, Green)                    // at (5,6)
  val w0  = World.empty
  val w1  = w0.removeBlockAt((1, 2))
  val w2  = w1.removeNameFromBlockAt((3, 4))
  val w3  = w2.moveBlock((3, 4), (1, 2))
  val w4  = w3.addBlockAt((1, 2), b0)                 // b0 at 1,2
  val w5  = w4.removeBlockAt((3, 4))                  // b0 at 1,2
  val w6  = w5.removeNameFromBlockAt((3, 4))          // b0 at 1,2
  val w7  = w6.removeNameFromBlockAt((1, 2))          // b0 at 1,2
  val w8  = w7.addNameToBlockAt((3, 4), "b")          // b0 at 1,2
  val w9  = w8.addNameToBlockAt((1, 2), "b")          // b0 at 1,2
  val b0n = b0.setLabel("b")                          // b0 at 1,2
  val w10 = w9.addNameToBlockAt((1, 2), "c")          // b0 at 1,2
  val w11 = w10.moveBlock(from = (3, 4), to = (1, 2)) // None at 3,4 -> b0 at 1,2
  val w12 = w11.moveBlock(from = (1, 2), to = (3, 4)) // b0 at 1,2 -> None at 3,4
  val w13 = w12.addBlockAt((3, 4), b1)                // b0 at 3,4
  val w14 = w13.addBlockAt((5, 6), b1)                // b0 at 3,4, b1 at 5,6
  val w15 = w14.moveBlock((5, 6), (3, 4))             // b1 at 5,6 -> b0 at 3,4
  val w16 = w15.removeNameFromBlockAt((3, 4))         // b1 at 5,6

  test("Empty world with no blocks or grid and all names available"):
    assert(w0.nameGrid.isEmpty, s"empty world should have no blocks, but has ${w0.nameGrid}")
    assert(w0.board.grid.isEmpty, s"empty world should have no grid, but has ${w0.board.grid}")
    assert(
      w0.names.values.forall(_ == Available),
      s"empty world should have all 6 names available, but has names ${w0.names}"
    )

  test("Moving / removing a block, or removing a name in an empty world"):
    assertEquals(w1, w0, "removing a block from an empty world should not work, but did")
    assertEquals(w2, w1, "removing a name in an empty world should not work, but did")
    assertEquals(w3, w2.selectPos((1, 2)), "moving a block in an empty world should not work, but did")

  test("World with 1 unnamed block and its grid, blocks and names"):
    val ob41 = w4.board.grid.keys.toSeq
    val ex41 = Seq((1, 2))
    assertEquals(ob41, ex41, s"world should have grid keys $ex41 but has $ob41")
    val ob42 = w4.board.grid((1, 2)).block
    assertEquals(ob42, b0, s"world should have block $b0 at pos (1, 2), but has $ob42")
    val ob43 = w4.nameGrid.values.toSeq
    val ex43 = Seq((b0, (1, 2)))
    assertEquals(ob43, ex43, s"world should have blocks values $ex43 but has $ob43")
    assert(
      w4.names.values.forall(_ == Available),
      "world with 1 unnamed block added should have all names available, but does not"
    )

  test("Removing a block or a name at wrong position, or from a nameless block"):
    assertEquals(w5, w4, "removing block at wrong position should not work, but does")
    assertEquals(w6, w5, "removing name from block at wrong pos should not work, but does")
    assertEquals(w7, w6, "removing name from nameless block should not work, but does")

  test("Adding a name to a block, in wrong / correct position"):
    assertEquals(w8, w7, "adding name to block at wrong pos should not work, but does")
    assertEquals(w9.nameGrid("b").block, b0n, "adding name to block should change its label correctly, but does not")
    assert(
      w9.names.forall: (name, status) =>
        status == (if name == "b" then Occupied else Available),
      "adding name `b` to block should make only `b` occupied, but does not"
    )

  test("Adding a name to an already named block"):
    assertEquals(w10, w9, "adding a name to an already named block should not work, but does")

  test("Moving a block in a world with 1 block, wrong position"):
    assertEquals(w11, w10.selectPos((1, 2)), "moving a block at wrong position should not work, but does")

  test("Moving a block in a world with 1 block, correct position"):
    assertEquals(
      w12.board.grid,
      Map[Pos, (Block, String)]((3, 4) -> (b0n, "b")),
      "moving a block from correct position should work, but does not"
    )
    assertEquals(
      w12.nameGrid,
      Map("b" -> (b0n, (3, 4))),
      "moving a block from correct position should work, but does not"
    )
    assert(
      w12.names.forall: (name, status) =>
        status == (if name == "b" then Occupied else Available),
      "moving a block from correct position should not change availability, but does"
    )

  test("Adding a second block with a fake name to a world with 1 block"):
    assertEquals(w13, w12, "adding a block on top of a block should not work, but does")
    assertEquals(
      w14.board.grid((3, 4)),
      (b0n, "b"),
      s"adding a second block with fake name should not affect named block $b0n, but does"
    )
    assertEquals(
      w14.board.grid((5, 6)).block,
      b1,
      s"adding a second block $b1 with fake name should work, but does not"
    )
    assertEquals(
      w14.nameGrid.values.toSeq,
      Seq((b0n, (3, 4)), (b1, (5, 6))),
      s"adding a second block with fake name should work correctly, but does not"
    )
    assert(
      w14.names.forall: (name, status) =>
        status == (if name == "b" then Occupied else Available),
      "adding a second nameless block should not affect availability, but does"
    )

  test("Moving a block to an occupied space"):
    assertEquals(w15, w14, "moving a block to an occupied space should not work, but does")

  test("Removing a name in a world with 2 blocks"):
    assertEquals(w16.board.grid((3, 4)).block, b0, "removing name from named block should work correctly, but does not")
    assertEquals(w16.board.grid((5, 6)).block, b1, "removing name from named block should work correctly, but does not")
    assertEquals(
      w16.nameGrid.values.toSeq,
      Seq((b0, (3, 4)), (b1, (5, 6))),
      "removing name from named block should work correctly, but does not"
    )
    assert(
      w16.names.forall((name, status) => status == Available),
      "removing name `b` should make it available, but does not"
    )
    assert(
      w16.board.grid(3, 4).block.label.isEmpty,
      "removing name from a named block should reset its label, but does not"
    )
