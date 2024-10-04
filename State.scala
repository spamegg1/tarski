package tarski

case class State(board: List[List[Block]])

def tick(state: State): State = state
def render(state: State): Image = BOARD
def click(point: Point, state: State): State = state
def move(point: Point, state: State): State = state
def stop(state: State): Boolean = false
