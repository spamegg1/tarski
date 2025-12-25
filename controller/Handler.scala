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

  import Select.*

  /** We can click on the board only when we are asked to pick an object for a false universal formula, or a true
    * existential formula. In this case, the game's `pos` [[Select]] state must be `Wait` or `On`. Otherwise, clicking
    * on the board has no effect.
    *
    * @param pos
    *   The integer grid positions that the user clicked on.
    * @param game
    *   The current state of the game.
    * @return
    *   New state of the game, updated according to the position clicked.
    */
  def gameBoard(pos: Pos, game: Game): Game =
    game.pos match
      case Off   => game // no effect
      case Wait  => game.setPos(pos)
      case On(p) => if p == pos then game.unsetPos else game.setPos(pos)

  /** Handles what happens when a user clicks somewhere on the game controls.
    *
    * @param pos
    *   The integer grid positions that the user clicked on.
    * @param game
    *   Current state of the game.
    * @return
    *   New state of the game, updated according to which button was clicked on.
    */
  def gameControls(pos: Pos, game: Game): Game =
    Converter.gameMap.get(pos) match
      case None        => game
      case Some(value) => // make sure a button is clicked
        value match
          case "Left" | "Right" => handleChoice(value, game)
          case "Back"           => handleBack(game)
          case "OK"             => handleOK(game)
          case "Commit"         => game
          case "Block"          => game
          case _                => game

  /** We can only click the top /bottom selection buttons if left/right are both `Some(_)` and:
    *
    * the commitment is `Some(false)` and the formula is of the form `And(a, b)`, or
    *
    * the commitment is `Some(true)` and the formula is of the form `Or(a, b)`.
    *
    * Otherwise clicking does nothing.
    *
    * @param choice
    *   `"Left"` or `"Right"`.
    * @param game
    *   Current state of the game.
    * @return
    *   New state of the game depending on choice, commitment and formula.
    */
  private def handleChoice(choice: String, game: Game): Game =
    (game.commitment, game.formula, game.left, game.right, choice) match
      case (Some(false), And(a, b), Some(l), Some(_), "Left") =>
        game.copy(left = None, right = None, formula = l)
      case (Some(false), And(a, b), Some(_), Some(r), "Right") =>
        game.copy(left = None, right = None, formula = r)
      case (Some(true), Or(a, b), Some(l), Some(_), "Left") =>
        game.copy(left = None, right = None, formula = l)
      case (Some(true), Or(a, b), Some(_), Some(r), "Right") =>
        game.copy(left = None, right = None, formula = r)
      case _ => game

  /** Moves the game back in time by one step.
    *
    * @param game
    *   Current state of the game.
    * @return
    *   The previous state of the game, if available, else this game.
    */
  private def handleBack(game: Game): Game = ???

  /** We can only click the OK button if:
    *
    * a block has been selected but not yet confirmed (false forall, or true exists formula), or
    *
    * a message has been displayed and we need to move on to the next step.
    *
    * @param game
    *   Current state of the game.
    * @return
    *   New state of the game, depending on the commitment and formula.
    */
  private def handleOK(game: Game): Game = game match
    case Game(_, All(x, f), On(pos), Some(false), _, _) => game.subst(pos, x, f)
    case Game(_, Ex(x, f), On(pos), Some(true), _, _)   => game.subst(pos, x, f)
    case Game(_, Neg(f), _, Some(commit), _, _)         => game.negate(commit, f)
    case _                                              => game

  def next(g: Game): Game =
    given nm: NameMap = g.board.grid.toNameMap
    (g.commitment, g.formula) match
      case (Some(commit), a: FOLAtom) =>
        val result  = Interpreter.eval(a)
        val winLose = if commit == result then "You win!" else "You lose."
        val msg     = s"$winLose $a is $result in this world."
        g

      case (Some(true), And(a, b)) =>
        val evalA  = Interpreter.eval(a)
        val choice = if evalA then b else a
        val msg1   = s"You believe both $a and $b are true."
        val msg2   = s"I choose $choice as false."
        g.copy(formula = choice)

      case (Some(false), And(a, b)) =>
        g.pos match
          case Off =>
            val msg1 = s"You believe one of $a or $b is ${g.commitment}."
            val msg2 = s"Choose a ${g.commitment} formula above."
            g.copy(left = Some(a), right = Some(b), pos = Wait)
          case Wait  => g // Wait => Off transition (selecting L/R) handled elsewhere
          case On(_) => g

      case (Some(true), Or(a, b)) =>
        g.pos match
          case Off =>
            val msg1 = s"You believe one of $a or $b is ${g.commitment}."
            val msg2 = s"Choose a ${g.commitment} formula above."
            g.copy(left = Some(a), right = Some(b), pos = Wait)
          case Wait  => g // Wait => Off transition (selecting L/R) handled elsewhere
          case On(_) => g

      case (Some(false), Or(a, b)) =>
        val evalA  = Interpreter.eval(a)
        val choice = if evalA then a else b
        val msg1   = s"You believe both $a and $b are false."
        val msg2   = s"I choose $choice as true."
        g.copy(formula = choice)

      case (Some(commit), Neg(a)) => g.copy(commitment = Some(!commit), formula = a)

      case (_, Imp(a, b)) =>
        val f   = Or(Neg(a), b)
        val msg = s"${g.formula} can be written as $f"
        g.copy(formula = f)

      case (_, Iff(a: FOLFormula, b: FOLFormula)) =>
        val f   = And(Imp(a, b), Imp(b, a))
        val msg = s"${g.formula} can be written as $f"
        g.copy(formula = f)

      case (Some(true), All(x, f)) =>
        val msg1   = s"You believe ${g.formula} is true."
        val msg2   = s"You believe every object [${x.name}] satisfies $f"
        val choice = nm.keys
          .map(name => name -> Interpreter.eval(f.sub(x, name)))
          .find(!_._2) match
          case None            => nm.keys.head
          case Some((name, _)) => name
        val msg3 = s"I choose $choice as my counterexample"
        g.copy(formula = f.sub(x, choice))

      case (Some(false), All(x, f)) =>
        g.pos match
          case Off =>
            val msg1 = s"You believe some object [${x.name}] falsifies $f"
            val msg2 = s"Click on a block, then click OK"
            g.copy(pos = Wait)
          case Wait     => g // Wait => On transition handled elsewhere
          case On(name) => g.copy(formula = f.sub(x, ???), pos = Off)

      case (Some(true), Ex(x, f)) =>
        g.pos match
          case Off =>
            val msg1 = s"You believe some object [${x.name}] satisfies $f"
            val msg2 = s"Click on a block, then click OK"
            g.copy(pos = Wait)
          case Wait     => g // Wait => On transition handled elsewhere
          case On(name) => g.copy(formula = f.sub(x, ???), pos = Off)

      case (Some(false), Ex(x, f)) =>
        val msg1   = s"You believe ${g.formula} is false."
        val msg2   = s"You believe no object [${x.name}] satisfies $f"
        val choice = nm.keys
          .map(name => name -> Interpreter.eval(f.sub(x, name)))
          .find(_._2) match
          case None            => nm.keys.head
          case Some((name, _)) => name
        val msg3 = s"I choose $choice as an instance that satisfies it"
        g.copy(formula = f.sub(x, choice))

      case _ => g
  end next
end Handler
