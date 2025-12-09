package tarski
package testing

class InterpreterTest extends munit.FunSuite:
  import Shape.*, Sizes.*, Tone.*

  private given c: Constants = Constants(DefaultSize)

  val b0 = Block(Sml, Cir, Red, "b")
  val b1 = Block(Sml, Cir, Red)
  val b2 = Block(Mid, Tri, Lim, "c")
  val b3 = Block(Sml, Sqr, Blu)
  val b4 = Block(Sml, Sqr, Blu, "a")

  private val grid: Grid = Map(
    (1, 1) -> b0,
    (1, 5) -> b1,
    (3, 3) -> b2,
    (4, 2) -> b3,
    (6, 6) -> b4
  )

  private val sentences = Seq(
    fof"∃x ∃y ∃z (Sqr(x) ∧ Cir(y) ∧ Tri(z))",
    fof"¬(∃x Big(x))", // careful with this, negation needs parentheses!
    fof"∀x (Cir(x) → ∃y (Sqr(y) ∧ Abv(x, y)))",
    fof"∀x (Cir(x) → ∃y (Sqr(y) ∧ Abv(x, y)))",
    fof"∀x (Cir(x) → ∃y (Sqr(y) ∧ Abv(x, y)))",
    fof"∃x ∃y (x != y ∧ ∀w ((w = x | w = y) → ∀z ¬Abv(z, w)))",
    fof"∀x (Sqr(x) ↔ ∃y (Tri(y) ∧ Abv(y, x)))",
    fof"∀x ∀y (More(x, y) → ∃z Btw(x, y, z))",
    fof"¬(∀x ∀y (Left(x, y) ∨ Rgt(x, y)))", // same here!
    fof"∃x ∃y ¬(Bel(x, y) ∨ Abv(x, y))",
    fof"Sml(a) ∧ Sqr(a) ∧ Blu(a)",
    fof"Mid(c) ∧ Tri(c) ∧ Lim(c)",
    fof"Sml(b) ∧ Cir(b) ∧ Red(b)"
  )

  private val world = World.from(grid, sentences)

  test("interpreter is correct on complex sentences in a world with 5 objects"):
    given NameMap = world.nameMap
    val results   = sentences.map(Interpreter.eval)
    sentences
      .zip(results)
      .foreach: (sentence, result) =>
        assert(result, s"$sentence is false, expected true")

  test("Interpreter should throw NSE exception on formulas with missing objects in them"):
    import java.util.NoSuchElementException as NSE
    given NameMap = world.nameMap
    try
      Interpreter.eval(fof"c = d")
      assert(false) // should not reach here! must throw ex on prev line
    catch
      case ex: NSE => assert(true)
      case _       => assert(false) // should not throw any other kind of ex

  test("Interpreter should handle `Btw` predicate correctly"):
    given NameMap = Map(
      "d" -> (Block(Big, Sqr, Blu, "d"), (0, 1)),
      "e" -> (Block(Big, Cir, Lim, "e"), (0, 4)),
      "f" -> (Block(Big, Tri, Red, "f"), (0, 7)),
      "c" -> (Block(Mid, Sqr, Lim, "c"), (4, 3)),
      "a" -> (Block(Sml, Tri, Lim, "a"), (6, 1)),
      "b" -> (Block(Sml, Sqr, Red, "b"), (6, 4))
    )
    assert(!Interpreter.eval(fof"Btw(d, e, f)"))
    assert(Interpreter.eval(fof"Btw(e, d, f)"))
    assert(Interpreter.eval(fof"Btw(c, a, f)"))
    assert(!Interpreter.eval(fof"Btw(c, d, b)"))
    assert(!Interpreter.eval(fof"Btw(c, b, e)"))
    assert(!Interpreter.eval(fof"Btw(c, c, c)"))
    assert(!Interpreter.eval(fof"Btw(c, a, e)"))
