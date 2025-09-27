package tarski
package model

type GridSize = (rows: Int, cols: Int)
type Grid     = Map[Pos, (block: Block, name: Name)]
type Blocks   = Map[Name, (block: Block, pos: Pos)]
type Formulas = Map[FOLFormula, Result]
