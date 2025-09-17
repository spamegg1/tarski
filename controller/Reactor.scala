package tarski

import model.World

def tick(world: World): World         = world
def click(point: Point, world: World) = world
def move(point: Point, world: World)  = world
def stop(world: World): Boolean       = false
