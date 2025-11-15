package tarski
package model

enum Sizes:
  case Small, Mid, Large
  infix def <(that: Sizes) = (this, that) match
    case (Small, Mid) => true
    case (Small, Large) => true
    case (Mid, Large) => true
    case _ => false
export Sizes.*

object Sizes:
  given (c: Constants) => Conversion[Sizes, Double] =
    (s: Sizes) =>
      s match
        case Small => c.Small
        case Mid => c.Mid
        case Large => c.Large
