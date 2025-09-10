package tarski

type Width      = Double
type Height     = Double
type Dimensions = (h: Height, w: Width)

// pure constants
val Pts      = 30
val FontSz   = FontSize.points(Pts)
val TheFont  = Font.defaultSerif.bold.size(FontSz)
val Size     = 100.0
val BgColor  = gray
val Blue     = deepSkyBlue
val Gray     = lightGray
val Green    = yellowGreen
val Title    = "Tarski's World"
val TickRate = FiniteDuration(50L, "milliseconds")

// derived constants
val Height = Size * 8
val Width  = Size * 16
val Small  = Size * 0.4
val Medium = Size * 0.7
val Large  = Size * 0.95

// basic shapes
val Sqr     = Image.square(Size)
val Whi     = Sqr.fillColor(white)
val Bla     = Sqr.fillColor(black)
val Wb      = Whi beside Bla
val Bw      = Bla beside Whi
val RedLine = Image.line(40, 0).strokeWidth(8).strokeColor(red)

// derived shapes
val Wb8       = Wb beside Wb beside Wb beside Wb
val Bw8       = Bw beside Bw beside Bw beside Bw
val Quarter   = Wb8 above Bw8
val Half      = Quarter above Quarter
val Board     = Half above Half
val CrossMark = RedLine.rotate(45.degrees).on(RedLine.rotate(135.degrees))
val CheckMark = Image
  .path:
    OpenPath.empty
      .lineTo(20, -20)
      .lineTo(50, 20)
  .strokeColor(green)
  .strokeWidth(8)

// frame
val MainFrame = Frame.default
  .withSize(Width, Height)
  .withBackground(BgColor)
  .withTitle(Title)
