package tarski
package controller

object GameHandler:
  import Select.*

  /** We can click on the board only when we are asked to pick an object for a false universal formula or a true
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
  def boardPos(pos: Pos, game: Game): Game =
    game.pos match
      case Off   => game // no effect
      case Wait  => game.setPos(pos)
      case On(p) => if p == pos then game.waitPos else game.setPos(pos)

  /** Handles what happens when a user clicks somewhere on the game controls.
    *
    * @param pos
    *   The integer grid positions that the user clicked on.
    * @param game
    *   Current state of the game.
    * @return
    *   New state of the game, updated according to which button was clicked on.
    */
  def controls(pos: Pos, game: Game): Game =
    Converter.gameMap.get(pos) match
      case None        => game
      case Some(value) => // make sure a button is clicked
        value match
          case "True" | "False" => handleStart(value, game)
          case "Left" | "Right" => handleChoice(value, game)
          case "Back"           => handleBack(game)
          case "OK"             => handleOK(game)
          case "Block"          => game
          case _                => game

  /** Handles what happens when the user clicks on the True/False buttons.
    *
    * We can only click the True/False buttons if `commitment` is set to `None`, i.e. we are at the very beginning of
    * the game.
    *
    * @param choice
    *   Either `"True"` or `"False"`.
    * @param game
    *   Current state of the game.
    * @return
    *   New state of the game that sets the user's commitment and starts the game.
    */
  def handleStart(choice: String, game: Game): Game = game.step.play.commitment match
    case Some(_) => game // commitment is already set, we cannot click
    case None    =>
      choice match
        case "True" =>
          val nextPlay = game.step.play.commitTo(true)
          val nextMsgs = generateMessages(nextPlay, game.pos)(using game.board)
          game.addStep(nextPlay, nextMsgs)
        case "False" =>
          val nextPlay = game.step.play.commitTo(false)
          val nextMsgs = generateMessages(nextPlay, game.pos)(using game.board)
          game.addStep(nextPlay, nextMsgs)
        case _ => game

  /** Handles what happens when the user clicks on one of the two choice buttons.
    *
    * We can only click the choice buttons if:
    *
    * left/right are both `Some(_)`, and:
    *
    * the commitment is None, or
    *
    * the commitment is `Some(false)` and the formula is of the form `And(a, b)`, or
    *
    * the commitment is `Some(true)` and the formula is of the form `Or(a, b)`.
    *
    * Otherwise clicking the choice buttons does nothing.
    *
    * @param choice
    *   `"Left"` or `"Right"`.
    * @param game
    *   Current state of the game.
    * @return
    *   New state of the game depending on choice, commitment and formula.
    */
  private def handleChoice(choice: String, game: Game): Game =
    val play     = game.step.play
    val nextPlay = (play, choice) match
      case (Play(And(a, b), Some(false), Some(l), Some(_)), "Left")  => play.setFormula(l)
      case (Play(And(a, b), Some(false), Some(_), Some(r)), "Right") => play.setFormula(r)
      case (Play(Or(a, b), Some(true), Some(l), Some(_)), "Left")    => play.setFormula(l)
      case (Play(Or(a, b), Some(true), Some(_), Some(r)), "Right")   => play.setFormula(r)
      case _                                                         => play
    val nextMsgs = generateMessages(nextPlay, game.pos)(using game.board)
    game.addStep(nextPlay, nextMsgs)

  /** Moves the game back in time by one step.
    *
    * @param game
    *   Current state of the game.
    * @return
    *   The previous state of the game, if available, else this game.
    */
  private def handleBack(game: Game): Game = game.rewind

  /** We can only click the OK button if a block has been selected and can be substituted into a formula, or a message
    * has been displayed and we need to move on to the next step. Otherwise clicking OK does nothing.
    *
    * A block can be substituted only if:
    *
    * commitment is `false`, formula is `All(x, f)`` and pos is `On(_)`, or
    *
    * commitment is `true`, formula is `Ex(x, f)` and pos is `On(_)`.
    *
    * @param game
    *   Current state of the game.
    * @return
    *   New state of the game, depending on the commitment and formula.
    */
  private def handleOK(game: Game): Game = game match
    case Game((Play(All(x, f), Some(false), _, _), _), _, _, On(pos)) => subst(game, x, f, pos)
    case Game((Play(Ex(x, f), Some(true), _, _), _), _, _, On(pos))   => subst(game, x, f, pos)

    case Game((Play(All(x, f), Some(true), _, _), _), _, _, _) =>
      given nm: NameMap = game.board.grid.toNameMap
      val choice        = nm.keys
        .map(name => name -> Interpreter.eval(f.sub(x, name)))
        .find(!_._2) match
        case None            => nm.keys.head
        case Some((name, _)) => name
      val nextPlay = game.step.play.sub(choice, x, f)
      val nextMsgs = generateMessages(nextPlay, Off)(using game.board)
      game.addStep(nextPlay, nextMsgs)

    case Game((Play(Ex(x, f), Some(false), _, _), _), _, _, _) =>
      given nm: NameMap = game.board.grid.toNameMap
      val choice        = nm.keys
        .map(name => name -> Interpreter.eval(f.sub(x, name)))
        .find(!_._2) match
        case None            => nm.keys.head
        case Some((name, _)) => name
      val nextPlay = game.step.play.sub(choice, x, f)
      val nextMsgs = generateMessages(nextPlay, Off)(using game.board)
      game.addStep(nextPlay, nextMsgs)

    case Game((Play(Neg(_), _, _, _), _), _, _, _) =>
      val nextPlay = game.step.play.negate
      val nextMsgs = generateMessages(nextPlay, game.pos)(using game.board)
      game.addStep(nextPlay, nextMsgs)

    case Game((Play(And(a, b), Some(true), _, _), _), _, _, _) =>
      val evalA    = Interpreter.eval(a)(using game.board)
      val choice   = if evalA then b else a
      val nextPlay = game.step.play.setFormula(choice)
      val nextMsgs = generateMessages(nextPlay, game.pos)(using game.board)
      game.addStep(nextPlay, nextMsgs)

    case Game((Play(Or(a, b), Some(false), _, _), _), _, _, _) =>
      val evalA    = Interpreter.eval(a)(using game.board)
      val choice   = if evalA then a else b
      val nextPlay = game.step.play.setFormula(choice)
      val nextMsgs = generateMessages(nextPlay, game.pos)(using game.board)
      game.addStep(nextPlay, nextMsgs)

    case Game((Play(Imp(a, b), _, _, _), _), _, _, _) =>
      val nextPlay = game.step.play.setFormula(Or(Neg(a), b))
      val nextMsgs = generateMessages(nextPlay, game.pos)(using game.board)
      game.addStep(nextPlay, nextMsgs)

    case Game((Play(Iff(a: FOLFormula, b: FOLFormula), _, _, _), _), _, _, _) =>
      val nextPlay = game.step.play.setFormula(And(Imp(a, b), Imp(b, a)))
      val nextMsgs = generateMessages(nextPlay, game.pos)(using game.board)
      game.addStep(nextPlay, nextMsgs)

    case _ => game

  /** Advances the game by substituting the name of the block at selected position into a formula.
    *
    * @param game
    *   Current state of the game.
    * @param x
    *   A first-order variable.
    * @param f
    *   A first-order formula.
    * @param pos
    *   The currently selected position on the board, which holds a block.
    * @return
    *   New state of the game, where the formula has its free occurrences of `x` replaced by the name of the selected
    *   block at `pos`.
    */
  private def subst(game: Game, x: FOLVar, f: FOLFormula, pos: Pos): Game =
    game.board.grid.get(pos) match
      case None                => game
      case Some((block, name)) =>
        val nextPlay = game.step.play.sub(name, x, f)
        val nextMsgs = generateMessages(nextPlay, Off)(using game.board)
        game.addStep(nextPlay, nextMsgs).unsetPos

  /** Generates messages to be displayed to the user about the current state of the game.
    *
    * @param play
    *   The current state of play.
    * @param pos
    *   Current state of the selected position on the board.
    * @param nm
    *   A `NameMap` to look up blocks and to evaluate formulas with the interpreter.
    * @return
    *   A list of messages (strings).
    */
  private def generateMessages(play: Play, pos: Select[Pos])(using nm: NameMap) =
    (play.commitment, play.formula) match
      case (Some(commit), a: FOLAtom) =>
        val result = Interpreter.eval(a)
        val msg1   = if commit == result then "You win!" else "You lose."
        val msg2   = s"$a is $result in this world."
        msg1 :: msg2 :: Nil

      case (Some(true), And(a, b)) =>
        val evalA  = Interpreter.eval(a)
        val choice = if evalA then b else a
        val msg1   = s"You believe both are true:"
        val msg2   = s"$a and $b"
        val msg3   = s"I choose $choice as false."
        msg1 :: msg2 :: msg3 :: Nil

      case (Some(false), And(a, b)) =>
        pos match
          case Off =>
            val msg1 = s"You believe at least one is false:"
            val msg2 = s"$a or $b"
            val msg3 = s"Choose a false formula above."
            msg1 :: msg2 :: msg3 :: Nil
          case Wait  => Nil
          case On(_) => Nil

      case (Some(true), Or(a, b)) =>
        pos match
          case Off =>
            val msg1 = s"You believe one of these is true:"
            val msg2 = s"$a or $b"
            val msg3 = s"Choose a true formula above."
            msg1 :: msg2 :: msg3 :: Nil
          case Wait  => Nil
          case On(_) => Nil

      case (Some(false), Or(a, b)) =>
        val evalA  = Interpreter.eval(a)
        val choice = if evalA then a else b
        val msg1   = s"You believe both are false:"
        val msg2   = s"$a and $b"
        val msg3   = s"I choose $choice as true."
        msg1 :: msg2 :: msg3 :: Nil

      case (Some(commit), Neg(a)) => s"You believe ${play.formula} is $commit." :: Nil

      case (_, Imp(a, b)) =>
        val msg1 = s"${Imp(a, b)} can be written as:"
        val msg2 = s"${Or(Neg(a), b)}"
        msg1 :: msg2 :: Nil

      case (_, Iff(a: FOLFormula, b: FOLFormula)) =>
        val f    = And(Imp(a, b), Imp(b, a))
        val msg1 = s"${play.formula} can be written as:"
        val msg2 = s"$f"
        msg1 :: msg2 :: Nil

      case (Some(true), All(x, f)) =>
        val msg1   = s"You believe every object [${x.name}] satisfies:"
        val msg2   = s"${f.toUntypedString}"
        val choice = nm.keys
          .map(name => name -> Interpreter.eval(f.sub(x, name)))
          .find(!_._2) match
          case None            => nm.keys.head
          case Some((name, _)) => name
        val msg3 = s"I choose $choice as my counterexample"
        msg1 :: msg2 :: msg3 :: Nil

      case (Some(false), All(x, f)) =>
        pos match
          case Off =>
            val msg1 = s"You believe some object [${x.name}] falsifies:"
            val msg2 = s"${f.toUntypedString}"
            val msg3 = s"Click on a block, then click OK"
            msg1 :: msg2 :: msg3 :: Nil
          case Wait     => Nil
          case On(name) => Nil

      case (Some(true), Ex(x, f)) =>
        pos match
          case Off =>
            val msg1 = s"You believe some object [${x.name}] satisfies:"
            val msg2 = s"${f.toUntypedString}"
            val msg3 = s"Click on a block, then click OK"
            msg1 :: msg2 :: msg3 :: Nil
          case Wait     => Nil
          case On(name) => Nil

      case (Some(false), Ex(x, f)) =>
        val msg1   = s"You believe no object [${x.name}] satisfies:"
        val msg2   = s"${f.toUntypedString}"
        val choice = nm.keys
          .map(name => name -> Interpreter.eval(f.sub(x, name)))
          .find(_._2) match
          case None            => nm.keys.head
          case Some((name, _)) => name
        val msg3 = s"I choose $choice as an example"
        msg1 :: msg2 :: msg3 :: Nil

      case _ => Nil
  end generateMessages
end GameHandler
