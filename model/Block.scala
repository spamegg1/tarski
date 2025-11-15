package tarski
package model

case class Block(
    size: Sizes,  // Small, Medium, Large
    shape: Shape, // Tri, Squ, Cir
    color: Color, // Blue, Black, Grey
    label: String = ""
):
  def removeLabel             = copy(label = "")
  def smaller(that: Block)    = size < that.size
  def larger(that: Block)     = that.size < size
  def sameSize(that: Block)   = size == that.size
  def sameShape(that: Block)  = shape == that.shape
  def sameColor(that: Block)  = color == that.color
  def setLabel(label: String) = copy(label = label)

object Block:
  def fromControls(c: Controls) =
    c.size.flatMap: size =>
      c.shape.flatMap: shape =>
        c.color.flatMap: color =>
          Some(Block(size, shape, color))
