package tarski

// stored as 0 to 63: 0 to 7 is the top row, 8 to 15 second row, etc.
//  0  1  2  3  4  5  6  7
//  8  9 10 11 12 13 14 15
// 16 17 18 19 20 21 22 23
// 24 25 26 27 28 29 30 31
// 32 33 34 35 36 37 38 39
// 40 41 42 43 44 45 46 47
// 48 49 50 51 52 53 54 55
// 56 57 58 59 60 61 62 63
case class State(board: Vector[Option[Block]], formulas: Vector[Formula]):
  require(board.size == 64)
  def upDiag1 = Vector(board(0))
  def upDiag2 = Vector(board(1), board(8))
  def upDiag3 = Vector(board(2), board(9), board(16))
  def upDiag4 = Vector(board(3), board(10), board(17), board(24))
  def upDiag5 = Vector(board(4), board(11), board(18), board(25), board(32))
  def upDiag6 = Vector(board(5), board(12), board(19), board(26), board(33), board(40))
  def upDiag7 =
    Vector(board(6), board(13), board(20), board(27), board(34), board(41), board(48))
