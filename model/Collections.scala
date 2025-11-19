package tarski
package model

type Attr     = Sizes | Shape | Tone
type Grid     = Map[Pos, Block]
type PosGrid  = Map[Pos, (block: Block, name: Name)]
type NameGrid = Map[Name, (block: Block, pos: Pos)]
type Formulas = Map[FOLFormula, Result]

object Formulas:
  def fromSeq(fs: Seq[FOLFormula]): Formulas = fs.map(f => f -> Ready).toMap

object PosGrid:
  def fromGrid(pb: Grid): PosGrid = pb.map: (pos, block) =>
    val name = if block.label.isEmpty then Name.generateFake else block.label
    pos -> (block, name)

extension (grid: PosGrid)
  def toNameGrid: NameGrid = grid.map:
    case (pos, (block, name)) => name -> (block, pos)
