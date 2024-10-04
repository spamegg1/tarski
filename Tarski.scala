package tarski

enum Tsize:
  case Small, Medium, Large
import Tsize.*

enum Tshape:
  case Triangle, Square, Circle
import Tshape.*

enum Tcolor:
  case Blue, Yellow, Gray, Pink, Black
import Tcolor.*

case class Block(size: Tsize, shape: Tshape, color: Tcolor)

def tick(state: State): State = state
def render(state: State): Image = BOARD
def click(point: Point, state: State): State = state
def move(point: Point, state: State): State = state
def stop(state: State): Boolean = false
