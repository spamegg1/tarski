package tarski
package model

/** The main data type for Tarski's world.
  *
  * @param board
  *   A board of position -> (block, name) pairs. Represents the blocks on the chess board and their names. Also
  *   contains the grid size, i.e. the number of rows and columns of the board.
  * @param names
  *   Tracks which of the 6 names are available and which are assigned to blocks. Initially all names are available
  *   unless assigned.
  * @param formulas
  *   Tracks formulas and their evaluation results in the current world. Initially all formulas are un-evaluated. Also,
  *   evaluated formulas are reset back to un-evaluated whenever the world state changes (e.g. if a block is moved).
  * @param controls
  *   The state of the user interface controls and the selected position on the board.
  */
case class World(
    board: Board = Board.empty,
    names: Names = World.initNames,
    formulas: Formulas = ListMap(),
    controls: Controls = Controls()
):
  /** Resets all formulas back to the [[Result.Ready]] status (i.e. un-evaluated).
    *
    * @return
    *   A copy of this world where all formulas are reset to un-evaluated.
    */
  def resetFormulas = copy(formulas = formulas.reset)

  /** Adds a single, new, un-evaluated formula to this world. Useful when creating an initial world.
    *
    * @param f
    *   A first-order formula.
    * @return
    *   A copy of this world with the given formula added.
    */
  def addFormula(f: FOLFormula) = copy(formulas = formulas.add(f))

  /** Selects the given position, and sets the user interface controls to display the block at the position, if any.
    *
    * @param pos
    *   A position on the board.
    * @return
    *   A copy of this world with controls updated to select `pos` and display the block there.
    */
  def selectPos(pos: Pos) = copy(controls = controls.selectPos(pos).setBlock(board.grid.get(pos)))

  /** De-selects the currently selected position in [[controls]]. Wrapper for [[Controls.deselectPos]].
    *
    * @return
    *   A copy of this world with no position selected.
    */
  def deselectPos = copy(controls = controls.deselectPos)

  /** Toggles the ability to move the selected block. Wrapper for [[Controls.toggleMove]].
    *
    * @return
    *   A copy of this world with no position selected.
    */
  def toggleMove = copy(controls = controls.toggleMove)

  /** Sets the displayed block in the user interface controls to `None`. Wrapper for [[Controls.unsetBlock]].
    *
    * @return
    *   A copy of this world with no block displayed in the controls.
    */
  def unsetBlock = copy(controls = controls.unsetBlock)

  /** [[World]] wrapper for [[toNameMap]].
    *
    * @return
    *   The name map obtained from the position grid of the current world. Useful when evaluating formulas.
    */
  def nameMap = board.grid.toNameMap

  /** Adds given block to the world at the given position, if possible. Newly added blocks are always nameless, the name
    * can only be added later.
    *
    * @param pos
    *   A position on the board.
    * @param block
    *   A block to be added to the world.
    * @return
    *   A copy of this world with the block added at the position if possible, and formula results reset. If the block
    *   is added, it's assigned a fake name initially.
    */
  def addBlockAt(pos: Pos, block: Block) = board.grid.get(pos) match
    case Some(_) => this
    case None    => // make sure there is no block at position
      val fakeName = Name.generateFake
      val newGrid  = board.grid.updated(pos, (block, fakeName))
      resetFormulas.copy(board = board(newGrid))

  /** Adds the block that is displayed in the user interface controls to the position that is currently selected in the
    * controls. Useful when the user clicks on an empty square and then wants to add a specific block by setting its
    * attributes in the controls.
    *
    * @return
    *   A copy of this world, but with the block currently displayed in the controls (if any) added to the currently
    *   selected position (if any), and formula results reset.
    */
  def addBlockFromControls: World =
    Block.fromControls(controls) match
      case None        => this
      case Some(block) => // make sure a block can be created
        controls.posOpt match
          case None      => this
          case Some(pos) => addBlockAt(pos, block)

  /** Removes the block at given position.
    *
    * @param pos
    *   A position on the board.
    * @return
    *   A copy of this world with the block at `pos` (if any) removed, and formula results reset.
    */
  def removeBlockAt(pos: Pos) = board.grid.get(pos) match
    case None            => this
    case Some((_, name)) => // make sure there is a block at position
      val newGrid  = board.grid.removed(pos)
      val newNames = names.avail(name)
      resetFormulas.copy(board = board(newGrid), names = newNames)

  /** Removes the block at currently selected position.
    *
    * @return
    *   A copy of this world with the block at currently selected position (if any) removed, and formula results reset.
    */
  def removeSelectedBlock: World = controls.posOpt match
    case None      => this
    case Some(pos) => removeBlockAt(pos)

  /** Moves a block from one position to another.
    *
    * @param from
    *   The position from which we want to move a block.
    * @param to
    *   The position to which we'd like to move the block at `from`.
    * @return
    *   A copy of this world with the block at `from` (if any) moved to `to` (if empty), and formula results reset. If
    *   the move is successful, Move will also be disabled.
    */
  def moveBlock(from: Pos, to: Pos): World = board.grid.get(from) match
    case None                => selectPos(to)
    case Some((block, name)) => // make sure there is a block at from
      board.grid.get(to) match
        case Some(_) => selectPos(to)
        case None    => // make sure there is no block at to
          val newGrid = board.grid.removed(from).updated(to, (block, name))
          resetFormulas.selectPos(to).toggleMove.copy(board = board(newGrid))

  /** Adds given name to the block at given position.
    *
    * @param pos
    *   A position on the board.
    * @param name
    *   The name we'd like to assign to the block at `pos`.
    * @return
    *   A copy of this world with the block at `pos` (if any) assigned the name `name` (if available), and formula
    *   results reset.
    */
  def addNameToBlockAt(pos: Pos, name: Name): World = board.grid.get(pos) match
    case None                   => this
    case Some((block, oldName)) => // make sure there is a block at position
      names.get(oldName) match
        case Some(_) => this
        case None    => // make sure the block does not already have a real name
          names.get(name) match
            case None            => this
            case Some(Occupied)  => this
            case Some(Available) => // make sure the name is available
              val newBlock = block.setLabel(name)
              val newGrid  = board.grid.updated(pos, (newBlock, name))
              val newNames = names.occupy(name)
              resetFormulas.copy(board = board(newGrid), names = newNames)

  /** Removes the name from the block at given position.
    *
    * @param pos
    *   A position on the board.
    * @return
    *   A copy of this world with the name (if any) of the block (if any) at `pos` removed and made available, and
    *   formula results reset. If the name removal is successful, the now-nameless block will receive a generated fake
    *   name.
    */
  def removeNameFromBlockAt(pos: Pos): World = board.grid.get(pos) match
    case None                => this
    case Some((block, name)) => // make sure there is a block at position
      names.get(name) match
        case None            => this
        case Some(Available) => this
        case Some(Occupied)  => // make sure name is already occupied
          val newBlock = block.removeLabel
          val newName  = Name.generateFake
          val newGrid  = board.grid.updated(pos, (newBlock, newName))
          val newNames = names.avail(name)
          resetFormulas.copy(board = board(newGrid), names = newNames)

  /** Rotates the board positions according to given rotator. Normally this is 90 degrees clockwise or
    * counter-clockwise, but it supports any function that maps positions to positions.
    *
    * @param rotator
    *   A function [[Pos]] => [[Pos]]. This normally comes from [[controller.Converter.board]].
    * @return
    *   New world where block positions and the selected position are rotated according to `rotator`.
    */
  def rotate(rotator: Pos => Pos): World =
    val newGrid     = board.grid.map((pos, value) => (rotator(pos), value))
    val newGridSize = (rows = board.gs.cols, cols = board.gs.rows)
    val newControls = controls.posOpt.match
      case None      => controls
      case Some(pos) => controls.selectPos(rotator(pos))
    resetFormulas.copy(board = Board(newGrid, newGridSize), controls = newControls)
end World

/** Contains values and helper methods for [[World]]. */
object World:
  /** The only allowed names: a,b,c,d,e,f; and their initial availability. */
  val initNames = Map(
    "a" -> Available,
    "b" -> Available,
    "c" -> Available,
    "d" -> Available,
    "e" -> Available,
    "f" -> Available
  )

  /** Creates an empty world. Useful for building examples step-by-step.
    *
    * @return
    *   An empty world with no blocks, names, or any attributes selected in the controls.
    */
  def empty = World()

  /** Creates an initial world from a given grid and formulas. Used in [[main.runWorld]].
    *
    * @param grid
    *   A map of position -> block pairs.
    * @param formulas
    *   A sequence of first-order formulas.
    * @return
    *   A world with given blocks at given positions, with all names initially available and all given formulas are
    *   un-evaluated.
    */
  def from(grid: Grid, formulas: Seq[FOLFormula]) =
    val board = Board.fromGrid(grid)
    World(
      board = board,
      names = Names.fromNameMap(board.grid.toNameMap),
      formulas = Formulas.fromSeq(formulas)
    )
end World
