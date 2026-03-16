package tarski
package model

/** Type alias for a game message displayed to the user. */
type Message = String

/** Type alias for the game messages displayed to the user. */
type Messages = List[Message]

/** A class consisting of the state of play, the messages that correspond to it, and the selection state. This is used
  * to record the game play and to rewind to an earlier state if needed.
  *
  * @param play
  *   The current state of play.
  * @param msgs
  *   The messages corresponding to the current play state.
  * @param pos
  *   The current state of selecting a block on the board.
  */
case class Step(play: Play, msgs: Messages, pos: Select[Pos]):
  import Select.*

  /** Sets the [[Select]] state to `Select.Wait`.
    *
    * @return
    *   A copy of this `Step` with position state set to `Select.Wait`.
    */
  def waitPos = copy(pos = Wait)

  /** Sets the [[Select]] state to a given [[Pos]].
    *
    * @param p
    *   A position on the board.
    * @return
    *   A copy of this `Step` with position state set to `Select.On(p)`.
    */
  def setPos(p: Pos) = copy(pos = On(p))

  /** Sets the [[Select]] state to `Select.Off`.
    *
    * @return
    *   A copy of this `Step` with position state set to `Select.Off`.
    */
  def unsetPos = copy(pos = Off)

  /** Checks if the current formula in `play` requires a block to be selected (in the case of a universal or existential
    * sentence).
    *
    * @return
    *   A copy of this `Step` with `Select` state updated based on the formula.
    */
  def checkAndSetWait = if play.checkWait then waitPos else unsetPos

  /** Updates this `Step` with the given new [[Play]] and [[Messages]].
    *
    * @param nextPlay
    *   The next state of play.
    * @param nextMsgs
    *   The messages to be displayed at the next state.
    * @return
    *   The next step updated accordingly.
    */
  def next(nextPlay: Play, nextMsgs: Messages) = copy(play = nextPlay, msgs = nextMsgs).checkAndSetWait

/** Contains a convenient alternate constructor for [[Step]]. */
object Step:
  /** Every game starts with the same message asking the user for a commitment. */
  private val initMsgs = List("Choose initial commitment True/False above:")

  /** Convenient method to create an initial `Step` for a game's beginning.
    *
    * @param f
    *   A first-order formula.
    * @return
    *   An initial `Step` for a game with the given formula.
    */
  def apply(f: FOLFormula): Step = Step(Play(f), initMsgs, Select.Off)
