package tarski
package model

/** Contains the state of the user interface controls and the board.
  *
  * @param sizeOpt
  *   The size that is currently selected on the user interface (if any).
  * @param shapeOpt
  *   The shape that is currently selected on the user interface (if any).
  * @param toneOpt
  *   The tone that is currently selected on the user interface (if any).
  * @param posOpt
  *   The position that is currently selected on the chess board (if any).
  * @param move
  *   The toggle state of the "Move" button.
  */
case class Controls(
    sizeOpt: Option[Sizes] = None,
    shapeOpt: Option[Shape] = None,
    toneOpt: Option[Tone] = None,
    posOpt: Option[Pos] = None,
    move: Boolean = false
):
  /** Selects a position on the chess board.
    *
    * @param pos
    *   The position on the board we want to select.
    * @return
    *   A new [[Controls]] instance, with the position selected.
    */
  def selectPos(pos: Pos) = copy(posOpt = Some(pos))

  /** De-selects the currently selected position on the chess board.
    *
    * @return
    *   A new [[Controls]] instance, with no position selected.
    */
  def deselectPos = copy(posOpt = None)

  /** Clears all the selected [[Block]] attributes (size, shape, tone).
    *
    * @return
    *   A new [[Controls]] instance, with all [[Block]] attributes set to `None`.
    */
  def unsetBlock = copy(sizeOpt = None, shapeOpt = None, toneOpt = None)

  /** Sets the user interface controls to be the same as the attributes of the selected block.
    *
    * @param opt
    *   An optional `(Block, Name)` pair, depending on whether or not the selected position on the board is empty or
    *   contains a block.
    * @return
    *   A new [[Controls]] instance, updated to display the attributes of the selected block on the user interface.
    */
  def setBlock(opt: Option[(block: Block, name: Name)]) = opt match
    case None         => this
    case Some((b, _)) =>
      copy(
        sizeOpt = Some(b.size),
        shapeOpt = Some(b.shape),
        toneOpt = Some(b.tone)
      )

  /** Sets any one of the 3 attributes (size, shape, tone).
    *
    * @param attr
    *   An attribute we'd like to set (size, shape or tone).
    * @return
    *   A new [[Controls]] instance, with the given attribute changed and set.
    */
  def setAttr(attr: Attr) = attr match
    case sz: Sizes => copy(sizeOpt = Some(sz))
    case sh: Shape => copy(shapeOpt = Some(sh))
    case t: Tone   => copy(toneOpt = Some(t))

  /** Toggles the "Move" button (the ability to move a block to another square).
    *
    * @return
    *   A new [[Controls]] instance with the move value flipped.
    */
  def toggleMove = copy(move = !move)
