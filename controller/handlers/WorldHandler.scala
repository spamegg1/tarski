package tarski
package controller

/** Handles user interface controls, for the chess board and for the user interface control buttons.
  */
object WorldHandler:
  /** Handles what happens when a user clicks somewhere on the board.
    *
    * @param pos
    *   The integer grid positions that the user clicked on.
    * @param world
    *   Current state of the world.
    * @return
    *   New state of the world, updated according to which square was clicked on.
    */
  def boardPos(pos: Pos, world: World): World =
    world.controls.posOpt match
      case Some(p) if p == pos =>
        val newControls = world.controls.deselectPos.unsetBlock
        world.copy(controls = newControls)
      case Some(p) if world.controls.move => world.moveBlock(from = p, to = pos)
      case _                              =>
        val newControls = world.controls.selectPos(pos).setBlock(world.board.grid.get(pos))
        world.copy(controls = newControls)

  /** Handles what happens when a user clicks somewhere on the user interface controls.
    *
    * @param pos
    *   The integer grid positions that the user clicked on.
    * @param world
    *   Current state of the world.
    * @return
    *   New state of the world, updated according to which button was clicked on.
    */
  def uiButtons(pos: Pos, world: World): World =
    Converter.uiMap.get(pos) match
      case None        => world
      case Some(click) => // make sure a button is clicked
        click match
          case l: Letter   => handleName(l.toName, world)
          case attr: Attr  => handleAttr(attr, world)
          case r: Rotation => handleRotate(r, world)
          case a: Action   => handleAction(a, world)

  /** Handles what happens when a user clicks one of the action buttons which control evaluation, adding / moving /
    * deleting a block, or the selected block display.
    *
    * @param action
    *   One of `Eval`, `Add`, `Del`, `Move`, `Icon`.
    * @param world
    *   The current state of the world.
    * @return
    *   New state of the world, updated according to which action button was clicked on.
    */
  private def handleAction(action: Action, world: World): World =
    import Action.*
    action match
      case Eval => handleEval(world)
      case Add  => world.addBlockFromControls
      case Del  => world.removeSelectedBlock
      case Move => world.toggleMove
      case Icon => world

  /** Handles the evaluation of formulas if the user clicked on the Eval button.
    *
    * If a formula refers to a named object that's not present on the board, it is left unevaluated. If a formula uses
    * an unsupported predicate symbol, it errors.
    *
    * @param world
    *   The current state of the world.
    * @return
    *   New state of the world, updated by evaluating the formulas.
    */
  private def handleEval(world: World): World =
    import Result.*
    val results = world.formulas.map: (formula, result) =>
      var status = Ready
      try
        val bool = Interpreter.eval(formula)(using world.nameMap)
        status = if bool then Valid else Invalid
      catch
        case ex: java.util.NoSuchElementException   => status = Ready
        case ex: java.lang.IllegalArgumentException => status = Error
      formula -> status
    world.copy(formulas = results)

  /** Handles the named objects if the user clicked on one of the name buttons.
    *
    * @param name
    *   The letter name of the button that was clicked (a, b, c, d, e or f).
    * @param world
    *   The current state of the world.
    * @return
    *   New state of the world, updated according to which name was clicked.
    */
  private def handleName(name: Name, world: World): World =
    import Status.*
    world.names.get(name) match
      case None            => world
      case Some(Available) =>
        world.controls.posOpt match
          case None      => world
          case Some(pos) => world.addNameToBlockAt(pos, name)
      case Some(Occupied) =>
        world.nameMap.get(name) match
          case None           => world
          case Some((_, pos)) => world.removeNameFromBlockAt(pos)

  /** Handles the rotation of the board if the user clicked on one of the rotation buttons.
    *
    * @param dir
    *   "Left" (counter-clockwise) or "Right" (clockwise).
    * @param world
    *   the current state of the world.
    * @return
    *   New state of the world, where positions of the blocks are rotated 90 degrees in given direction.
    */
  private def handleRotate(dir: Rotation, world: World): World =
    world.rotate(Rotator.board.rotate(dir))

  /** Handles attributes ([[model.Tone]], [[model.Shape]] or [[model.Sizes]]) if the user clicks on one of those
    * buttons. If there is a block at selected position, the block's attributes are changed, and formulas are reset.
    *
    * @param attr
    *   The attribute that was clicked.
    * @param world
    *   The current state of the world.
    * @return
    *   New state of the world, updated according to which attribute was clicked.
    */
  private def handleAttr(attr: Attr, world: World): World =
    val newControls            = world.controls.setAttr(attr)
    val (newGrid, newFormulas) = world.controls.posOpt match
      case None      => (world.board.grid, world.formulas)
      case Some(pos) =>
        world.board.grid.get(pos) match
          case None                => (world.board.grid, world.formulas)
          case Some((block, name)) =>
            val newBlock = block.setAttr(attr)
            (world.board.grid.updated(pos, (newBlock, name)), world.formulas.reset)
    world.copy(controls = newControls, board = world.board(newGrid), formulas = newFormulas)
end WorldHandler
