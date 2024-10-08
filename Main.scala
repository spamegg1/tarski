package tarski

val emptyBoard = Vector.fill(64)(None: Option[Block])
val init = State(emptyBoard, Vector[Formula]())

@main
def run =
  Reactor
    .init[State](init) // these functions are in Reactor.scala
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
