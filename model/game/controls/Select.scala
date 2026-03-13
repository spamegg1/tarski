package tarski
package model

/** TODO
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
