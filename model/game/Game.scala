package tarski
package model

type Message  = String
type Messages = List[Message]
type Step     = (play: Play, msgs: Messages)

case class Game(step: Step, prev: List[Step] = Nil, pos: Select[Pos] = Off, board: Board):
  import Select.*

  def setPos(p: Pos) = board.grid.get(p) match
    case None    => copy(pos = Wait)
    case Some(_) => copy(pos = On(p))

  def waitPos  = copy(pos = Wait)
  def unsetPos = copy(pos = Off)

  def rewind = prev match
    case head :: next => copy(step = head, prev = next)
    case Nil          => this

  def addStep(play: Play, msgs: Messages) = copy(step = (play, msgs), prev = step :: prev)
