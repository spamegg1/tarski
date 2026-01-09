package tarski
package model

/** Type alias for a game message displayed to the user. */
type Message = String

/** Type alias for the game messages displayed to the user. */
type Messages = List[Message]

/** A named tuple consisting of the state of play and the messages that correspond to it. This is used to record the
  * game play and to rewind to an earlier state if needed.
  */
type Step = (play: Play, msgs: Messages)

/** The core Game data structure.
  *
  * @param step
  *   The current play, and its corresponding messages to be displayed to the user.
  * @param prev
  *   A list of all the previous steps (pairs of play and messages); the history of the game play.
  * @param pos
  *   The state of currently selected position on the board.
  * @param board
  *   The board that holds the blocks for the game.
  */
case class Game(step: Step, board: Board, prev: List[Step] = Nil, pos: Select[Pos] = Off):
  import Select.*

  /** Used to change the position when the user clicks on the board. If the user clicked on an empty spot with no block,
    * then we continue waiting for a position to be selected. If a block was already selected and the user clicked on
    * another block, then we switch to that block.
    *
    * @param p
    *   The position the user clicked on.
    * @return
    *   New state of the game where the position is updated accordingly.
    */
  def setPos(p: Pos) = board.grid.get(p) match
    case None    => copy(pos = Wait)
    case Some(_) => copy(pos = On(p))

  def waitPos  = copy(pos = Wait)
  def unsetPos = copy(pos = Off)

  /** Used to go to a previous state of the game.
    *
    * @return
    *   State of the immediately previous game, if available.
    */
  def rewind = prev match
    case head :: next => copy(step = head, prev = next)
    case Nil          => this

  /** Used to advance the game forward.
    *
    * @param play
    *   The next state of play.
    * @param msgs
    *   The messages to be displayed to the user in the next state of play.
    * @return
    *   New game that advances the state of play to the given play and messages.
    */
  def addStep(play: Play, msgs: Messages) = copy(step = (play, msgs), prev = step :: prev)

  /** Looks up the block at selected position on the board.
    *
    * @return
    *   The block at selected position on the board, if any.
    */
  def getBlock: Option[Block] = pos.opt match
    case None    => None
    case Some(p) => board.grid.get(p).map(_.block)
