package tarski
package model

case class Block(
    size: Sizes,  // Small, Medium, Large
    shape: Shape, // Tri, Squ, Cir
    tone: Tone,   // Blue, Black, Grey
    label: String = ""
):
  def removeLabel             = copy(label = "")
  def smaller(that: Block)    = size < that.size
  def larger(that: Block)     = that.size < size
  def sameSize(that: Block)   = size == that.size
  def sameShape(that: Block)  = shape == that.shape
  def sameColor(that: Block)  = tone == that.tone
  def setLabel(label: String) = copy(label = label)

  def setAttr(attr: Attr) = attr match
    case sz: Sizes => copy(size = sz)
    case sh: Shape => copy(shape = sh)
    case t: Tone   => copy(tone = t)

object Block:
  def fromControls(c: Controls) =
    for
      size  <- c.size
      shape <- c.shape
      tone  <- c.tone
    yield Block(size, shape, tone)
