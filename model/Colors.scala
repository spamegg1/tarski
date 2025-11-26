package tarski
package model

enum Tone:
  case Blue, Green, Gray

object Tone:
  given Conversion[Tone, Color] =
    (t: Tone) =>
      t match
        case Blue  => BlueColor
        case Green => GreenColor
        case Gray  => GrayColor
