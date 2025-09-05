package tarski

def tick(world: World): World         = world
def render(world: World): Image       = Board
def click(point: Point, world: World) = world
def move(point: Point, world: World)  = world
def stop(world: World): Boolean       = false
