package tarski

given Dimensions = (800, 800)
given GridSize   = (8, 8)

val world = World.empty
  .addBlockAt((1, 2))(Block(Small, Squ, Green))
  .addBlockAt((3, 4))(Block(Medium, Tri, Blue))
  .addBlockAt((5, 6))(Block(Large, Cir, Gray))
  .addNameToBlockAt((1, 2))("a")
  .addNameToBlockAt((3, 4))("b")
  .addNameToBlockAt((5, 6))("c")

@main
def run =
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
