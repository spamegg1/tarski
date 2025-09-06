package tarski

class WorldTest extends munit.FunSuite:
  test("world add/remove blocks"):
    val b0 = Block(Small, Cir, Gray, "b")
    val b1 = Block(Small, Cir, Gray)
    val b2 = Block(Medium, Tri, Black, "c")
    val b3 = Block(Small, Squ, Blue)
    val b4 = Block(Small, Squ, Blue, "a")

    val grid: Grid = Map(
      (1, 1) -> (b0, "b"),
      (1, 5) -> (b1, "block0"),
      (3, 3) -> (b2, "c"),
      (4, 2) -> (b3, "block1"),
      (6, 6) -> (b4, "a")
    )

    given blocks: Blocks = Map(
      "b"      -> (b0, (1, 1)),
      "block0" -> (b1, (1, 5)),
      "c"      -> (b2, (3, 3)),
      "block1" -> (b3, (4, 2)),
      "a"      -> (b4, (6, 6))
    )
    val world = World(grid, blocks)

    val results  = (0 until 10).map(_ => true)
    val expected = (0 until 10).map(_ => true)
    results
      .zip(expected)
      .foreach: (sentence, result) =>
        assert(result, s"$sentence is false, expected true")
