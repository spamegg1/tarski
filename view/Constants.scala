package tarski

// pure constants
val FONTSIZE = 24
val FONT = Font.defaultSerif.bold.size(FontSize.points(FONTSIZE))
val SIZE = 100.0
val BGCOLOR = white
val TITLE = "Tarski's World"
val TICKRATE = FiniteDuration(50L, "milliseconds")

// derived constants
val HEIGHT = SIZE * 8
val WIDTH = HEIGHT * 2
val SMALL = 0.3 * SIZE
val MEDIUM = 0.6 * SIZE
val LARGE = 0.9 * SIZE

// basic shapes
val SQ = Image.square(SIZE)
val WHITE = SQ.fillColor(white)
val BLACK = SQ.fillColor(black)
val WB = WHITE beside BLACK
val BW = BLACK beside WHITE

// derived shapes
val LINE1 = WB beside WB beside WB beside WB
val LINE2 = BW beside BW beside BW beside BW
val QUARTER = LINE1 above LINE2
val HALF = QUARTER above QUARTER
val BOARD = HALF above HALF

// frame
val FRAME = Frame.default
  .withSize(WIDTH, HEIGHT)
  .withBackground(BGCOLOR)
  .withTitle(TITLE)
