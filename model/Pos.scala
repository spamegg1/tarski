package tarski

type Pos = (row: Int, col: Int)

extension (p: Pos)
  def neighbors = Seq(
    (p.row - 1, p.col),
    (p.row + 1, p.col),
    (p.row, p.col - 1),
    (p.row, p.col + 1)
  )

  def leftOf(q: Pos)  = p.col < q.col
  def rightOf(q: Pos) = p.col > q.col
  def frontOf(q: Pos) = p.row > q.row
  def backOf(q: Pos)  = p.row < q.row
  def sameRow(q: Pos) = p.row == q.row
  def sameCol(q: Pos) = p.col == q.col
  def adjoins(q: Pos) = p.neighbors.contains(q)

  def sameRow2(q: Pos, r: Pos) = p.sameRow(q) && p.sameRow(r)
  def sameCol2(q: Pos, r: Pos) = p.sameCol(q) && p.sameCol(r)
  def rowBtw(q: Pos, r: Pos)   = q.backOf(p) && p.backOf(r)
  def colBtw(q: Pos, r: Pos)   = q.leftOf(p) && p.leftOf(r)
  def rowBtw2(q: Pos, r: Pos)  = p.rowBtw(q, r) || p.rowBtw(r, q)
  def colBtw2(q: Pos, r: Pos)  = p.colBtw(q, r) || p.colBtw(r, q)

  def botDiag(q: Pos, r: Pos)  = p.colBtw(q, r) && p.rowBtw(r, q)
  def topDiag(q: Pos, r: Pos)  = p.colBtw(q, r) && p.rowBtw(q, r)
  def botDiag2(q: Pos, r: Pos) = p.botDiag(q, r) || p.botDiag(r, q)
  def topDiag2(q: Pos, r: Pos) = p.topDiag(q, r) || p.topDiag(r, q)
  def diagBtw(q: Pos, r: Pos)  = p.botDiag2(q, r) || p.topDiag2(q, r)

  def between(q: Pos, r: Pos) =
    p.sameRow2(q, r) && p.colBtw2(q, r) ||
      p.sameCol2(q, r) && p.rowBtw2(q, r) ||
      p.diagBtw(q, r)
