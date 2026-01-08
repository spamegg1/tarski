package tarski
package view

/** Contains a mapping between game UI button names and their grid positions. Used by the companion class. */
object GameUI:
  val grid = Map[String, Pos](
    "Left"  -> (0, 5),
    "True"  -> (0, 11),
    "Back"  -> (0, 13),
    "Block" -> (0, 14),
    "Right" -> (1, 5),
    "False" -> (1, 11),
    "OK"    -> (1, 13)
  )
end GameUI

/** Calculates the Cartesian points for all the game UI buttons.
  *
  * @param _
  *   A given instance of [[Constants]], needed for [[Converter]].
  */
class GameUI(using Constants):
  val leftPt  = Converter.ui.toPoint(GameUI.grid("Left"))
  val rightPt = Converter.ui.toPoint(GameUI.grid("Right"))
  val truePt  = Converter.ui.toPointX(GameUI.grid("True"))
  val falsePt = Converter.ui.toPointX(GameUI.grid("False"))
  val backPt  = Converter.ui.toPoint(GameUI.grid("Back"))
  val okPt    = Converter.ui.toPoint(GameUI.grid("OK"))
  val blockPt = Converter.ui.toPointXY(GameUI.grid("Block"))
