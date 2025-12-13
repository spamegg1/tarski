package tarski
package model

/** Type alias for the name of an object. */
type Name = String

/** Type alias for a map of all the names and their availability. */
type Names = Map[Name, Status] // a,b,c,d,e,f

/** Contains variables and methods internal to [[Name]] used for bookkeeping purposes. */
object Name:
  /** An internal counter used to generate distinct names for every block. */
  private var counter = -1

  /** Generates a "fake" name for a blocks that does not have one of the labels `"a", "b", "c", "d", "e", "f"`. This is
    * necessary because every first-order constant requires a string argument, like `FOLConst("block0")`. If a block has
    * the empty label `""` then it is assigned a fake name so that it can be used as a constant in atomic formulas
    * during evaluation.
    *
    * @return
    *   A unique name that has not been used before and is not currently used by any block.
    */
  def generateFake: Name =
    counter += 1
    s"b$counter"

/** Contains values and methods used for [[Names]]. */
object Names:
  /** The only allowed names. */
  val TheNames = Set("a", "b", "c", "d", "e", "f")

  /** Creates a [[Names]] instance from a given name grid by checking which names are occupied or not. Useful for
    * initializing a [[World]] instance at the beginning of a world.
    *
    * @param nameMap
    *   A map of name -> (block, pos) pairs.
    * @return
    *   A map of names and their availability statuses.
    */
  def fromNameMap(nameMap: NameMap): Names = TheNames
    .map: name =>
      name -> (if nameMap.contains(name) then Occupied else Available)
    .toMap

extension (names: Names)
  /** Extension method for [[Names]] to toggle the availability of a specific name. It works whether the name argument
    * is "fake" or not.
    *
    * @param name
    *   A name we want to make available.
    * @return
    *   The same names, except `name` has been made available, if applicable.
    */
  def avail(name: Name): Names = names.get(name) match
    case Some(Occupied)  => names.updated(name, Available)
    case Some(Available) => names
    case None            => names // name was fake

  /** Extension method for [[Names]] to toggle the availability of a specific name. It works whether the name argument
    * is "fake" or not.
    *
    * @param name
    *   A name we want to make occupied.
    * @return
    *   The same names, except `name` has been made occupied, if applicable.
    */
  def occupy(name: Name): Names = names.get(name) match
    case Some(Available) => names.updated(name, Occupied)
    case Some(Occupied)  => names
    case None            => names // name was fake
