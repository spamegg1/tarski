package tarski
package view

case class Controls(
    selectedColor: Color = Blue,
    selectedShape: Shape = Shape.Squ,
    selectedSize: Double = Small
):
  def toImage = BlueSq beside GreenSq beside GraySq
