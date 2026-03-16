package tarski
package model

/** The core Game data structure.
  *
  * @param step
  *   The current play, and its corresponding messages to be displayed to the user.
  * @param prev
  *   A list of all the previous steps (pairs of play and messages); the history of the game play.
  * @param board
  *   The board that holds the blocks for the game.
  */
case class Game(step: Step, prev: List[Step], board: Board):
  import Select.*

  /** Changes the position when the user clicks on the board. Does not advance the step or produce messages.
    *
    * If the user clicked on an empty spot with no block, then we continue waiting for a position to be selected. If a
    * block was already selected and the user clicked on another block, then we switch to that block.
    *
    * @param p
    *   The position the user clicked on.
    * @return
    *   New state of the game where the position is updated accordingly.
    */
  def setPos(p: Pos) = board.grid.get(p) match
    case None    => waitPos
    case Some(_) => copy(step = step.setPos(p))

  /** Sets the step's `pos` to [[Select.Wait]]. Does not advance the step, or produce messages.
    *
    * @return
    *   A copy of this game with the current step's `pos` set to `Wait`.
    */
  def waitPos = copy(step = step.waitPos)

  /** Sets the `pos` to [[Select.Off]]. Does not advance the step, or produce messages.
    *
    * @return
    *   A copy of this game with the current step's `pos` set to `Off`.
    */
  def unsetPos = copy(step = step.unsetPos)

  /** Goes to a previous state of the game.
    *
    * @return
    *   State of the immediately previous game, if available.
    */
  def rewind = prev match
    case head :: next => copy(step = head, prev = next)
    case Nil          => this

  /** Advances the game forward by adding one step.
    *
    * @param newStep
    *   The next step in the game.
    * @return
    *   New game that advances the state of play to the given new step.
    */
  def advance(newStep: Step) = copy(step = newStep, prev = step :: prev)

  /** Adds the given new state of [[Play]] and new [[Messages]], then advances the [[Step]] of the game. */
  val addStep = step.next.tupled andThen advance

  /** Looks up the block at selected position on the board.
    *
    * @return
    *   The block at selected position on the board, if any.
    */
  def getBlock: Option[Block] = step.pos.opt
    .flatMap(board.grid.get)
    .map(_.block)

  /** The list of messages to be displayed.
    *
    * @return
    *   A list of messages of the current step and the previous step.
    */
  def messages: Messages =
    val currentMsgs = "Current step:" :: step.msgs
    val prevMsgs    = "" :: "Previous step:" :: prev.take(1).flatMap(_.msgs)
    currentMsgs ::: prevMsgs

object Game:
  /** Convenient alternate constructor for [[Game]].
    *
    * @param formula
    *   An instance of `FOLFormula`.
    * @param grid
    *   An instance of [[Grid]].
    * @return
    *   The initial state of a [[Game]] with given formula and given grid used as the board.
    */
  def apply(formula: FOLFormula, grid: Grid): Game = Game(Step(formula), Nil, Board.fromGrid(grid))
