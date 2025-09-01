package tarski

val init = State(8)

@main
def run =
  Reactor
    .init[State](init)       // these functions are in Reactor.scala
    .withOnTick(tick)        // State -> State
    .withRender(render)      // State -> Image
    .withOnMouseClick(click) // Point State -> State
    .withOnMouseMove(move)   // Point State => State
    .withStop(stop)          // State => Boolean
    .withTickRate(TickRate)
    .draw(frame)
  // .run(frame)

@main
def board: Unit =
  Board.draw()
