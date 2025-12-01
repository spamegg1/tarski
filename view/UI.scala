package tarski
package view

/** Contains a mapping between UI button names and their grid positions. Used by the companion class. */
object UI:
  val grid = Map[String, Pos](
    "Eval"  -> (0, 0),
    "Add"   -> (0, 2),
    "a"     -> (0, 4),
    "b"     -> (0, 5),
    "c"     -> (0, 6),
    "d"     -> (0, 7),
    "e"     -> (0, 8),
    "f"     -> (0, 9),
    "Blue"  -> (0, 10),
    "Green" -> (0, 11),
    "Gray"  -> (0, 12),
    "Block" -> (0, 14),
    "Move"  -> (1, 0),
    "Del"   -> (1, 2),
    "Small" -> (1, 4),
    "Mid"   -> (1, 6),
    "Large" -> (1, 8),
    "Tri"   -> (1, 10),
    "Squ"   -> (1, 11),
    "Cir"   -> (1, 12)
  )

/** Calculates the Cartesian points for all the UI buttons.
  *
  * @param _
  *   A given instance of [[Constants]], needed for [[Converter.ui]].
  */
class UI(using Constants):
  val evalPt  = Converter.ui.toPointX(UI.grid("Eval"))
  val movePt  = Converter.ui.toPointX(UI.grid("Move"))
  val addPt   = Converter.ui.toPointX(UI.grid("Add"))
  val delPt   = Converter.ui.toPointX(UI.grid("Del"))
  val smallPt = Converter.ui.toPointX(UI.grid("Small"))
  val midPt   = Converter.ui.toPointX(UI.grid("Mid"))
  val largePt = Converter.ui.toPointX(UI.grid("Large"))
  val bluePt  = Converter.ui.toPoint(UI.grid("Blue"))
  val greenPt = Converter.ui.toPoint(UI.grid("Green"))
  val grayPt  = Converter.ui.toPoint(UI.grid("Gray"))
  val triPt   = Converter.ui.toPoint(UI.grid("Tri"))
  val squPt   = Converter.ui.toPoint(UI.grid("Squ"))
  val cirPt   = Converter.ui.toPoint(UI.grid("Cir"))
  val blockPt = Converter.ui.toPointY(UI.grid("Block"))
