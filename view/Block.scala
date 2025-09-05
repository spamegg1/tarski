package tarski

case class Block(
    size: Size,     // Small, Medium, Large
    shape: Shape,   // Tri, Squ, Cir
    colour: Colour, // Blue, Black, Grey
    name: String = ""
):
  def addName(newName: String) = copy(name = newName)
  def removeName               = copy(name = "")
  def toImage                  = Text(name).font(TheFont).on(shape.toImage(size, colour))
  def smaller(that: Block)     = size < that.size
  def sameSize(that: Block)    = size == that.size
  def sameShape(that: Block)   = shape == that.shape
  def sameColour(that: Block)  = colour == that.colour
