package tarski

enum Colour:
  case Blue, Black, Gray
  lazy val value = this match
    case Blue  => deepSkyBlue
    case Black => black
    case Gray  => lightGray
export Colour.*
