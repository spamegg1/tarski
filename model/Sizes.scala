package tarski
package model

/** Enum for the three sizes used in Tarski. It's called [[Sizes]] to avoid name clashes with other types named `Size`.
  * We do not use [[Double]] directly, in order to benefit from exhaustive pattern matching and safety.
  */
enum Sizes:
  case Small, Mid, Large

  /** Compares two [[Sizes]].
    *
    * @param that
    *   Another size.
    * @return
    *   true if this size is smaller than that, false otherwise.
    */
  infix def <(that: Sizes) =
    (this, that) match
      case (Small, Mid)   => true
      case (Small, Large) => true
      case (Mid, Large)   => true
      case _              => false

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
        case Small => c.Small
        case Mid   => c.Mid
        case Large => c.Large
