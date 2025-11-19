package tarski
package model

case class Controls(
    sizeOpt: Option[Sizes] = None,
    shapeOpt: Option[Shape] = None,
    toneOpt: Option[Tone] = None,
    posOpt: Option[Pos] = None,
    move: Boolean = false
):
  def selectPos(pos: Pos) = copy(posOpt = Some(pos))
  def deselectPos         = copy(posOpt = None)
  def unsetBlock          = copy(sizeOpt = None, shapeOpt = None, toneOpt = None)
  def setBlock(opt: Option[(block: Block, name: Name)]) = opt match
    case None => this
    case Some((b, _)) =>
      copy(
        sizeOpt = Some(b.size),
        shapeOpt = Some(b.shape),
        toneOpt = Some(b.tone)
      )

  def setAttr(attr: Attr) = attr match
    case sz: Sizes => copy(sizeOpt = Some(sz))
    case sh: Shape => copy(shapeOpt = Some(sh))
    case t: Tone   => copy(toneOpt = Some(t))

  def toggleMove = copy(move = !move)
