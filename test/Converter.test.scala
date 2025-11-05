package tarski
package testing

class ConverterTest extends munit.FunSuite:
  val positions = Seq((0, 0), (0, 7), (7, 0), (7, 7), (2, 3), (5, 4))
  val points = Seq(
    Point(-350, 350),
    Point(350, 350),
    Point(-350, -350),
    Point(350, -350),
    Point(-50, 150),
    Point(50, -150)
  )

  extension (p: Point)
    def isCloseTo(that: Point) =
      math.abs(p.x - that.x) < Epsilon && math.abs(p.y - that.y) < Epsilon

  test("correctly convert Pos to Point on standard chess board"):
    positions
      .map(BoardConverter.toPoint)
      .zip(points)
      .foreach: (obt, exp) =>
        assert(obt.isCloseTo(exp), s"Expected $exp, obtained $obt")

  test("correctly convert Point to Pos on standard chess board"):
    points
      .map(BoardConverter.toPos)
      .zip(positions)
      .foreach: (obt, exp) =>
        assert(obt == exp, s"Expected $exp, obtained $obt")

  test("point to pos conversion works for all points inside a block"):
    Seq(Point(-99, 101), Point(-1, 101), Point(-99, 199), Point(-1, 199))
      .map(BoardConverter.toPos)
      .foreach: pos =>
        assert(pos == (row = 2, col = 3), s"expected (2,3), obtained $pos")
