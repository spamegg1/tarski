package tarski

val WIDTH = 800
val HEIGHT = 1000
val SIZE = 100.0
val SMALL = 30.0
val MEDIUM = 60.0
val LARGE = 90.0
val BGCOLOR = skyBlue
val TITLE = "Tarski's World"
val TICKRATE = FiniteDuration(50L, "milliseconds")

val SQ = Image.square(SIZE)
val WHITE = SQ.fillColor(white)
val BLACK = SQ.fillColor(black)
val WB = WHITE beside BLACK
val BW = BLACK beside WHITE

val LINE1 = WB beside WB beside WB beside WB
val LINE2 = BW beside BW beside BW beside BW
val QUARTER = LINE1 above LINE2
val HALF = QUARTER above QUARTER
val BOARD = HALF above HALF

val FRAME = Frame.default
  .withSize(WIDTH, HEIGHT)
  .withBackground(BGCOLOR)
  .withTitle(TITLE)
  .withCenterAtOrigin
