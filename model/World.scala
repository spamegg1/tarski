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

  def moveBlock(from: Pos, to: Pos): World = grid.get(from) match
    case None => this
    case Some((block, name)) =>
      grid.get(to) match
        case Some(_) => this
        case None =>
          val newGrid   = grid.removed(from).updated(to, (block, name))
          val newBlocks = blocks.updated(name, (block, to))
          World(newGrid, newBlocks)

  def addNameToBlockAt(pos: Pos, name: Name): World = ???

  def removeNameFromBlockAt(pos: Pos): World = grid.get(pos) match
    case None => this
    case Some((block, _)) =>
      val newBlock  = block.removeName
      val newName   = Name.generate
      val newGrid   = grid.updated(pos, (newBlock, newName))
      val newBlocks = blocks.updated(newName, (newBlock, pos))
      World(newGrid, newBlocks)

object World:
  def empty: World = World(Map(), Map())

  def fromGrid(grid: Grid): World =
    val blocks = grid.map:
      case (pos, (block, name)) =>
        name -> (block, pos)
    World(grid, blocks)

  def fromBlocks(blocks: Blocks): World =
    val grid = blocks.map:
      case (name, (block, pos)) =>
        pos -> (block, name)
    World(grid, blocks)
