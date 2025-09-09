package tarski

class ConverterTest extends munit.FunSuite:
  given Dimensions = (h = 800, w = 800)
  given GridSize   = (rows = 8, cols = 8)
  val positions    = Seq((0, 0), (0, 7), (7, 0), (7, 7), (2, 3), (5, 4))
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
      math.abs(p.x - that.x) < 0.0001 && math.abs(p.y - that.y) < 0.0001

  test("correctly convert Pos to Point on standard chess board"):
    positions
      .map(_.toPoint)
      .zip(points)
      .foreach: (obt, exp) =>
        assert(obt.isCloseTo(exp), s"Expected $exp, obtained $obt")

  test("correctly convert Point to Pos on standard chess board"):
    points
      .map(_.toPos)
      .zip(positions)
      .foreach: (obt, exp) =>
        assert(obt == exp, s"Expected $exp, obtained $obt")
