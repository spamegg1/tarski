package tarski
package model

/** A block to be placed on a square of the chess board.
  *
  * @param size
  *   one of Small, Mid, Large
  * @param shape
  *   one of Tri, Squ, Cir
  * @param tone
  *   one of Blue, Green, Orange
  * @param label
  *   an optional name, defaults to the empty string.
  */
case class Block(
    size: Sizes,
    shape: Shape,
    tone: Tone,
    label: String = ""
):
  /** Removes label from this [[Block]]. Used by [[World]].
    *
    * @return
    *   The same block with its label removed.
    */
  def removeLabel = copy(label = "")

  /** Compares sizes of two blocks. Used by [[controller.Interpreter.evalAtom]].
    *
    * @param that
    *   Another block to compare with.
    * @return
    *   `true` if this block's size is smaller, `false` otherwise.
    */
  def smaller(that: Block) = size < that.size

  /** Compares sizes of two blocks. Used by [[controller.Interpreter.evalAtom]].
    *
    * @param that
    *   Another block to compare with.
    * @return
    *   `true` if this block's size is larger, `false` otherwise.
    */
  def larger(that: Block) = that.size < size

  /** Compares sizes of two blocks. Used by [[controller.Interpreter.evalAtom]].
    *
    * @param that
    *   Another block to compare with.
    * @return
    *   `true` if the blocks have the same size, `false` otherwise.
    */
  def sameSize(that: Block) = size == that.size

  /** Compares shapes of two blocks. Used by [[controller.Interpreter.evalAtom]].
    *
    * @param that
    *   Another block to compare with.
    * @return
    *   `true` if the blocks have the same shape, `false` otherwise.
    */
  def sameShape(that: Block) = shape == that.shape

  /** Compares tones of two blocks. Used by [[controller.Interpreter.evalAtom]].
    *
    * @param that
    *   Another block to compare with.
    * @return
    *   `true` if the blocks have the same tone, `false` otherwise.
    */
  def sameColor(that: Block) = tone == that.tone

  /** Changes the label of this block.
    *
    * @param label
    *   A `String`, only one of `"a", "b", "c", "d", "e", "f"`. Other labels are not allowed.
    * @return
    *   The same block with its label changed.
    */
  def setLabel(label: String) =
    require(Names.TheNames.contains(label))
    copy(label = label)

  /** Changes any one of the 3 attributes (size, shape, tone).
    *
    * @param attr
    *   either a [[Sizes]], or a [[Shape]] or a [[Tone]].
    * @return
    *   a new block with the given attribute changed.
    */
  def setAttr(attr: Attr) = attr match
    case sz: Sizes => copy(size = sz)
    case sh: Shape => copy(shape = sh)
    case t: Tone   => copy(tone = t)

/** Contains some helper methods for the [[Block]] class. */
object Block:
  /** Creates an optional [[Block]] from the current state of the controls in the user interface, if all 3 attributes
    * are present. This is useful when the user wants to add a new block to the board by selecting its size, shape and
    * tone from the UI.
    *
    * @param c
    *   Current state of the controls in the UI (tone, shape, size)
    * @return
    *   `Some` block if all 3 attributes have a value, `None` otherwise.
    */
  def fromControls(c: Controls) =
    for
      size  <- c.sizeOpt
      shape <- c.shapeOpt
      tone  <- c.toneOpt
    yield Block(size, shape, tone)
