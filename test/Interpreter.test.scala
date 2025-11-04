package tarski
package testing

class InterpreterTest extends munit.FunSuite:
  test("interpreter is correct on complex sentences in a world with 5 objects"):
    import Shape.*

    val b0 = Block(Small, Cir, Gray, "b")
    val b1 = Block(Small, Cir, Gray)
    val b2 = Block(Med, Tri, Green, "c")
    val b3 = Block(Small, Squ, Blue)
    val b4 = Block(Small, Squ, Blue, "a")

    val grid: Grid = Map(
      (1, 1) -> (b0, "b"),
      (1, 5) -> (b1, "block0"),
      (3, 3) -> (b2, "c"),
      (4, 2) -> (b3, "block1"),
      (6, 6) -> (b4, "a")
    )

    val world = World(grid)

    val sentences = Seq(
      fof"∃x ∃y ∃z (Squ(x) ∧ Cir(y) ∧ Tri(z))",
      fof"¬(∃x Large(x))", // careful with this, negation needs parentheses!
      fof"∀x (Cir(x) → ∃y (Squ(y) ∧ Back(x, y)))",
      fof"∀x (Cir(x) → ∃y (Squ(y) ∧ Back(x, y)))",
      fof"∀x (Cir(x) → ∃y (Squ(y) ∧ Back(x, y)))",
      fof"∃x ∃y (x != y ∧ ∀w ((w = x | w = y) → ∀z ¬Back(z, w)))",
      fof"∀x (Squ(x) ↔ ∃y (Tri(y) ∧ Back(y, x)))",
      fof"∀x ∀y (Larger(x, y) → ∃z Betw(x, y, z))",
      fof"¬(∀x ∀y (Left(x, y) ∨ Right(x, y)))", // same here!
      fof"∃x ∃y ¬(Front(x, y) ∨ Back(x, y))",
      fof"Small(a) ∧ Squ(a) ∧ Blue(a)",
      fof"Med(c) ∧ Tri(c) ∧ Green(c)",
      fof"Small(b) ∧ Cir(b) ∧ Gray(b)"
    )

    given Blocks = world.blocks
    val results  = sentences.map(eval)
    sentences
      .zip(results)
      .foreach: (sentence, result) =>
        assert(result, s"$sentence is false, expected true")
