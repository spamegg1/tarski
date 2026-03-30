package tarski
package model

/** Enum for the state of selecting a position on the board.
  *
  * If the user is not being prompted to select a block, then it's `Off`. When user is prompted and we are awaiting
  * user's input, it's `Wait`. When a `pos` on the board is selected, it's `On(pos)`.
  *
  * It is parametric, so it could be potentially used for other selection states.
  */
enum Select[+T]:
  case Off
  case Wait
  case On(t: T)

  /** Converts this select state to an `Option`.
    *
    * @return
    *   `Some(t)` if `On(t)`, `None` otherwise.
    */
  def opt: Option[T] = this match
    case Off   => None
    case Wait  => None
    case On(t) => Some(t)

export Select.*
