package tarski

val init = State(Nil)

@main
def run =
  Reactor
    .init[State](init)
    .withOnTick(tick) // State -> State
    .withRender(render) // State -> Image
    .withOnMouseClick(click) // Point State -> State
    .withOnMouseMove(move) // Point State => State
    .withStop(stop) // State => Boolean
    .withTickRate(TICKRATE)
    // .draw(FRAME)
    .run(FRAME)

@main
def board: Unit =
  BOARD.draw()
