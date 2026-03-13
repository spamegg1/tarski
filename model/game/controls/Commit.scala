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
