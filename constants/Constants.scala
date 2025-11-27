package tarski
package constants

/** Contains type aliases [[Dims]] and [[GridSize]] used in [[controller]], and all the pure constants that do not
  * depend on the size of the user interface.
  */
object Constants:
  /** Type alias for the dimensions of a square on the board. Used in [[controller.Converter]]. */
  type Dims = (h: Double, w: Double)

  /** Type alias for the number of rows and columns of various grids. Used in [[controller.Converter]]. */
  type GridSize = (rows: Int, cols: Int)

  // pure constants

  /** The default size of one side of a square on the board. */
  val DefaultSize = 100.0

  /** The default background color used in the interface. */
  private val BgColor = white

  /** Renamed color value from [[doodle.core.Color]], used later in the enum [[model.Tone]]. */
  val BlueColor = deepSkyBlue

  /** Renamed color value from [[doodle.core.Color]], used later in the enum [[model.Tone]]. */
  val GrayColor = lightGray

  /** Renamed color value from [[doodle.core.Color]], used later in the enum [[model.Tone]]. */
  val GreenColor = yellowGreen

  /** The default title for the window. */
  private val Title = "Tarski's World"

  /** Tick rate used in [[doodle.reactor.Reactor]]. */
  val TickRate = FiniteDuration(1000, "ms")

  /** Number of rows on the chess board. */
  private val BoardRows = 8

  /** Number of columns on the chess board. */
  private val BoardCols = 8

  /** Number of rows of the user interface controls. */
  private val UIRows = 2

  /** Number of columns of the user interface controls. */
  private val UICols = 16

  /** Tolerance value used in equality checks for Double values. */
  val Epsilon = 0.0001

  /** Default numbers of rows and columns of the board. Used in [[controller]]. */
  val BoardSize = (rows = BoardRows, cols = BoardCols)

  /** Default numbers of rows and columns of the user interface controls. Used in [[controller]]. */
  val UISize = (rows = UIRows, cols = UICols)

/** Contains all the constants that depend on the size of the user interface, the board image, and the frame in which
  * the interface is displayed and run.
  *
  * This class is instantiated in [[main]] depending on the [[scaleFactor]] that the user provides to scale the
  * interface. Then it is used implicitly by all the other modules.
  *
  * @param size
  *   defines the side length of a block on the board. Default is [[DefaultSize]]: 100.0. The dimensions of the user
  *   interface is 16 x 8, multiplied by size, in pixels (so, the default is 1600 x 800).
  */
case class Constants(size: Double):
  // derived constants
  private val StrokeW = size * 0.08
  private val Pts     = size * 0.25
  private val Height  = size * 8.0
  private val Width   = Height * 2.0
  private val FontSz  = FontSize.points(Pts.toInt)

  val Small         = size * 0.4
  val Mid           = size * 0.7
  val Large         = size * 0.95
  val SmallStroke   = StrokeW * 0.25
  val UIBottom      = Height * 0.375
  val BoardOrigin   = Point(-Width * 0.25, 0)
  val UIOrigin      = Point(Width * 0.25, Height * 0.4375)
  val FormulaOrigin = Point(Width * 0.25, -Height * 0.0625)
  val TheFont       = Font.defaultSansSerif.size(FontSz)
  val BoardDims     = (h = Height, w = Width * 0.5)
  val UIDims        = (h = Height * 0.125, w = Width * 0.5)

  // basic shapes
  private val SmallSq = Image.square(Pts)
  private val Sqr     = Image.square(size)
  private val WhiteSq = Sqr.fillColor(white)
  private val BlackSq = Sqr.fillColor(black)

  // derived shapes
  private val Wb      = WhiteSq beside BlackSq
  private val Bw      = BlackSq beside WhiteSq
  private val Wb8     = Wb beside Wb beside Wb beside Wb
  private val Bw8     = Bw beside Bw beside Bw beside Bw
  private val Quarter = Wb8 above Bw8
  private val Half    = Quarter above Quarter

  val Board = Half above Half

  val MainFrame = Frame.default
    .withSize(Width, Height)
    .withBackground(Constants.BgColor)
    .withTitle(Constants.Title)
    .withCenterAtOrigin
