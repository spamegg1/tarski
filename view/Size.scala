package tarski

enum Size:
  case Small, Medium, Large

  lazy val value = this match
    case Small  => SmallSize
    case Medium => MidSize
    case Large  => LargeSize

  infix def <(that: Size) = value < that.value
export Size.*
