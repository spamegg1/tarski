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
case class Step(play: Play, msgs: Messages, pos: Select[Pos])
