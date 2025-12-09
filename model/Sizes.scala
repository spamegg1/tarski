package tarski
package model

/** Enum for the three sizes used in Tarski. It's called [[Sizes]] to avoid name clashes with other types named `Size`.
  * We do not use [[Double]] directly, in order to benefit from exhaustive pattern matching and safety.
  */
enum Sizes:
  case Sml, Mid, Big

  /** Compares two [[Sizes]].
    *
    * @param that
    *   Another size.
    * @return
    *   true if this size is smaller than that, false otherwise.
    */
  infix def <(that: Sizes) =
    (this, that) match
      case (Sml, Mid) => true
      case (Sml, Big) => true
      case (Mid, Big) => true
      case _          => false

/** Contains given instances to work with [[Sizes]]. */
object Sizes:
  /** A conditional given instance to convert [[Sizes]] to [[Double]].
    *
    * @param c
    *   A given instance of [[constants.Constants]].
    * @return
    *   An implicit conversion given instance that converts [[Sizes]] to their intended [[Double]] values.
    */
  given (c: Constants) => Conversion[Sizes, Double] =
    (s: Sizes) =>
      s match
        case Sml => c.Small
        case Mid => c.Medium
        case Big => c.Large
