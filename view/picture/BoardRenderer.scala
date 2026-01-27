package tarski
package view

/** This trait factours out the common components of [[WorldRenderer]] and [[GameRenderer]].
  *
  * @param ui
  *   An instance of [[UI]]
  * @param c
  *   A given instance of [[Constants]]
  */
trait BoardRenderer(ui: UI)(using c: Constants):
  /** Displays the block on the user interface controls.
    *
    * @param blockOpt
    *   An optional Block, normally coming from a [[World]] or a [[Game]].
    * @return
    *   An image of the block, or an empty image.
    */
  def renderBlock(blockOpt: Option[Block]): Image = Imager(blockOpt).at(ui.blockPt)

  /** Draws a red indicator box around the selected position on the board.
    *
    * @param pos
    *   The position of the selected square on the chess board.
    * @return
    *   A red-edged, empty square to fit around the selected square on the chess board.
    */
  def selectedPos(pos: Option[Pos]): Image =
    pos match
      case None      => Image.empty
      case Some(pos) =>
        Image
          .rectangle(Converter.board.blockWidth, Converter.board.blockHeight)
          .strokeColor(Color.red)
          .strokeWidth(c.SmallStroke)
          .at(Converter.board.toPoint(pos))

  /** Draws all the blocks on the chess board.
    *
    * @param grid
    *   A map of positions and blocks
    * @return
    *   An image of the chess board, with blocks at their positions.
    */
  def blocksOnBoard(board: Board): Image = board.grid
    .foldLeft[Image](c.Board):
      case (image, (pos, (block, name))) =>
        Imager(block)
          .at(Converter.board.toPoint(pos))
          .on(image)
