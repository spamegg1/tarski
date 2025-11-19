package tarski
package model

enum Status:
  case Available, Occupied
export Status.*

type Name  = String
type Names = Map[Name, Status] // a,b,c,d,e,f

object Name:
  var counter = -1
  def generateFake: Name =
    counter += 1
    s"block$counter"

object Names:
  val TheNames = Set("a", "b", "c", "d", "e", "f")

  def fromNameGrid(ng: NameGrid): Names = TheNames
    .map: name =>
      name -> (if ng.contains(name) then Occupied else Available)
    .toMap

extension (names: Names) // these work whether the name is fake or not.
  def avail(name: Name): Names = names.get(name) match
    case Some(Occupied)  => names.updated(name, Available)
    case Some(Available) => names
    case None            => names // name was fake

  def occupy(name: Name): Names = names.get(name) match
    case Some(Available) => names.updated(name, Occupied)
    case Some(Occupied)  => names
    case None            => names // name was fake
