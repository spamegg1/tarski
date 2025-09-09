package tarski

class WorldTest extends munit.FunSuite:
  test("World add, move, remove, rename blocks integration test"):
    val b0 = Block(Small, Cir, Gray)   // at (1,2), then (3,4)
    val b1 = Block(Medium, Tri, Green) // at (5,6)

    // 0. empty world
    val w0 = World.empty
    assert(w0.blocks.isEmpty, s"empty world should have no blocks, but has ${w0.blocks}")
    assert(w0.grid.isEmpty, s"empty world should have no grid, but has ${w0.grid}")
    assert(
      w0.names.values.forall(_ == Available),
      s"empty world should have all 6 names available, but has names ${w0.names}"
    )

    // 1. attempt to remove block from empty world
    val w1 = w0.removeBlockAt((1, 2))
    assert(w1 == w0, "removing a block from an empty world should not work, but did")

    // 2. attemt to remove name from a block in empty world
    val w2 = w1.removeNameFromBlockAt((3, 4))
    assert(w2 == w1, "removing a name in an empty world should not work, but did")

    // 3. attemt to move a block in empty world
    val w3 = w2.moveBlock((3, 4), (1, 2))
    assert(w3 == w2, "moving a block in an empty world should not work, but did")

    // 4. add one block: initially, it has no name, just fake name block0
    val w4  = w3.addBlockAt((1, 2))(b0)
    val g4  = Map((1, 2) -> (b0, "block0"))
    val bl4 = Map("block0" -> (b0, (1, 2)))
    assert(w4.grid == g4, s"world with 1 block should have grid $g4 but has ${w4.grid}")
    assert(
      w4.blocks == bl4,
      s"world with 1 unnamed block should have blocks $bl4 but has ${w4.blocks}"
    )
    assert(
      w4.names.values.forall(_ == Available),
      "world with 1 unnamed block added should have all names available, but does not"
    )

    // 5. attempt to remove the block at wrong position
    val w5 = w4.removeBlockAt((3, 4))
    assert(w5 == w4, "removing block at wrong position should not work, but does")

    // 6. attempt to remove name from block at wrong position
    val w6 = w5.removeNameFromBlockAt((3, 4))
    assert(w6 == w5, "removing name from block at wrong pos should not work, but does")

    // 7. attempt to remove name from nameless block, at correct position
    val w7 = w6.removeNameFromBlockAt((1, 2))
    assert(w7 == w6, "removing name from nameless block should not work, but does")

    // 8. attempt to add name to nameless block, at wrong position
    val w8 = w7.addNameToBlockAt((3, 4))("b")
    assert(w8 == w7, "adding name to block at wrong pos should not work, but does")

    // 9. attempt to add name to nameless block, at correct position
    val w9      = w8.addNameToBlockAt((1, 2))("b")
    val b0named = b0.setLabel("b")
    assert(
      w9.blocks("b").block == b0named,
      "adding name to block should change its label correctly, but does not"
    )
    assert(
      w9.names.forall: (name, status) =>
        status == (if name == "b" then Occupied else Available),
      "adding name `b` to block should make only `b` occupied, but does not"
    )

    // 10. attempt to add name to an already named block
    val w10 = w9.addNameToBlockAt((1, 2))("c")
    assert(w10 == w9, "adding name to already named block should not work, but does")

    // 11. attempt to move a block from wrong position
    val w11 = w10.moveBlock(from = (3, 4), to = (1, 2))
    assert(w11 == w10, "moving a block at wrong position should not work, but does")

    // 12. attempt to move a block from correct position
    val w12 = w11.moveBlock(from = (1, 2), to = (3, 4))
    assert(
      w12.grid == Map((3, 4) -> (b0named, "b")),
      "moving a block from correct position should work, but does not"
    )
    assert(
      w12.blocks == Map("b" -> (b0named, (3, 4))),
      "moving a block from correct position should work, but does not"
    )
    assert(
      w12.names.forall: (name, status) =>
        status == (if name == "b" then Occupied else Available),
      "moving a block from correct position should not change availability, but does"
    )

    // 13. attempting to add a block to an already taken position
    val w13 = w12.addBlockAt((3, 4))(b1)
    assert(w13 == w12, "adding a block on top of a block should not work, but does")

    // 14. attempting to add a second block, with fake name initially
    val w14 = w13.addBlockAt((5, 6))(b1)
    assert(
      w14.grid == Map((3, 4) -> (b0named, "b"), (5, 6) -> (b1, "block1")),
      "adding a second block with fake name should work correctly, but does not"
    )
    assert(
      w14.blocks == Map("b" -> (b0named, (3, 4)), "block1" -> (b1, (5, 6))),
      "adding a second block with fake name should work correctly, but does not"
    )
    assert(
      w14.names.forall: (name, status) =>
        status == (if name == "b" then Occupied else Available),
      "adding a second nameless block should not affect availability, but does"
    )

    // 15. attempting to move block to an already occupied position
    val w15 = w14.moveBlock((5, 6), (3, 4))
    assert(w15 == w14, "moving a block to an occupied space should not work, but does")

    // 16. removing a name from a named block, getting a new fake name
    val w16 = w15.removeNameFromBlockAt((3, 4))
    assert(
      w16.grid == Map((3, 4) -> (b0, "block2"), (5, 6) -> (b1, "block1")),
      "removing name from named block should work correctly, but does not"
    )
    assert(
      w16.blocks == Map("block2" -> (b0, (3, 4)), "block1" -> (b1, (5, 6))),
      "removing name from named block should work correctly, but does not"
    )
    assert(
      w16.names.forall((name, status) => status == Available),
      "removing name `b` should make it available, but does not"
    )
    assert(
      w16.grid(3, 4).block.label.isEmpty,
      "removing name from a named block should reset its label, but does not"
    )
