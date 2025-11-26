package tarski
package testing

class InterpreterTest extends munit.FunSuite:
  private given c: Constants = Constants(DefaultSize)
  import Shape.*, Sizes.*, Tone.*

  val b0 = Block(Small, Cir, Gray, "b")
  val b1 = Block(Small, Cir, Gray)
  val b2 = Block(Mid, Tri, Green, "c")
  val b3 = Block(Small, Squ, Blue)
  val b4 = Block(Small, Squ, Blue, "a")

  private val grid: PosGrid = Map(
    (1, 1) -> (b0, "b"),
    (1, 5) -> (b1, "block0"),
    (3, 3) -> (b2, "c"),
    (4, 2) -> (b3, "block1"),
    (6, 6) -> (b4, "a")
  )

  private val world = World(grid)

  private val sentences = Seq(
    fof"∃x ∃y ∃z (Squ(x) ∧ Cir(y) ∧ Tri(z))",
    fof"¬(∃x Large(x))", // careful with this, negation needs parentheses!
    fof"∀x (Cir(x) → ∃y (Squ(y) ∧ Above(x, y)))",
    fof"∀x (Cir(x) → ∃y (Squ(y) ∧ Above(x, y)))",
    fof"∀x (Cir(x) → ∃y (Squ(y) ∧ Above(x, y)))",
    fof"∃x ∃y (x != y ∧ ∀w ((w = x | w = y) → ∀z ¬Above(z, w)))",
    fof"∀x (Squ(x) ↔ ∃y (Tri(y) ∧ Above(y, x)))",
    fof"∀x ∀y (Larger(x, y) → ∃z Betw(x, y, z))",
    fof"¬(∀x ∀y (Left(x, y) ∨ Right(x, y)))", // same here!
    fof"∃x ∃y ¬(Below(x, y) ∨ Above(x, y))",
    fof"Small(a) ∧ Squ(a) ∧ Blue(a)",
    fof"Mid(c) ∧ Tri(c) ∧ Green(c)",
    fof"Small(b) ∧ Cir(b) ∧ Gray(b)"
  )

  private given NameGrid = world.nameGrid

  test("interpreter is correct on complex sentences in a world with 5 objects"):
    val results = sentences.map(Interpreter.eval)
    sentences
      .zip(results)
      .foreach: (sentence, result) =>
        assert(result, s"$sentence is false, expected true")

  test("Interpreter should throw NSE exception on formulas with missing objects in them"):
    import java.util.NoSuchElementException as NSE
    try
      Interpreter.eval(fof"c = d")
      assert(false) // should not reach here! must throw ex on prev line
    catch
      case ex: NSE => assert(true)
      case _       => assert(false) // should not throw any other kind of ex
