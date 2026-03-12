package tarski
package model

/** Enumeration for all the possible types of button clicks in the game.
  *
  * `Left` and `Right` correspond to a choice between two formulas (in the case of an `Or` statement for example),
  * `True` and `False` correspond to the commitment to a truth value for the current sentence, `Back` and `OK`
  * correspond to navigating the game steps, and `Display` refers to the block display (does nothing when clicked).
  */
enum GameClick:
  case Left, Right, True, False, Back, OK, Display

  /** Provides the Boolean commitment value when the user clicks on `True` or `False` at the beginning of the game. Used
    * by [[controller.GameHandler.handleChoice]].
    *
    * @return
    *   `true` / `false` if the click is `True` / `False`, `false` otherwise.
    */
  def toBoolean = this match
    case True  => true
    case False => false
    case _     => false
