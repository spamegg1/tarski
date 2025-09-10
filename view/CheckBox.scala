package tarski

enum CheckBox:
  case Ready, Valid, Invalid

  def toImage: Image = this match
    case Ready   => CheckMark
    case Valid   => CheckMark
    case Invalid => CrossMark

export CheckBox.*
