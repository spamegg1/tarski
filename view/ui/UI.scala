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
    "Blu"   -> (0, 10),
    "Lim"   -> (0, 11),
    "Red"   -> (0, 12),
    "Left"  -> (0, 13),
    "Block" -> (0, 14),
    "Move"  -> (1, 0),
    "Del"   -> (1, 2),
    "Sml"   -> (1, 4),
    "Mid"   -> (1, 6),
    "Big"   -> (1, 8),
    "Tri"   -> (1, 10),
    "Sqr"   -> (1, 11),
    "Cir"   -> (1, 12),
    "Rgt"   -> (1, 13)
  )
end UI

/** Calculates the Cartesian points for all the UI buttons.
  *
  * @param _
  *   A given instance of [[Constants]], needed for [[Converter]].
  */
class UI(using Constants):
  val evalPt  = Converter.ui.toPointX(UI.grid("Eval"))
  val movePt  = Converter.ui.toPointX(UI.grid("Move"))
  val addPt   = Converter.ui.toPointX(UI.grid("Add"))
  val delPt   = Converter.ui.toPointX(UI.grid("Del"))
  val smallPt = Converter.ui.toPointX(UI.grid("Sml"))
  val midPt   = Converter.ui.toPointX(UI.grid("Mid"))
  val largePt = Converter.ui.toPointX(UI.grid("Big"))
  val bluePt  = Converter.ui.toPoint(UI.grid("Blu"))
  val greenPt = Converter.ui.toPoint(UI.grid("Lim"))
  val redPt   = Converter.ui.toPoint(UI.grid("Red"))
  val triPt   = Converter.ui.toPoint(UI.grid("Tri"))
  val sqrPt   = Converter.ui.toPoint(UI.grid("Sqr"))
  val cirPt   = Converter.ui.toPoint(UI.grid("Cir"))
  val leftPt  = Converter.ui.toPoint(UI.grid("Left"))
  val rightPt = Converter.ui.toPoint(UI.grid("Rgt"))
  val blockPt = Converter.ui.toPointXY(UI.grid("Block"))
