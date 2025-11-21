package tarski
package model

case class World(
    posGrid: PosGrid = Map(),
    names: Names = World.initNames,
    formulas: Formulas = Map(),
    controls: Controls = Controls()
):
  def resetFormulas             = copy(formulas = formulas.map((f, _) => f -> Ready))
  def addFormula(f: FOLFormula) = copy(formulas = formulas + (f -> Ready))
  def selectPos(pos: Pos)       = copy(controls = controls.selectPos(pos))
  def deselectPos               = copy(controls = controls.deselectPos)
  def toggleMove                = copy(controls = controls.toggleMove)
  def unsetBlock                = copy(controls = controls.unsetBlock)
  def nameGrid                  = posGrid.toNameGrid

  // newly added blocks are always nameless, the name can only be added later.
  def addBlockAt(pos: Pos, block: Block) = posGrid.get(pos) match
    case Some(_) => this
    case None => // make sure there is no block at position
      val fakeName = Name.generateFake
      val newGrid  = posGrid.updated(pos, (block, fakeName))
      resetFormulas.copy(posGrid = newGrid)

  def addBlockFromControls: World =
    Block.fromControls(controls) match
      case None => this
      case Some(block) => // make sure a block can be created
        controls.posOpt match
          case None      => this
          case Some(pos) => addBlockAt(pos, block)

  def removeBlockAt(pos: Pos) = posGrid.get(pos) match
    case None => this
    case Some((_, name)) => // make sure there is a block at position
      val newGrid  = posGrid.removed(pos)
      val newNames = names.avail(name)
      resetFormulas.copy(posGrid = newGrid, names = newNames)

  def removeSelectedBlock: World = controls.posOpt match
    case None      => this
    case Some(pos) => removeBlockAt(pos)

  def moveBlock(from: Pos, to: Pos): World = posGrid.get(from) match
    case None => copy(controls = controls.selectPos(to))
    case Some((block, name)) => // make sure there is a block at from
      posGrid.get(to) match
        case Some(_) => this
        case None => // make sure there is no block at to
          val newGrid     = posGrid.removed(from).updated(to, (block, name))
          val newControls = controls.toggleMove.selectPos(to)
          resetFormulas.copy(posGrid = newGrid, controls = newControls)

  // this is tricky; since fake names are also involved.
  def addNameToBlockAt(pos: Pos, name: Name): World = posGrid.get(pos) match
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
              val newGrid  = posGrid.updated(pos, (newBlock, name))
              val newNames = names.occupy(name)
              resetFormulas.copy(posGrid = newGrid, names = newNames)

  def removeNameFromBlockAt(pos: Pos): World = posGrid.get(pos) match
    case None => this
    case Some((block, name)) => // make sure there is a block at position
      names.get(name) match
        case None            => this
        case Some(Available) => this
        case Some(Occupied) => // make sure name is already occupied
          val newBlock = block.removeLabel
          val newName  = Name.generateFake
          val newGrid  = posGrid.updated(pos, (newBlock, newName))
          val newNames = names.avail(name)
          resetFormulas.copy(posGrid = newGrid, names = newNames)

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

  def empty = World()

  def from(grid: Grid, formulas: Seq[FOLFormula]) =
    val pg = PosGrid.fromGrid(grid)
    World(
      posGrid = pg,
      names = Names.fromNameGrid(pg.toNameGrid),
      formulas = Formulas.fromSeq(formulas)
    )
