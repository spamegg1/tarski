package tarski
package model

type PosBlock = Map[Pos, Block]
type Grid     = Map[Pos, (block: Block, name: Name)]
type Blocks   = Map[Name, (block: Block, pos: Pos)]
type Formulas = Map[FOLFormula, Result]

object Formulas:
  def fromSeq(fs: Seq[FOLFormula]): Formulas = fs.map(f => f -> Ready).toMap

object Grid:
  def fromPosBlock(pb: PosBlock): Grid = pb.map: (pos, block) =>
    val name = if block.label.isEmpty then Name.generateFake else block.label
    pos -> (block, name)

extension (grid: Grid)
  def toBlocks: Blocks = grid.map:
    case (pos, (block, name)) => name -> (block, pos)
