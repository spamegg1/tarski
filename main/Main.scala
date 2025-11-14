package tarski
package main

def run(grid: Grid, formulas: Seq[FOLFormula], scaleFactor: Double = 1.0) =
  given c: Constants = Constants(Size * scaleFactor)
  val world          = World(grid = grid, formulas = Formulas.fromSeq(formulas))
  Reactor
    .init[World](world)
    .withOnTick(tick)
    .withRender(Render.apply)
    .withOnMouseClick(click)
    .withOnMouseMove(move)
    .withStop(stop)
    .withTickRate(TickRate)
    .animateWithFrame(c.MainFrame)

// Example world and formulas to run
given c: Constants = Constants(Size)
import Shape.*, c.{Small, Mid, Large}

val grid: Grid = Map(
  (1, 2) -> (Block(Small, Tri, Green), "a"),
  (3, 4) -> (Block(Mid, Tri, Blue), "b"),
  (5, 6) -> (Block(Large, Cir, Gray), "d"),
  (6, 2) -> (Block(Small, Squ, Blue), "f")
)

val formulas = Seq(
  fof"¬(∃x Large(x))",
  fof"∀x Squ(x)",
  fof"∀x ¬ Cir(x)",
  fof"¬(∀x Small(x))",
  fof"∃x Tri(x)",
  fof"∀x Large(x)",
  fof"∃x Cir(x)",
  fof"a = b",
  fof"∀x ∃y Larger(x, y)",
  fof"a = b",
  fof"c != d",
  fof"∀x (Squ(x) ∨ Tri(x))",
  fof"∀x (Large(x) ∨ Squ(x))",
  fof"∃x (Tri(x) ∧ Mid(x))",
  fof"¬(∃x (Cir(x) ∧ Small(x)))",
  fof"∃y (Squ(y) ∧ Small(y))"
)

@main
def example = run(grid, formulas)
