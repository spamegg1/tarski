package tarski

type Height     = Double
type Width      = Double
type Dimensions = (h: Height, w: Width)

// pure constants
val Size        = 100.0
val BgColor     = white
val Blue        = deepSkyBlue
val Gray        = lightGray
val Green       = yellowGreen
val Title       = "Tarski's World"
val TickRate    = FiniteDuration(1000, "ms")
val BoardRows   = 8
val BoardCols   = 8
val ControlRows = 2
val ControlCols = 16
val Epsilon     = 0.0001
val UIOrigin    = Point(0, 0)

// derived constants
val UIRows        = BoardRows
val UICols        = BoardCols * 2
val BoardGridSize = (rows = BoardRows, cols = BoardCols)
val UIGridSize    = (rows = ControlRows, cols = ControlCols)

case class Constants(size: Double):
  val StrokeW       = size / 12.5
  val Pts           = size / 4
  val Height        = size * 8
  val Width         = Height * 2
  val Small         = size * 0.4
  val Mid           = size * 0.7
  val Large         = size * 0.95
  val SmallStroke   = StrokeW / 4
  val UIBottom      = Height * 3 / 8
  val BoardOrigin   = Point(-Width / 4, 0)
  val UIOrigin      = Point(Width / 4, Height * 7 / 16)
  val FormulaOrigin = Point(Width / 4, -Height / 16)
  val FontSz        = FontSize.points(Pts.toInt)
  val TheFont       = Font.defaultSansSerif.size(FontSz)
  val BoardDims     = (h = Height, w = Width / 2)
  val UIDims        = (h = Height / 8, w = Width / 2)

  // basic shapes
  val SmallSq = Image.square(Pts)
  val Sqr     = Image.square(Size)
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
