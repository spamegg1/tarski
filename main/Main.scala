package tarski
package main

val controls = Controls(Some(Small), Some(Tri), Some(Green), Some("a"), None, true)
val world = World(controls = controls)
  .addBlockAt((1, 2))(Block(Small, Tri, Green))
  .addBlockAt((3, 4))(Block(Medium, Tri, Blue))
  .addBlockAt((5, 6))(Block(Large, Tri, Gray))
  .addNameToBlockAt((1, 2))("a")
  .addNameToBlockAt((3, 4))("b")
  .addNameToBlockAt((5, 6))("c")
  .addFormula(fof"¬(∃x Large(x))") // 17 formulas seem to be the max that fits
  .addFormula(fof"!x (P(x,f(x)) -> ?y P(x,y))")
  .addFormula(fof"!x (P(x,f(f(x))) -> ?y P(x,y))")
  .addFormula(fof"∀x ∃y ∀z f(z) = g(h(z))")
  .addFormula(fof"a != b")
  .addFormula(fof"∀x Large(x)")
  .addFormula(fof"∃x (P(x,f(x)) -> ∀y P(x,y))")
  .addFormula(fof"∀x (P(x,f(f(x))) -> ∀y P(x,y))")
  .addFormula(fof"∃x ∀y ∃z f(z) = g(h(z))")
  .addFormula(fof"a = b")
  .addFormula(fof"∀x ∃y Larger(x, y)")
  .addFormula(fof"∃x ∀y (P(x,f(y)) -> ∀z P(y,z))")
  .addFormula(fof"∃x ∀z (P(x,f(f(z))) -> ∃y P(z,y))")
  .addFormula(fof"∃x ∀y ∃z h(y) = g(z, f(x))")
  .addFormula(fof"e = f")
  .addFormula(fof"c != d")
  .addFormula(fof"c = d")
  .selectPos((row = 5, col = 6))

@main
def main = Reactor
  .init[World](world)      // these functions are in Reactor.scala
  .withOnTick(identity)    // World -> World
  .withRender(render)      // World -> Image
  .withOnMouseClick(click) // Point World -> World
  .withOnMouseMove(move)   // Point World => World
  .withStop(stop)          // World => Boolean
  .withTickRate(TickRate)
  .draw(MainFrame)
// .run(MainFrame)
