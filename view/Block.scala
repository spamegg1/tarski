package tarski

case class Block(
    size: Double, // Small, Medium, Large
    shape: Shape, // Tri, Squ, Cir
    color: Color, // Blue, Black, Grey
    name: String = ""
):
  def addName(newName: String) = copy(name = newName)
  def removeName               = copy(name = "")
  def toImage                  = Text(name).font(TheFont).on(shape.toImage(size, color))
  def smaller(that: Block)     = size < that.size
  def sameSize(that: Block)    = size == that.size
  def sameShape(that: Block)   = shape == that.shape
  def sameColor(that: Block)   = color == that.color
