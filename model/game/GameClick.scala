package tarski
package model

/** Enumeration for the commitment to a truth value for the current sentence. */
enum Commit:
  case True, False

  /** Provides the Boolean commitment value when the user clicks on `True` or `False` at the beginning of the game. Used
    * by [[controller.GameHandler.handleChoice]].
    *
    * @return
    *   `true` / `false` if the click is `True` / `False`.
    */
  def toBoolean = this match
    case True  => true
    case False => false

/** Enumeration for a choice between two formulas (in the case of an `Or` statement for example). */
enum Choice:
  case Left, Right

/** Enumeration for the remaining actions for the buttons in a game.
  *
  * `Back` and `OK` are to navigate the game steps, and `Display` is the block display (does nothing when clicked).
  */
enum GameAction:
  case Back, OK, Display

/** Union type for all the possible types of button clicks in the game. */
type GameClick = Commit | Choice | GameAction
