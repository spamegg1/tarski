package tarski
package model

case class Controls(
    size: Option[Double] = None,
    shape: Option[Shape] = None,
    color: Option[Color] = None,
    name: Option[Name] = None,
    pos: Option[Pos] = None,
    move: Boolean = false
):
  def selectPos(pos: Pos) = copy(pos = Some(pos))
  def deselectPos         = copy(pos = None)

  def set(b: Block, name: Name) = copy(
    size = Some(b.size),
    shape = Some(b.shape),
    color = Some(b.color),
    name = if b.label.isEmpty then None else Some(b.label)
  )

  def setSize(s: Double) = copy(size = Some(s))
  def setShape(s: Shape) = copy(shape = Some(s))
  def setColor(c: Color) = copy(color = Some(c))

  def toggleMove = copy(move = !move)
