package tarski
package constants

/** Contains type aliases [[Dims]] and [[GridSize]] used in [[controller.Converter]], and all the pure constants that do
  * not depend on the size of the user interface.
  */
object Constants:
  /** Type alias for the dimensions of a square on the board. Used in [[controller.Converter]]. */
  type Dims = (h: Double, w: Double)

  /** Type alias for the number of rows and columns of various grids. Used in [[controller.Converter]]. */
  type GridSize = (rows: Int, cols: Int)

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

  /** Tick rate passed to [[doodle.reactor.Reactor]] as an argument. */
  val TickRate = FiniteDuration(1000, "ms")

  /** Number of rows on the chess board. Must be an even positive integer. */
  private val BoardRows = 8

  /** Number of columns on the chess board. Must be an even positive integer. */
  private val BoardCols = 8

  /** Number of rows of the user interface controls. */
  private val UIRows = 2

  /** Number of columns of the user interface controls. */
  private val UICols = 16

  /** Tolerance value used in equality checks for Double values. */
  val Epsilon = 0.0001

  /** Default numbers of rows and columns of the board. Used in [[controller.Converter]]. */
  val BoardSize = (rows = BoardRows, cols = BoardCols)

  /** Default numbers of rows and columns of the user interface controls. Used in [[controller.Converter]]. */
  val UISize = (rows = UIRows, cols = UICols)

/** Contains all the constants that depend on the size of the user interface, the board image, and the frame in which
  * the interface is displayed and run.
  *
  * This class is instantiated in [[main.runWorld]] depending on its `scaleFactor` parameter that the user provides to
  * scale the interface. Then it is used implicitly by all the other modules.
  *
  * @param size
  *   defines the side length of a block on the board. Default is [[Constants.DefaultSize]]: 100.0. The dimensions of
  *   the user interface is 16 x 8, multiplied by size, in pixels (so, the default is 1600 x 800).
  */
case class Constants(size: Double):
  /** Stroke width for color and text. */
  private val StrokeW = size * 0.08

  /** Height of the interface, based on [[size]] and number of rows on the board. */
  private val Height = size * Constants.BoardSize.rows

  /** Width of the interface, based on [[size]] and number of columns on the board. */
  private val Width = size * 2 * Constants.BoardSize.cols

  /** Used to scale the font size according to interface dimensions. */
  private val Pts = Width * 0.015625

  /** Size of small blocks, used in the enum [[model.Sizes]]. */
  val Small = size * 0.4

  /** Size of mid-sized blocks, used in the enum [[model.Sizes]]. */
  val Mid = size * 0.7

  /** Size of large blocks, used in the enum [[model.Sizes]]. */
  val Large = size * 0.95

  /** A smaller stroke width for some color boxes. */
  val SmallStroke = StrokeW * 0.25

  /** The right half of the interface is divided into two parts: UI controls and formulas. This value determines the
    * line between them. Used by [[controller.React.click]].
    */
  val UIBottom = Height / 2.0 - size

  /** The origin of the chess board that holds the blocks. Used by [[controller.React.click]] */
  val BoardOrigin = Point(-Width * 0.25, 0)

  /** The origin of the UI control buttons on the top right of the interface. Used by [[controller.React.click]] */
  val UIOrigin = Point(Width * 0.25, Height / 2.0 - size / 2.0)

  /** The origin of the formulas display on the right half of the interface. Used by [[controller.React.click]] */
  val FormulaOrigin = Point(Width * 0.25, -size / 2.0)

  /** Default font. */
  val TheFont = Font(FontFamily.named("DejaVu Sans"), FontStyle.normal, FontWeight.normal, FontSize.points(Pts.toInt))

  /** Dimensions of the chess board that holds the blocks. */
  val BoardDims = (h = Height, w = Width * 0.5)

  /** Dimensions of the UI controls on the top right of the interface. */
  val UIDims = (h = size, w = Width * 0.5)

  /** Default square used on the chess board that holds the blocks, uncolored. */
  private val Sqr = Image.square(size)

  /** Default white square used on the chess board. */
  private val WhiteSq = Sqr.fillColor(white)

  /** Default black square used on the chess board. */
  private val BlackSq = Sqr.fillColor(black)

  /** Builder method for the rows and columns of the chess board.
    *
    * @param builder
    *   function parameter that combines two images, e.g. beside, above.
    * @param count
    *   number of rows or columns. Assumes it is an even positive integer.
    * @param img1
    *   first image to be combined
    * @param img2
    *   first image to be combined
    * @return
    *   the amalgamated image.
    */
  private def build(builder: (Image, Image) => Image)(count: Int)(img1: Image, img2: Image) =
    require(count % 2 == 0)
    require(0 < count)
    (0 until count / 2)
      .foldLeft[Image](Image.empty): (line, _) =>
        builder(line, builder(img1, img2))

  /** A row of alternating squares: white-black-white-black... */
  private val Wb = build(_.beside(_))(Constants.BoardSize.cols)(WhiteSq, BlackSq)

  /** A row of alternating squares: black-white-black-white... */
  private val Bw = build(_.beside(_))(Constants.BoardSize.cols)(BlackSq, WhiteSq)

  /** The board image in full. */
  val Board = build(_.above(_))(Constants.BoardSize.rows)(Wb, Bw)

  /** The frame in which [[doodle.reactor.Reactor]] runs the [[main.runWorld]] method. */
  val MainFrame = Frame.default
    .withSize(Width, Height)
    .withBackground(Constants.BgColor)
    .withTitle(Constants.Title)
    .withCenterAtOrigin
