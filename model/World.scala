package tarski

type Grid   = Map[Pos, (block: Block, name: Name)]
type Blocks = Map[Name, (block: Block, pos: Pos)]

case class World(grid: Grid, blocks: Blocks):
  def addBlockAt(block: Block, pos: Pos) = grid.get(pos) match
    case Some(_) => this
    case None =>
      val name      = if block.name.isEmpty then Name.generate else block.name
      val newGrid   = grid.updated(pos, (block, name))
      val newBlocks = blocks.updated(name, (block, pos))
      World(newGrid, newBlocks)

  def removeBlockAt(pos: Pos) = grid.get(pos) match
    case None => this
    case Some((_, name)) =>
      val newGrid   = grid.removed(pos)
      val newBlocks = blocks.removed(name)
      World(newGrid, newBlocks)

  def moveBlock(from: Pos, to: Pos): World = ???

  def addNameToBlockAt(pos: Pos): World = ???

  def removeNameFromBlockAt(pos: Pos, name: String): World = ???
