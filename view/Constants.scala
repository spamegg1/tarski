package tarski

type Width      = Double
type Height     = Double
type Dimensions = (w: Width, h: Height)

// pure constants
val Pts      = 30
val Size     = 100.0
val BgColor  = lightGray
val Blue     = deepSkyBlue
val Gray     = lightGray
val Green    = yellowGreen
val Title    = "Tarski's World"
val TickRate = FiniteDuration(50L, "milliseconds")
val StrokeW  = 8

// derived constants
val Height  = Size * 8
val Width   = Height * 2
val Small   = Size * 0.4
val Medium  = Size * 0.7
val Large   = Size * 0.95
val LineLen = 1.2 * Pts
val FontSz  = FontSize.points(Pts)
val TheFont = Font.defaultSansSerif.size(FontSz)

// basic shapes
val SmallSq   = Image.square(Pts)
val Sqr       = Image.square(Size)
val WhiteSq   = Sqr.fillColor(white)
val BlackSq   = Sqr.fillColor(black)
val BlueSq    = SmallSq.fillColor(Blue)
val GraySq    = SmallSq.fillColor(Gray)
val GreenSq   = SmallSq.fillColor(Green)
val RedLine   = Image.line(LineLen, 0).strokeWidth(StrokeW).strokeColor(red)
val ReadyMark = Image.circle(Pts).strokeWidth(StrokeW).strokeColor(Blue)
val CheckMark = Image
  .path:
    OpenPath.empty
      .lineTo(0.3 * LineLen, -0.3 * LineLen)
      .lineTo(LineLen, 0.4 * LineLen)
  .strokeColor(green)
  .strokeWidth(StrokeW)

// derived shapes
val Wb        = WhiteSq beside BlackSq
val Bw        = BlackSq beside WhiteSq
val Wb8       = Wb beside Wb beside Wb beside Wb
val Bw8       = Bw beside Bw beside Bw beside Bw
val Quarter   = Wb8 above Bw8
val Half      = Quarter above Quarter
val Board     = Half above Half
val CrossMark = RedLine.rotate(45.degrees).on(RedLine.rotate(135.degrees))

// frame
val MainFrame = Frame.default
  .withSize(Width, Height)
  .withBackground(BgColor)
  .withTitle(Title)
