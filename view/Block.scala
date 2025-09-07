package tarski

case class Block(
    size: Double, // Small, Medium, Large
    shape: Shape, // Tri, Squ, Cir
    color: Color, // Blue, Black, Grey
    label: String = ""
):
  def toImage                = Text(label).font(TheFont).on(shape.toImage(size, color))
  def smaller(that: Block)   = size < that.size
  def larger(that: Block)    = size > that.size
  def sameSize(that: Block)  = size == that.size
  def sameShape(that: Block) = shape == that.shape
  def sameColor(that: Block) = color == that.color
  def removeLabel            = copy(label = "")
  def setLabel(newLabel: String) = copy(label = newLabel)
