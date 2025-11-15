package tarski
package main

def run(pb: PosBlock, formulas: Seq[FOLFormula], scaleFactor: Double = 1.0) =
  given c: Constants = Constants(Size * scaleFactor)
  val world          = World.from(pb, formulas)
  Reactor
    .init[World](world)
    .withOnTick(tick)
    .withRender(Render.all)
    .withOnMouseClick(click)
    .withOnMouseMove(move)
    .withStop(stop)
    .withTickRate(TickRate)
    .animateWithFrame(c.MainFrame)

// Example world and formulas to run
given c: Constants = Constants(Size)
import Shape.*, c.{Small, Mid, Large}

val pb: PosBlock = Map(
  (1, 2) -> Block(Small, Tri, Green, "a"),
  (3, 4) -> Block(Mid, Tri, Blue),
  (5, 6) -> Block(Large, Cir, Gray, "d"),
  (6, 3) -> Block(Small, Squ, Blue)
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
def example = run(pb, formulas)
