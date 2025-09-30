package tarski
package model

case class Controls(
    size: Option[Double] = None,
    shape: Option[Shape] = None,
    color: Option[Color] = None,
    name: Option[Name] = None,
    move: Boolean = false
):
  def enableMove  = copy(move = true)
  def disableMove = copy(move = false)
  def set(b: Block, name: Name) = copy(
    size = Some(b.size),
    shape = Some(b.shape),
    color = Some(b.color),
    name = if b.label.isEmpty then None else Some(b.label)
  )
