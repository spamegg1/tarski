package tarski

// pure constants
val FontSz   = 30
val TheFont  = Font.defaultSansSerif.bold.size(FontSize.points(FontSz))
val Size     = 100.0
val BgColor  = white
val Blue     = deepSkyBlue
val Gray     = lightGray
val Black    = black
val Title    = "Tarski's World"
val TickRate = FiniteDuration(50L, "milliseconds")

// derived constants
val Height = Size * 8
val Width  = Size * 16
val Small  = Size * 0.4
val Medium = Size * 0.7
val Large  = Size * 0.95

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
val frame = Frame.default
  .withSize(Width, Height)
  .withBackground(BgColor)
  .withTitle(Title)
