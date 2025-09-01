package tarski

type State = Map[Pos, Option[Block]]

object State:
  def apply(size: Int): State =
    (for
      row <- 0 until size
      col <- 0 until size
    yield (row, col) -> None).toMap
