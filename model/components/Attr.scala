package tarski
package model

/** A type alias for the union of all 3 attributes of a [[Block]]: size, shape, tone. This helps to handle updating a
  * block in a generic, uniform way, reducing repeated code and logic.
  */
type Attr = Sizes | Shape | Tone
