package tarski
package model

case class World(
    grid: Grid = Map(),
    names: Names = World.initNames,
    formulas: Formulas = Map(),
    controls: Controls = Controls()
):
  def resetFormulas             = copy(formulas = formulas.map((f, _) => f -> Ready))
  def addFormula(f: FOLFormula) = copy(formulas = formulas + (f -> Ready))
  def selectPos(pos: Pos)       = copy(controls = controls.selectPos(pos))
  def deselectPos               = copy(controls = controls.deselectPos)
  def toggleMove                = copy(controls = controls.toggleMove)

  def blocks: Blocks = grid.map:
    case (pos, (block, name)) => name -> (block, pos)

  // newly added blocks are always nameless, the name can only be added later.
  def addBlockAt(pos: Pos, block: Block) = grid.get(pos) match
    case Some(_) => this
    case None => // make sure there is no block at position
      val fakeName = Name.generateFake
      val newGrid  = grid.updated(pos, (block, fakeName))
      resetFormulas.copy(grid = newGrid)

  def addBlockFromControls: World =
    Block.fromControls(controls) match
      case None => this
      case Some(block) => // make sure a block can be created
        controls.pos match
          case None    => this
          case Some(p) => addBlockAt(p, block)

  def removeBlockAt(pos: Pos) = grid.get(pos) match
    case None => this
    case Some((_, name)) => // make sure there is a block at position
      val newGrid  = grid.removed(pos)
      val newNames = names.avail(name)
      resetFormulas.copy(grid = newGrid, names = newNames)

  def removeSelectedBlock: World = controls.pos match
    case None      => this
    case Some(pos) => removeBlockAt(pos)

  def moveBlock(from: Pos, to: Pos): World = grid.get(from) match
    case None => copy(controls = controls.selectPos(to))
    case Some((block, name)) => // make sure there is a block at from position
      grid.get(to) match
        case Some(_) => this
        case None => // make sure there is no block at to position
          val newGrid     = grid.removed(from).updated(to, (block, name))
          val newControls = controls.toggleMove.selectPos(to)
          resetFormulas.copy(grid = newGrid, controls = newControls)

  // this is tricky; since fake names are also involved.
  def addNameToBlockAt(pos: Pos, name: Name): World = grid.get(pos) match
    case None => this
    case Some((block, oldName)) => // make sure there is a block at position
      names.get(oldName) match
        case Some(_) => this
        case None => // make sure the block does not already have a real name
          names.get(name) match
            case None           => this
            case Some(Occupied) => this
            case Some(Available) => // make sure the name is available
              val newBlock = block.setLabel(name)
              val newGrid  = grid.updated(pos, (newBlock, name))
              val newNames = names.occupy(name)
              resetFormulas.copy(grid = newGrid, names = newNames)

  def removeNameFromBlockAt(pos: Pos): World = grid.get(pos) match
    case None => this
    case Some((block, name)) => // make sure there is a block at position
      names.get(name) match
        case None            => this
        case Some(Available) => this
        case Some(Occupied) => // make sure name is already occupied
          val newBlock = block.removeLabel
          val newName  = Name.generateFake
          val newGrid  = grid.updated(pos, (newBlock, newName))
          val newNames = names.avail(name)
          resetFormulas.copy(grid = newGrid, names = newNames)

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
