package tarski

class InterpreterTest extends munit.FunSuite:
  test("interpreter is correct in a world with 5 objects and 10 sentences"):
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

    val sentences = Seq(
      fof"∃x ∃y ∃z (Square(x) ∧ Circle(y) ∧ Triangle(z))",
      fof"¬(∃x Large(x))", // careful with this, negation needs parentheses!
      fof"∀x (Circle(x) → ∃y (Square(y) ∧ BackOf(x, y)))",
      fof"∀x (Circle(x) → ∃y (Square(y) ∧ BackOf(x, y)))",
      fof"∀x (Circle(x) → ∃y (Square(y) ∧ BackOf(x, y)))",
      fof"?x ?y (x != y & !w ((w = x | w = y) -> !z -BackOf(z, w)))",
      fof"∀x (Square(x) ↔ ∃y (Triangle(y) ∧ BackOf(y, x)))",
      fof"∀x ∀y (Larger(x, y) → ∃z Between(x, y, z))",
      fof"¬(∀x ∀y (LeftOf(x, y) | RightOf(x, y)))", // same here!
      fof"∃x ∃y ¬(FrontOf(x, y) | BackOf(x, y))"
    )
    val results  = sentences.map(eval)
    val expected = (0 until 10).map(_ => true)
    sentences
      .zip(results)
      .foreach: (sentence, result) =>
        assert(result, s"$sentence is false, expected true")
