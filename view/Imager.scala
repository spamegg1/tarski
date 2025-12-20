package tarski
package view

/** Converts every type of object to an image. */
object Imager:
  /** Type alias for all the types that can be converted to an image. */
  private type Obj = Block | FOLFormula | Result

  /** Converts an [[Obj]] to a [[doodle.image.Image]].
    *
    * @param o
    *   An instance of [[Block]], [[FOLFormula]] or [[Result]].
    * @param c
    *   A given instance of [[Constants]], needed for the fonts.
    * @return
    *   An image of the object.
    */
  def apply(o: Obj)(using c: Constants): Image =
    o match
      case b: Block =>
        val shapeImg =
          b.shape match
            case Shape.Tri => Image.triangle(b.size, b.size).fillColor(b.tone)
            case Shape.Sqr => Image.square(b.size).fillColor(b.tone)
            case Shape.Cir => Image.circle(b.size).fillColor(b.tone)
        Text(b.label).font(c.TheFont).on(shapeImg)
      case f: FOLFormula => colorText(f.toUntypedString)
      case r: Result     =>
        r match
          case Result.Ready   => Text(" ?").font(c.BoldFont).strokeColor(Color.blue)
          case Result.Valid   => Text(" T").font(c.BoldFont).strokeColor(Color.green)
          case Result.Invalid => Text(" F").font(c.BoldFont).strokeColor(Color.red)
          case Result.Error   => Text("Err").font(c.BoldFont).strokeColor(Color.darkRed)
  end apply

  /** Alternate imaging method for possibly missing objects. For example, [[Controls]] does not display a block unless
    * all three attributes are set, so an empty image has to be displayed.
    *
    * @param opt
    *   An optional [[Obj]].
    * @return
    *   The image of the object, or an empty image if the object is missing.
    */
  def apply(opt: Option[Obj])(using Constants): Image = opt match
    case None      => Image.empty
    case Some(obj) => Imager(obj)

  /** Adds a small amount of syntax highlighing to logical connectives in formulas.
    *
    * @param formula
    *   The (untyped) string representation of a formula.
    * @param c
    *   A given instance of [[Constants]], needed for the fonts.
    * @return
    *   An image of the input string with colors for logical connectives.
    */
  def colorText(formula: String)(using c: Constants): Image = formula
    .foldLeft(Image.empty): (img, char) =>
      img.beside:
        Text(char.toString)
          .font(c.TheFont)
          .strokeColor(char.toColor)

  extension (c: Char)
    /** Converts a character for a logical symbol to a color for syntax highlighing.
      *
      * @return
      *   A [[Color]].
      */
    def toColor: Color = c match
      case '¬' => Color.red
      case '∧' => Color.blue
      case '∨' => Color.green
      case '→' => Color.brown
      case '↔' => Color.brown
      case _   => Color.black
