package tarski

def tick(state: State): State                = state
def render(state: State): Image              = Board
def click(point: Point, state: State): State = state
def move(point: Point, state: State): State  = state
def stop(state: State): Boolean              = false
