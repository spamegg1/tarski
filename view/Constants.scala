package tarski

// pure constants
val FontSz   = 30
val TheFont  = Font.defaultSansSerif.bold.size(FontSize.points(FontSz))
val Size     = 100.0
val BgColor  = white
val Title    = "Tarski's World"
val TickRate = FiniteDuration(50L, "milliseconds")

// derived constants
val Height = Size * 8
val Width  = Size * 16
val Small  = Size * 0.4
val Medium = Size * 0.7
val Large  = Size * 0.95

// basic shapes
val Square  = Image.square(Size)
val WhiteSq = Square.fillColor(white)
val BlackSq = Square.fillColor(black)
val Wb      = WhiteSq beside BlackSq
val Bw      = BlackSq beside WhiteSq

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
