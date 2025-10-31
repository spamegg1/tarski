package tarski
package main

val controls = Controls(Some(Small), Some(Tri), Some(Green), None, true)
val world = World(controls = controls)
  .addBlockAt((1, 2), Block(Small, Tri, Green))
  .addBlockAt((3, 4), Block(Medium, Tri, Blue))
  .addBlockAt((5, 6), Block(Large, Cir, Gray))
  .addBlockAt((6, 2), Block(Small, Squ, Blue))
  .addNameToBlockAt((1, 2), "a")
  .addNameToBlockAt((3, 4), "b")
  .addNameToBlockAt((5, 6), "c")
  .addNameToBlockAt((6, 2), "d")
  .addFormula(fof"¬(∃x Large(x))") // 16 formulas seem to be the max that fits
  .addFormula(fof"∀x Square(x)")
  .addFormula(fof"∀x ¬ Circle(x)")
  .addFormula(fof"¬(∀x Small(x))")
  .addFormula(fof"∃x Triangle(x)")
  .addFormula(fof"∀x Large(x)")
  .addFormula(fof"∃x Circle(x)")
  .addFormula(fof"a = b")
  .addFormula(fof"∀x ∃y Larger(x, y)")
  .addFormula(fof"a = b")
  .addFormula(fof"c != d")
  .addFormula(fof"∀x (Square(x) ∨ Triangle(x))")
  .addFormula(fof"∀x (Large(x) ∨ Square(x))")
  .addFormula(fof"∃x (Triangle(x) ∧ Large(x))")
  .addFormula(fof"¬(∃x (Circle(x) ∧ Small(x)))")
  .addFormula(fof"∃y (Square(y) ∧ Small(y))")
  .selectPos((row = 5, col = 6))

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
