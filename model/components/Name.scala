package tarski
package model

/** Type alias for the name of an object. */
type Name = String

/** Contains variables and methods internal to [[Name]] used for bookkeeping purposes. */
object Name:
  /** An internal counter used to generate distinct names for every block. */
  private var counter = -1

  /** Generates a "fake" name for a block that does not have one of the labels `"a", "b", "c", "d", "e", "f"`. This is
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
end Name
