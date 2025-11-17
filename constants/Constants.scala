package tarski
package constants

type Dims     = (h: Double, w: Double)
type GridSize = (rows: Int, cols: Int)

// pure constants
val DefaultSize = 100.0
val BgColor     = white
val BlueColor   = deepSkyBlue
val GrayColor   = lightGray
val GreenColor  = yellowGreen
val Title       = "Tarski's World"
val TickRate    = FiniteDuration(1000, "ms")
val BoardRows   = 8
val BoardCols   = 8
val UIRows      = 2
val UICols      = 16
val Epsilon     = 0.0001
val BoardSize   = (rows = BoardRows, cols = BoardCols)
val UISize      = (rows = UIRows, cols = UICols)

case class Constants(size: Double):
  val StrokeW       = size * 0.08
  val Pts           = size * 0.25
  val Height        = size * 8.0
  val Width         = Height * 2.0
  val Small         = size * 0.4
  val Mid           = size * 0.7
  val Large         = size * 0.95
  val SmallStroke   = StrokeW * 0.25
  val UIBottom      = Height * 0.375
  val BoardOrigin   = Point(-Width * 0.25, 0)
  val UIOrigin      = Point(Width * 0.25, Height * 0.4375)
  val FormulaOrigin = Point(Width * 0.25, -Height * 0.0625)
  val FontSz        = FontSize.points(Pts.toInt)
  val TheFont       = Font.defaultSansSerif.size(FontSz)
  val BoardDims     = (h = Height, w = Width * 0.5)
  val UIDims        = (h = Height * 0.125, w = Width * 0.5)

  // basic shapes
  val SmallSq = Image.square(Pts)
  val Sqr     = Image.square(size)
  val WhiteSq = Sqr.fillColor(white)
  val BlackSq = Sqr.fillColor(black)

  // derived shapes
  val Wb      = WhiteSq beside BlackSq
  val Bw      = BlackSq beside WhiteSq
  val Wb8     = Wb beside Wb beside Wb beside Wb
  val Bw8     = Bw beside Bw beside Bw beside Bw
  val Quarter = Wb8 above Bw8
  val Half    = Quarter above Quarter
  val Board   = Half above Half

  val MainFrame = Frame.default
    .withSize(Width, Height)
    .withBackground(BgColor)
    .withTitle(Title)
    .withCenterAtOrigin
