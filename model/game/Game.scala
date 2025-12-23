package tarski
package model

type Message = String

// There will be a handler that initially "sets up" the game by first
// asking the user for their commitment. So we don't have to use Option[Boolean].

case class Game(
    board: Board,
    formula: FOLFormula,
    messages: List[Message] = Nil,
    pos: Select[Pos] = Off,
    commitment: Option[Boolean] = None,
    left: Option[FOLFormula] = None,
    right: Option[FOLFormula] = None
):
  def setPos(p: Pos) = board.grid.get(p) match
    case None    => copy(pos = Wait)
    case Some(_) => copy(pos = On(p))

  def unsetPos = copy(pos = Wait)

  // def chooseBlock(n: Name): Game = name match
  //   case Off   => this // should not happen
  //   case Wait  => copy(name = On(n))
  //   case On(_) => copy(name = On(n))

  def chooseFormula(f: FOLFormula): Game = pos match
    case Off   => this // should not happen
    case Wait  => copy(formula = f, left = None, right = None, pos = Off)
    case On(_) => this // should not happen
