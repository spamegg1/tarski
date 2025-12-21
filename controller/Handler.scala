package tarski
package controller

/** Handles user interface controls, for the chess board and for the user interface control buttons.
  */
object Handler:
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
      case Some(value) => // make sure a button is clicked
        value match
          case "Eval"                            => handleEval(world)
          case "Add"                             => world.addBlockFromControls
          case "Del"                             => world.removeSelectedBlock
          case "Move"                            => world.toggleMove
          case "Block"                           => world
          case "a" | "b" | "c" | "d" | "e" | "f" => handleName(value, world)
          case "Blu" | "Lim" | "Red"             => handleAttr(value, world)
          case "Sml" | "Mid" | "Big"             => handleAttr(value, world)
          case "Tri" | "Sqr" | "Cir"             => handleAttr(value, world)
          case "Left" | "Rgt"                    => handleRotate(value, world)
          case _                                 => world
  end uiButtons

  /** Handles the evaluation of formulas if the user clicked on the Eval button.
    *
    * @param world
    *   The current state of the world.
    * @return
    *   New state of the world, updated by evaluating the formulas. If a formula refers to a named object that's not
    *   present on the board, it is left unevaluated. If a formula uses an unsupported predicate symbol, it errors.
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
    *   The name of the button that was clicked (a, b, c, d, e or f).
    * @param world
    *   The current state of the world.
    * @return
    *   New state of the world, updated according to which name was clicked.
    */
  private def handleName(name: String, world: World): World =
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
  private def handleRotate(dir: String, world: World): World =
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
  private def handleAttr(attr: String, world: World): World =
    val newAttr                = attr.toAttr
    val newControls            = world.controls.setAttr(newAttr)
    val (newGrid, newFormulas) = world.controls.posOpt match
      case None      => (world.board.grid, world.formulas)
      case Some(pos) =>
        world.board.grid.get(pos) match
          case None                => (world.board.grid, world.formulas)
          case Some((block, name)) =>
            val newBlock = block.setAttr(newAttr)
            (world.board.grid.updated(pos, (newBlock, name)), world.formulas.reset)
    world.copy(controls = newControls, board = world.board(newGrid), formulas = newFormulas)

  extension (s: String)
    /** Extension method to convert a `String` to a [[model.Attr]].
      *
      * @return
      *   The attribute that corresponds to the `String`.
      */
    def toAttr: Attr = s match
      case "Blu" => Tone.Blu
      case "Red" => Tone.Red
      case "Lim" => Tone.Lim
      case "Sml" => Sizes.Sml
      case "Mid" => Sizes.Mid
      case "Big" => Sizes.Big
      case "Tri" => Shape.Tri
      case "Sqr" => Shape.Sqr
      case "Cir" => Shape.Cir

  def gameBoard(pos: Pos, game: Game): Game    = game
  def gameControls(pos: Pos, game: Game): Game = game

  def next(g: Game)(using nm: NameMap): Game = (g.commitment, g.formula) match
    case (commit, a: FOLAtom) =>
      val result  = Interpreter.eval(a)
      val winLose = if commit == result then "You win!" else "You lose."
      val msg     = s"$winLose $a is $result in this world."
      g.copy(messages = msg :: g.messages)

    case (true, And(a, b)) =>
      val evalA  = Interpreter.eval(a)
      val choice = if evalA then b else a
      val msg1   = s"You believe both $a and $b are true."
      val msg2   = s"I choose $choice as false."
      g.copy(formula = choice, messages = msg2 :: msg1 :: g.messages)

    case (false, And(a, b)) =>
      g.choice match
        case Choice.Off =>
          val msg1 = s"You believe one of $a or $b is ${g.commitment}."
          val msg2 = s"Choose a ${g.commitment} formula above."
          g.copy(left = Some(a), right = Some(b), messages = msg2 :: msg1 :: g.messages, choice = Choice.Wait)
        case Choice.Wait => g // Wait => Off transition (selecting L/R) handled elsewhere

    case (true, Or(a, b)) =>
      g.choice match
        case Choice.Off =>
          val msg1 = s"You believe one of $a or $b is ${g.commitment}."
          val msg2 = s"Choose a ${g.commitment} formula above."
          g.copy(left = Some(a), right = Some(b), messages = msg2 :: msg1 :: g.messages, choice = Choice.Wait)
        case Choice.Wait => g // Wait => Off transition (selecting L/R) handled elsewhere

    case (false, Or(a, b)) =>
      val evalA  = Interpreter.eval(a)
      val choice = if evalA then a else b
      val msg1   = s"You believe both $a and $b are false."
      val msg2   = s"I choose $choice as true."
      g.copy(formula = choice, messages = msg2 :: msg1 :: g.messages)

    case (commit, Neg(a)) => g.copy(commitment = !commit, formula = a)

    case (_, Imp(a, b)) =>
      val f   = Or(Neg(a), b)
      val msg = s"${g.formula} can be written as $f"
      g.copy(formula = f, messages = msg :: g.messages)

    case (_, Iff(a: FOLFormula, b: FOLFormula)) =>
      val f   = And(Imp(a, b), Imp(b, a))
      val msg = s"${g.formula} can be written as $f"
      g.copy(formula = f, messages = msg :: g.messages)

    case (true, All(x, f)) =>
      val msg1   = s"You believe ${g.formula} is true."
      val msg2   = s"You believe every object [${x.name}] satisfies $f"
      val choice = nm.keys
        .map(name => name -> Interpreter.eval(f.sub(x, name)))
        .find(!_._2) match
        case None            => nm.keys.head
        case Some((name, _)) => name
      val msg3 = s"I choose $choice as my counterexample"
      g.copy(formula = f.sub(x, choice), messages = msg3 :: msg2 :: msg1 :: g.messages)

    case (false, All(x, f)) =>
      g.selected match
        case Select.Off =>
          val msg1 = s"You believe some object [${x.name}] falsifies $f"
          val msg2 = s"Click on a block, then click OK"
          g.copy(messages = msg2 :: msg1 :: g.messages, selected = Select.Wait)
        case Select.Wait     => g // Wait => On transition handled elsewhere
        case Select.On(name) => g.copy(formula = f.sub(x, name), selected = Select.Off)

    case (true, Ex(x, f)) =>
      g.selected match
        case Select.Off =>
          val msg1 = s"You believe some object [${x.name}] satisfies $f"
          val msg2 = s"Click on a block, then click OK"
          g.copy(messages = msg2 :: msg1 :: g.messages, selected = Select.Wait)
        case Select.Wait     => g // Wait => On transition handled elsewhere
        case Select.On(name) => g.copy(formula = f.sub(x, name), selected = Select.Off)

    case (false, Ex(x, f)) =>
      val msg1   = s"You believe ${g.formula} is false."
      val msg2   = s"You believe no object [${x.name}] satisfies $f"
      val choice = nm.keys
        .map(name => name -> Interpreter.eval(f.sub(x, name)))
        .find(_._2) match
        case None            => nm.keys.head
        case Some((name, _)) => name
      val msg3 = s"I choose $choice as an instance that satisfies it"
      g.copy(formula = f.sub(x, choice), messages = msg3 :: msg2 :: msg1 :: g.messages)

    case _ => g
  end next
end Handler
