package tarski
package model

/** Enum for the three colors used in Tarski. It's called [[Tone]] to avoid name clashes with other types named `Color`.
  * We do not use [[doodle.core.Color]] directly, in order to benefit from exhaustive pattern matching and safety.
  */
enum Tone:
  case Blu, Lim, Red

/** Contains given instances to work with [[Tone]]. */
object Tone:
  /** An implicit conversion given instance that converts [[Tone]] to [[doodle.core.Color]]. */
  given Conversion[Tone, Color] =
    (t: Tone) =>
      t match
        case Blu => Color.deepSkyBlue
        case Lim => Color.limeGreen
        case Red => Color.coral
