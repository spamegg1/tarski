package tarski
package model

/** This is like the inverse of [[NameGrid]], allowing us to look-up blocks by name instead of position. Needed for
  * [[controller.Interpreter.eval]].
  */
type NameMap = Map[Name, (block: Block, pos: Pos)]
