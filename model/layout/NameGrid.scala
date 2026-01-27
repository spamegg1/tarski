package tarski
package model

/** The core grid type used by [[Block]] and subsequently [[World]]. */
type NameGrid = Map[Pos, (block: Block, name: Name)]

extension (ng: NameGrid)
  /** Extension method that converts a [[NameGrid]] to a [[NameMap]] by inverting the names and positions.
    *
    * @return
    *   The name grid inverted as position -> (block, name).
    */
  def toNameMap: NameMap = ng.map:
    case (pos, (block, name)) => name -> (block, pos)
