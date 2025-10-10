package tarski
package model

case class World(
    grid: Grid = Map(),
    blocks: Blocks = Map(),
    names: Names = World.initNames,
    formulas: Formulas = Map(),
    controls: Controls = Controls(),
    selectedPos: Option[Pos] = None
):
  // newly added blocks are always nameless, the name can only be added later.
  def addBlockAt(pos: Pos)(block: Block) = grid.get(pos) match
    case Some(_) => this
    case None => // make sure there is no block at position
      val fakeName  = Name.generateFake
      val newGrid   = grid.updated(pos, (block, fakeName))
      val newBlocks = blocks.updated(fakeName, (block, pos))
      copy(grid = newGrid, blocks = newBlocks)

  def removeBlockAt(pos: Pos) = grid.get(pos) match
    case None => this
    case Some((_, name)) => // make sure there is a block at position
      val newGrid   = grid.removed(pos)
      val newBlocks = blocks.removed(name)
      val newNames  = names.avail(name)
      copy(grid = newGrid, blocks = newBlocks, names = newNames)

  def moveBlock(from: Pos, to: Pos): World = grid.get(from) match
    case None => this
    case Some((block, name)) => // make sure there is a block at from position
      grid.get(to) match
        case Some(_) => this
        case None => // make sure there is no block at to position
          val newGrid   = grid.removed(from).updated(to, (block, name))
          val newBlocks = blocks.updated(name, (block, to))
          copy(grid = newGrid, blocks = newBlocks, controls = controls.disableMove)

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
              copy(grid = newGrid, blocks = newBlocks, names = newNames)

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
          copy(grid = newGrid, blocks = newBlocks, names = newNames)

  def updateFormulas(newFormulas: Formulas) = copy(formulas = newFormulas)
  def resetFormulas = copy(formulas = formulas.map((f, _) => f -> Ready))
  def addFormula(formula: FOLFormula) = copy(formulas = formulas + (formula -> Ready))
  def selectPos(pos: Pos)             = copy(selectedPos = Some(pos))
  def deselectPos                     = copy(selectedPos = None)

object World:
  // only 6 names are allowed: a,b,c,d,e,f
  val initNames = Map(
    "a" -> Available,
    "b" -> Available,
    "c" -> Available,
    "d" -> Available,
    "e" -> Available,
    "f" -> Available
  )

  def empty: World = World()

  def fromGrid(grid: Grid): World =
    val blocks = grid.map:
      case (pos, (block, name)) =>
        name -> (block, pos)
    World(grid = grid, blocks = blocks)

  def fromBlocks(blocks: Blocks): World =
    val grid = blocks.map:
      case (name, (block, pos)) =>
        pos -> (block, name)
    World(grid = grid, blocks = blocks)
