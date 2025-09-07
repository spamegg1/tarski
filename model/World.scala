package tarski

type Grid   = Map[Pos, (block: Block, name: Name)]
type Blocks = Map[Name, (block: Block, pos: Pos)]

case class World(grid: Grid, blocks: Blocks, names: Names = World.initNames):
  // newly added blocks are always nameless, the name can only be added later.
  def addBlockAt(pos: Pos)(block: Block) = grid.get(pos) match
    case Some(_) => this
    case None => // make sure there is no block at position
      val fakeName  = Name.generateFake
      val newGrid   = grid.updated(pos, (block, fakeName))
      val newBlocks = blocks.updated(fakeName, (block, pos))
      World(newGrid, newBlocks, names)

  def removeBlockAt(pos: Pos) = grid.get(pos) match
    case None => this
    case Some((_, name)) => // make sure there is a block at position
      val newGrid   = grid.removed(pos)
      val newBlocks = blocks.removed(name)
      val newNames  = names.avail(name)
      World(newGrid, newBlocks, newNames)

  def moveBlock(from: Pos, to: Pos): World = grid.get(from) match
    case None => this
    case Some((block, name)) => // make sure there is a block at from position
      grid.get(to) match
        case Some(_) => this
        case None => // make sure there is no block at to position
          val newGrid   = grid.removed(from).updated(to, (block, name))
          val newBlocks = blocks.updated(name, (block, to))
          World(newGrid, newBlocks, names)

  // this is tricky; since fake names are also involved.
  def addNameToBlockAt(pos: Pos)(name: Name): World = grid.get(pos) match
    case None => this
    case Some((block, oldName)) => // make sure there is a block at position
      names.get(oldName) match
        case Some(_) => this
        case None => // make sure the block does not already have a real name
          names.get(name) match
            case None           => this
            case Some(Occupied) => this
            case Some(Available) => // make sure the name is available
              val newBlock  = block.setLabel(name)
              val newGrid   = grid.updated(pos, (newBlock, name))
              val newBlocks = blocks.removed(oldName).updated(name, (newBlock, pos))
              val newNames  = names.occupy(name)
              World(newGrid, newBlocks, newNames)

  def removeNameFromBlockAt(pos: Pos): World = grid.get(pos) match
    case None => this
    case Some((block, name)) => // make sure there is a block at position
      names.get(name) match
        case None            => this
        case Some(Available) => this
        case Some(Occupied) => // make sure name is already occupied
          val newBlock  = block.removeLabel
          val newName   = Name.generateFake
          val newGrid   = grid.updated(pos, (newBlock, newName))
          val newBlocks = blocks.removed(name).updated(newName, (newBlock, pos))
          val newNames  = names.avail(name)
          World(newGrid, newBlocks, newNames)

object World:
  val initNames = Map(
    "a" -> Available,
    "b" -> Available,
    "c" -> Available,
    "d" -> Available,
    "e" -> Available,
    "f" -> Available
  )

  def empty: World = World(Map(), Map(), initNames)

  def fromGrid(grid: Grid): World =
    val blocks = grid.map:
      case (pos, (block, name)) =>
        name -> (block, pos)
    World(grid, blocks, initNames)

  def fromBlocks(blocks: Blocks): World =
    val grid = blocks.map:
      case (name, (block, pos)) =>
        pos -> (block, name)
    World(grid, blocks, initNames)
