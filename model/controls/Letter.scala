package tarski
package model

/** Enumeration for the 6 letter buttons on the user interface controls. */
enum Letter:
  case A, B, C, D, E, F

  /** Converts this to a [[Name]] so that its corresponding block can be looked up in the world.
    *
    * @return
    *   A string that is the letter name, one of `a, b, c, d, e, f`.
    */
  def toName: Name = this match
    case A => "a"
    case B => "b"
    case C => "c"
    case D => "d"
    case E => "e"
    case F => "f"
