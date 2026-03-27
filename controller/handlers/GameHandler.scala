package tarski
package controller

object GameHandler:
  import Select.*

  /** We can click on the board only when we are asked to pick an object for a false universal formula or a true
    * existential formula. In this case, the game's `pos` [[Select]] state must be `Wait` or `On`. Otherwise, clicking
    * on the board has no effect. Does not advance the step.
    *
    * @param pos
    *   The integer grid positions that the user clicked on.
    * @param game
    *   The current state of the game.
    * @return
    *   New state of the game, updated according to the position clicked.
    */
  def boardPos(pos: Pos, game: Game): Game =
    game.step.pos match
      case Off   => game                                                // no effect
      case Wait  => game.setPos(pos)                                    // set to `On(_)`
      case On(p) => if p == pos then game.waitPos else game.setPos(pos) // de-select or On

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
      case Some(click) => // make sure a button is clicked
        click match
          case commit: Commit     => handleCommit(commit, game)
          case choice: Choice     => handleChoice(choice, game)
          case action: GameAction => handleAction(action, game)

  def handleAction(action: GameAction, game: Game): Game = action match
    case GameAction.Back    => game.rewind
    case GameAction.OK      => handleOK(game)
    case GameAction.Display => game

  /** Handles what happens when the user clicks on the True/False buttons.
    *
    * We can only click the True/False buttons if `commitment` is set to `None`, i.e. we are at the very beginning of
    * the game.
    *
    * @param commit
    *   Either `True` or `False`.
    * @param game
    *   Current state of the game.
    * @return
    *   New state of the game that sets the user's commitment and advances the step.
    */
  def handleCommit(commit: Commit, game: Game): Game = game.step.play.commitment match
    case Some(_) => game // commitment is already set, we cannot click
    case None    =>
      val nextPlay = game.step.play.commitTo(commit.toBoolean)
      val nextMsgs = Messager.show(nextPlay)(using game.board)
      game.addStep(nextPlay, nextMsgs)

  /** Handles what happens when the user clicks on one of the two choice buttons.
    *
    * We can only click the choice buttons if:
    *
    * left/right are both `Some(_)`, and:
    *
    * the commitment is `Some(false)` and the formula is of the form `And(a, b)`, or
    *
    * the commitment is `Some(true)` and the formula is of the form `Or(a, b)`.
    *
    * Otherwise clicking the choice buttons does nothing.
    *
    * @param choice
    *   `Left` or `Right`.
    * @param game
    *   Current state of the game.
    * @return
    *   New state of the game depending on the choice, commitment and formula.
    */
  private def handleChoice(choice: Choice, game: Game): Game =
    import Choice.*
    val play        = game.step.play
    val nextPlayOpt = (play, choice) match
      case (Play(And(a, b), Some(false), Some(l), Some(_)), Left)  => Some(play.setFormula(l))
      case (Play(And(a, b), Some(false), Some(_), Some(r)), Right) => Some(play.setFormula(r))
      case (Play(Or(a, b), Some(true), Some(l), Some(_)), Left)    => Some(play.setFormula(l))
      case (Play(Or(a, b), Some(true), Some(_), Some(r)), Right)   => Some(play.setFormula(r))
      case _                                                       => None
    nextPlayOpt match
      case Some(next) => game.addStep(next, Messager.show(next)(using game.board))
      case None       => game
  end handleChoice

  /** We can only click the OK button if a block has been selected and can be substituted into a formula, or a message
    * has been displayed and we need to move on to the next step. Otherwise clicking OK does nothing.
    *
    * A block can be substituted only if:
    *
    * `commitment` is `false`, `formula` is `All(x, f)` and `pos` is `On(_)`, or
    *
    * `commitment` is `true`, `formula` is `Ex(x, f)` and `pos` is `On(_)`.
    *
    * @param game
    *   Current state of the game.
    * @return
    *   New state of the game, depending on the commitment and formula.
    */
  private def handleOK(game: Game): Game = game.step match
    case Step(Play(All(x, f), Some(false), _, _), _, On(pos)) => subst(game, x, f, pos)
    case Step(Play(Ex(x, f), Some(true), _, _), _, On(pos))   => subst(game, x, f, pos)
    case _                                                    =>
      val play          = game.step.play
      given nm: NameMap = game.board.grid.toNameMap
      val nextPlayOpt   = (play.formula, play.commitment) match
        case (Iff(a: FOLFormula, b: FOLFormula), Some(true)) =>
          val choice = if Interpreter.eval(Imp(a, b)) then Imp(b, a) else Imp(a, b)
          Some(play.setFormula(choice))
        case (All(x, f), Some(true)) => Some(play.sub(AI.chooseBlock(f, x)(false), x, f))
        case (Ex(x, f), Some(false)) => Some(play.sub(AI.chooseBlock(f, x)(true), x, f))
        case (And(a, b), Some(true)) => Some(play.setFormula(if Interpreter.eval(a) then b else a))
        case (Or(a, b), Some(false)) => Some(play.setFormula(if Interpreter.eval(a) then a else b))
        case (Imp(a, b), _)          => Some(play.setFormula(Or(Neg(a), b)))
        case (Neg(_), _)             => Some(play.negate)
        case _                       => None
      nextPlayOpt match
        case Some(next) => game.addStep(next, Messager.show(next))
        case None       => game
  end handleOK

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
        val nextMsgs = Messager.show(nextPlay)(using game.board)
        game.addStep(nextPlay, nextMsgs)

end GameHandler
