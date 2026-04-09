package tarski
package model

/** Contains values and methods used for [[Names]]. */
object Names:
  import Status.*

  /** Type alias for a map of all the names and their availability. */
  type Names = Map[Name, Status] // a,b,c,d,e,f

  /** The only allowed names. */
  val TheNames = Set("a", "b", "c", "d", "e", "f")

  /** Creates a [[Names]] instance from a given name grid by checking which names are occupied or not. Useful for
    * initializing a [[World]] instance at the beginning of a world.
    *
    * @param panel
    *   A map of name -> (block, pos) pairs.
    * @return
    *   A map of names and their availability statuses.
    */
  def fromPanel(panel: Panel): Names = TheNames
    .map: name =>
      name -> (if panel.contains(name) then Occupied else Available)
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
  end extension
end Names
