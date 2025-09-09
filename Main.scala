package tarski

val init = World(Map(), Map())

@main
def run =
  Reactor
    .init[World](init)       // these functions are in Reactor.scala
    .withOnTick(tick)        // World -> World
    .withRender(render)      // World -> Image
    .withOnMouseClick(click) // Point World -> World
    .withOnMouseMove(move)   // Point World => World
    .withStop(stop)          // World => Boolean
    .withTickRate(TickRate)
    // .draw(frame)
    .run(frame)

@main
def board: Unit =
  Board.draw()
