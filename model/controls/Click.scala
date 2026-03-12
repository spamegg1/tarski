package tarski
package model

/** Enumeration for all the possible button clicks in Tarski's world. */
enum Click:
  case Eval, Add, A, B, C, D, E, F, Blu, Lim, Red, Left, Right, Icon, Move, Del, Sml, Mid, Big, Tri, Sqr, Cir

  /** Provides the [[Name]] for a [[Block]] corresponding to the name button the user clicks on.
    *
    * @return
    *   A string for the name, one of `"a", "b", "c", "d", "e", "f"`.
    */
  def toName: Name = this match
    case A => "a"
    case B => "b"
    case C => "c"
    case D => "d"
    case E => "e"
    case F => "f"

  /** Provides the [[Attr]] corresponding to the attribute button the user clicks on.
    *
    * @return
    *   An attribute value: one of the 3 [[Tone]]s, 3 [[Sizes]] or 3 [[Shape]]s.
    */
  def toAttr: Attr = this match
    case Blu => Tone.Blu
    case Lim => Tone.Lim
    case Red => Tone.Red
    case Sml => Sizes.Sml
    case Mid => Sizes.Mid
    case Big => Sizes.Big
    case Tri => Shape.Tri
    case Sqr => Shape.Sqr
    case Cir => Shape.Cir
