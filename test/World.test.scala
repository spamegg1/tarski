package tarski

class WorldTest extends munit.FunSuite:
  test("World add, move, remove, rename blocks integration test"):
    val b0 = Block(Small, Cir, Gray)
    val b1 = Block(Medium, Tri, Black)
    val b2 = Block(Small, Squ, Blue)

    val w0 = World.empty
    assert(w0.blocks.isEmpty, s"empty world should have no blocks, but has ${w0.blocks}")
    assert(w0.grid.isEmpty, s"empty world should have no grid, but has ${w0.grid}")
    assert(
      w0.names.values.forall(_ == Available),
      s"empty world should have all 6 names available, but has names ${w0.names}"
    )

    val w1 = w0.removeBlockAt((1, 2))
    assert(w1 == w0, "removing a block from an empty world should not work, but did")

    val w2 = w1.removeNameFromBlockAt((3, 4))
    assert(w2 == w1, "removing a name in an empty world should not work, but did")

    val w3 = w2.moveBlock((3, 4), (1, 2))
    assert(w3 == w2, "moving a block in an empty world should not work, but did")

    // add 1 block: initially, it has no name, just fake name block0
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
      s"world with 1 unnamed block added should have all names available, but doesn't"
    )
