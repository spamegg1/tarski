package tarski
package view

enum CheckBox:
  case Ready, Valid, Invalid

  def toImage: Image = this match
    case Ready   => ReadyMark
    case Valid   => CheckMark
    case Invalid => CrossMark

export CheckBox.*
