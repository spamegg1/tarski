package tarski
package main

val world =
  import Shape.*
  World.empty
    .addBlockAt((1, 2), Block(Small, Tri, Green))
    .addBlockAt((3, 4), Block(Mid, Tri, Blue))
    .addBlockAt((5, 6), Block(Large, Cir, Gray))
    .addBlockAt((6, 2), Block(Small, Squ, Blue))
    .addNameToBlockAt((1, 2), "a")
    .addNameToBlockAt((3, 4), "b")
    .addNameToBlockAt((5, 6), "c")
    .addNameToBlockAt((6, 2), "d")
// .addFormula(fof"¬(∃x Large(x))")
// .addFormula(fof"∀x Squ(x)")
// .addFormula(fof"∀x ¬ Cir(x)")
// .addFormula(fof"¬(∀x Small(x))")
// .addFormula(fof"∃x Tri(x)")
// .addFormula(fof"∀x Large(x)")
// .addFormula(fof"∃x Cir(x)")
// .addFormula(fof"a = b")
// .addFormula(fof"∀x ∃y Larger(x, y)")
// .addFormula(fof"a = b")
// .addFormula(fof"c != d")
// .addFormula(fof"∀x (Squ(x) ∨ Tri(x))")
// .addFormula(fof"∀x (Large(x) ∨ Squ(x))")
// .addFormula(fof"∃x (Tri(x) ∧ Mid(x))")
// .addFormula(fof"¬(∃x (Cir(x) ∧ Small(x)))")
// .addFormula(fof"∃y (Squ(y) ∧ Small(y))")

def run(grid: Grid, formulas: Seq[FOLFormula], scaleFactor: Double = 1.0) =
  given c: Constant = Constant(Size * scaleFactor)
  val world         = World(grid = grid, formulas = Formulas.fromSeq(formulas))
  Reactor
    .init[World](world)      // these functions are in Reactor.scala
    .withOnTick(tick)        // World -> World
    .withRender(render)      // World -> Image
    .withOnMouseClick(click) // Point World -> World
    .withOnMouseMove(move)   // Point World => World
    .withStop(stop)          // World => Boolean
    .withTickRate(TickRate)
    .animateWithFrame(c.MainFrame)

@main
def main = Reactor
  .init[World](world)      // these functions are in Reactor.scala
  .withOnTick(tick)        // World -> World
  .withRender(render)      // World -> Image
  .withOnMouseClick(click) // Point World -> World
  .withOnMouseMove(move)   // Point World => World
  .withStop(stop)          // World => Boolean
  .withTickRate(TickRate)
  .animateWithFrame(MainFrame)
