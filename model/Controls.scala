package tarski
package model

case class Controls(
    size: Option[Double] = None,
    shape: Option[Shape] = None,
    color: Option[Color] = None,
    pos: Option[Pos] = None,
    move: Boolean = false
):
  def selectPos(pos: Pos) = copy(pos = Some(pos))
  def deselectPos         = copy(pos = None)

  def setBlock(opt: Option[(block: Block, name: Name)]) = opt match
    case None => this
    case Some((b, _)) =>
      copy(
        size = Some(b.size),
        shape = Some(b.shape),
        color = Some(b.color)
      )

  def unsetBlock = copy(
    size = None,
    shape = None,
    color = None
  )

  def setSize(s: Double) = copy(size = Some(s))
  def setShape(s: Shape) = copy(shape = Some(s))
  def setColor(c: Color) = copy(color = Some(c))

  def toggleMove = copy(move = !move)
