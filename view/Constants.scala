package tarski

type Width      = Double
type Height     = Double
type Dimensions = (h: Height, w: Width)

// pure constants
val FontSz   = FontSize.points(30)
val Size     = 100.0
val BgColor  = gray
val Blue     = deepSkyBlue
val Gray     = lightGray
val Green    = yellowGreen
val Title    = "Tarski's World"
val TickRate = FiniteDuration(50L, "milliseconds")

// derived constants
val TheFont = Font.defaultSerif.bold.size(FontSz)
val Height  = Size * 8
val Width   = Size * 16
val Small   = Size * 0.4
val Medium  = Size * 0.7
val Large   = Size * 0.95

// basic shapes
val Sqr = Image.square(Size)
val Whi = Sqr.fillColor(white)
val Bla = Sqr.fillColor(black)
val Wb  = Whi beside Bla
val Bw  = Bla beside Whi

// derived shapes
val Line1   = Wb beside Wb beside Wb beside Wb
val Line2   = Bw beside Bw beside Bw beside Bw
val Quarter = Line1 above Line2
val Half    = Quarter above Quarter
val Board   = Half above Half

// frame
val MainFrame = Frame.default
  .withSize(Width, Height)
  .withBackground(BgColor)
  .withTitle(Title)
