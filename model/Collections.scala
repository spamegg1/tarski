package tarski
package model

type GridSize = (rows: Int, cols: Int)
type Grid     = Map[Pos, (block: Block, name: Name)]
type Blocks   = Map[Name, (block: Block, pos: Pos)]
type Formulas = Map[FOLFormula, Result]

object Formulas:
  def fromSeq(fs: Seq[FOLFormula]): Formulas = fs.map(f => f -> Ready).toMap
