package tarski
package testing

class InterpreterTest extends munit.FunSuite:
  import Shape.*, Sizes.*, Tone.*

  private given c: Constants = Constants(DefaultSize)

  val b0 = Block(Small, Cir, Orange, "b")
  val b1 = Block(Small, Cir, Orange)
  val b2 = Block(Mid, Tri, Green, "c")
  val b3 = Block(Small, Squ, Blue)
  val b4 = Block(Small, Squ, Blue, "a")

  private val grid: Grid = Map(
    (1, 1) -> b0,
    (1, 5) -> b1,
    (3, 3) -> b2,
    (4, 2) -> b3,
    (6, 6) -> b4
  )

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
    fof"Small(b) ∧ Cir(b) ∧ Orange(b)"
  )

  private val world = World.from(grid, sentences)

  test("interpreter is correct on complex sentences in a world with 5 objects"):
    given NameGrid = world.nameGrid
    val results    = sentences.map(Interpreter.eval)
    sentences
      .zip(results)
      .foreach: (sentence, result) =>
        assert(result, s"$sentence is false, expected true")

  test("Interpreter should throw NSE exception on formulas with missing objects in them"):
    import java.util.NoSuchElementException as NSE
    given NameGrid = world.nameGrid
    try
      Interpreter.eval(fof"c = d")
      assert(false) // should not reach here! must throw ex on prev line
    catch
      case ex: NSE => assert(true)
      case _       => assert(false) // should not throw any other kind of ex

  test("Interpreter should handle `Betw` predicate correctly"):
    given NameGrid = Map(
      "d" -> (Block(Large, Squ, Blue, "d"), (0, 1)),
      "e" -> (Block(Large, Cir, Green, "e"), (0, 4)),
      "f" -> (Block(Large, Tri, Orange, "f"), (0, 7)),
      "c" -> (Block(Mid, Squ, Green, "c"), (4, 3)),
      "a" -> (Block(Small, Tri, Green, "a"), (6, 1)),
      "b" -> (Block(Small, Squ, Orange, "b"), (6, 4))
    )
    assert(!Interpreter.eval(fof"Betw(d, e, f)"))
    assert(Interpreter.eval(fof"Betw(e, d, f)"))
    assert(Interpreter.eval(fof"Betw(c, a, f)"))
    assert(!Interpreter.eval(fof"Betw(c, d, b)"))
    assert(!Interpreter.eval(fof"Betw(c, b, e)"))
    assert(!Interpreter.eval(fof"Betw(c, c, c)"))
    assert(!Interpreter.eval(fof"Betw(c, a, e)"))
