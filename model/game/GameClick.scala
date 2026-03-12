package tarski
package model

enum GameClick:
  case Left, Right, True, False, Back, OK, Display

  def toBoolean = this match
    case True  => true
    case False => false
    case _     => false
