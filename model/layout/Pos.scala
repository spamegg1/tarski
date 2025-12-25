package tarski
package model

/** Named tuple for a grid position. Both `row` and `col` start from 0 and count upward. Rows grow downward while
  * columns grow to the right.
  */
type Pos = (row: Int, col: Int)

/** Contains many extension methods for the [[Pos]] type alias. */
object Pos:
  extension (p: Pos)
    /** The up, down, left and right neighbors of this [[Pos]].
      *
      * @return
      *   A sequence of 4 positions that adjoin the given [[Pos]].
      */
    def neighbors = Seq(
      (p.row - 1, p.col),
      (p.row + 1, p.col),
      (p.row, p.col - 1),
      (p.row, p.col + 1)
    )

    /** Checks if this position is to the left of another position.
      *
      * @param q
      *   Another position.
      * @return
      *   true if `p` has a smaller column value than `q`, false otherwise.
      */
    def leftOf(q: Pos) = p.col < q.col

    /** Checks if this position is to the right of another position.
      *
      * @param q
      *   Another position.
      * @return
      *   true if `p` has a larger column value than `q`, false otherwise.
      */
    def rightOf(q: Pos) = p.col > q.col

    /** Checks if this position is below another position.
      *
      * @param q
      *   Another position
      * @return
      *   true if `p` has a larger row value than `q`, false otherwise.
      */
    def below(q: Pos) = p.row > q.row

    /** Checks if this position is above another position.
      *
      * @param q
      *   Another position.
      * @return
      *   true if `p` has a smaller row value than `q`, false otherwise.
      */
    def above(q: Pos) = p.row < q.row

    /** Checks if this position is on the same row as another position.
      *
      * @param q
      *   Another position.
      * @return
      *   true if `p` and `q` have the same row value, false otherwise.
      */
    def sameRow(q: Pos) = p.row == q.row

    /** Checks if this position is on the same column as another position.
      *
      * @param q
      *   Another position.
      * @return
      *   true if `p` and `q` have the same column value, false otherwise.
      */
    def sameCol(q: Pos) = p.col == q.col

    /** Checks if this position adjoins another position.
      *
      * @param q
      *   Another position.
      * @return
      *   true if `q` is a (up, down, left, right) neighbor of `p`.
      */
    def adjoins(q: Pos) = p.neighbors.contains(q)

    /** Checks if three positions are on the same row.
      *
      * @param q
      *   Another position.
      * @param r
      *   Another position.
      * @return
      *   true if all three positions have the same row value.
      */
    private def sameRow2(q: Pos, r: Pos) = p.sameRow(q) && p.sameRow(r)

    /** Checks if three positions are on the same column.
      *
      * @param q
      *   Another position.
      * @param r
      *   Another position.
      * @return
      *   true if all three positions have the same column value.
      */
    private def sameCol2(q: Pos, r: Pos) = p.sameCol(q) && p.sameCol(r)

    /** Checks if three positions are on different rows in a particular order.
      *
      * @param q
      *   Another position.
      * @param r
      *   Another position.
      * @return
      *   true if, from top to bottom, they are ordered as q-p-r; false otherwise.
      */
    private def rowBtw(q: Pos, r: Pos) = q.above(p) && p.above(r)

    /** Checks if three positions are on different columns in a particular order.
      *
      * @param q
      *   Another position.
      * @param r
      *   Another position.
      * @return
      *   true if, from left to right, they are ordered as q-p-r; false otherwise.
      */
    private def colBtw(q: Pos, r: Pos) = q.leftOf(p) && p.leftOf(r)

    /** Checks if three positions are on different rows.
      *
      * @param q
      *   Another position.
      * @param r
      *   Another position.
      * @return
      *   true if, from top to bottom, they are ordered as q-p-r or r-p-q; false otherwise.
      */
    private def rowBtw2(q: Pos, r: Pos) = p.rowBtw(q, r) || p.rowBtw(r, q)

    /** Checks if three positions are on different columns.
      *
      * @param q
      *   Another position.
      * @param r
      *   Another position.
      * @return
      *   true if, from left to right, they are ordered as q-p-r or r-p-q; false otherwise.
      */
    private def colBtw2(q: Pos, r: Pos) = p.colBtw(q, r) || p.colBtw(r, q)

    /** Checks if another position is on a bottom-right diagonal from this position.
      *
      * @param q
      *   Another position.
      * @return
      *   true if, from top-left to bottom-right, they are diagonally ordered as p-q; false otherwise.
      */
    private def diagTLBR(q: Pos) = p.row < q.row && p.col < q.col && p.row - q.row == p.col - q.col

    /** Checks if another position is on a top-left diagonal from this position.
      *
      * @param q
      *   Another position.
      * @return
      *   true if, from bottom-right to top-left, they are diagonally ordered as p-q; false otherwise.
      */
    private def diagBRTL(q: Pos) = q.diagTLBR(p)

    /** Checks if another position is on a bottom-left diagonal from this position.
      *
      * @param q
      *   Another position.
      * @return
      *   true if, from top-right to bottom-left, they are diagonally ordered as p-q; false otherwise.
      */
    private def diagTRBL(q: Pos) = p.row < q.row && p.col > q.col && -(p.row - q.row) == p.col - q.col

    /** Checks if another position is on a top-right diagonal from this position.
      *
      * @param q
      *   Another position.
      * @return
      *   true if, from bottom-left to top-right, they are diagonally ordered as p-q; false otherwise.
      */
    private def diagBLTR(q: Pos) = q.diagTRBL(p)

    /** Checks if three positions are on a bottom-left to top-right diagonal in a particular order.
      *
      * @param q
      *   Another position.
      * @param r
      *   Another position.
      * @return
      *   true if, from bottom-left to top-right, they are diagonally ordered as q-p-r; false otherwise.
      */
    private def botDiag(q: Pos, r: Pos) = q.diagBLTR(p) && p.diagBLTR(r)

    /** Checks if three positions are on a top-left to bottom-right diagonal in a particular order.
      *
      * @param q
      *   Another position.
      * @param r
      *   Another position.
      * @return
      *   true if, from top-left to bottom-right, they are diagonally ordered as q-p-r; false otherwise.
      */
    private def topDiag(q: Pos, r: Pos) = q.diagTLBR(p) && p.diagTLBR(r)

    /** Checks if three positions are on a bottom-left to top-right diagonal.
      *
      * @param q
      *   Another position.
      * @param r
      *   Another position.
      * @return
      *   true if, they are on a bottom-left to top-right diagonal as q-p-r or r-p-q; false ottherwise.
      */
    private def botDiag2(q: Pos, r: Pos) = p.botDiag(q, r) || p.botDiag(r, q)

    /** Checks if three positions are on a top-left to bottom-right diagonal.
      *
      * @param q
      *   Another position.
      * @param r
      *   Another position.
      * @return
      *   true if, they are on a top-left to bottom-right diagonal as q-p-r or r-p-q; false ottherwise.
      */
    private def topDiag2(q: Pos, r: Pos) = p.topDiag(q, r) || p.topDiag(r, q)

    /** Checks if three positions are on a diagonal.
      *
      * @param q
      *   Another position.
      * @param r
      *   Another position.
      * @return
      *   true if `p` is on a diagonal and in between `q` and `r` (in any order and direction), false otherwise.
      */
    private def diagBtw(q: Pos, r: Pos) = p.botDiag2(q, r) || p.topDiag2(q, r)

    /** Checks if this position between two other positions.
      *
      * @param q
      *   Another position.
      * @param r
      *   Another position.
      * @return
      *   true if `p` is horizontally, vertically or diagonally between `q` and `r`.
      */
    def between(q: Pos, r: Pos) =
      p.sameRow2(q, r) && p.colBtw2(q, r) ||
        p.sameCol2(q, r) && p.rowBtw2(q, r) ||
        p.diagBtw(q, r)
end Pos
