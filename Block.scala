package tarski

enum Tsize:
  case Small, Medium, Large
import Tsize.*

enum Tshape:
  case Triangle, Square, Circle
import Tshape.*

enum Tcolor:
  case Blue, Black, Gray
import Tcolor.*

case class Block(size: Tsize, shape: Tshape, color: Tcolor)
