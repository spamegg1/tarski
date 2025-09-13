package tarski

val world = World.empty
  .addBlockAt((1, 2))(Block(Small, Tri, Green))
  .addBlockAt((3, 4))(Block(Medium, Tri, Blue))
  .addBlockAt((5, 6))(Block(Large, Tri, Gray))
  .addNameToBlockAt((1, 2))("a")
  .addNameToBlockAt((3, 4))("b")
  .addNameToBlockAt((5, 6))("c")
  .addFormula(fof"¬(∃x Large(x))")
  .addFormula(fof"!x (P(x,f(x)) -> ?y P(x,y))")

@main
def run =
  given Dimensions = (800, 800)
  given GridSize   = (8, 8)
  Reactor
    .init[World](world)      // these functions are in Reactor.scala
    .withOnTick(tick)        // World -> World
    .withRender(render)      // World -> Image
    .withOnMouseClick(click) // Point World -> World
    .withOnMouseMove(move)   // Point World => World
    .withStop(stop)          // World => Boolean
    .withTickRate(TickRate)
    .draw(MainFrame)
  // .run(frame)

@main
def board: Unit =
  Board.draw()
