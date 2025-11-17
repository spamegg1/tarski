package tarski
package model

case class Controls(
    size: Option[Sizes] = None,
    shape: Option[Shape] = None,
    tone: Option[Tone] = None,
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
        tone = Some(b.tone)
      )

  def unsetBlock = copy(
    size = None,
    shape = None,
    tone = None
  )

  def setAttr(attr: Attr) = attr match
    case sz: Sizes => copy(size = Some(sz))
    case sh: Shape => copy(shape = Some(sh))
    case t: Tone   => copy(tone = Some(t))

  def toggleMove = copy(move = !move)
