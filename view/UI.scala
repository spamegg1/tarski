package tarski
package view

val UIGrid = Map[String, Pos](
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

object UI

extension (uio: UI.type)(using Constants)
  def evalPt  = Converter.ui.toPointX(UIGrid("Eval"))
  def movePt  = Converter.ui.toPointX(UIGrid("Move"))
  def addPt   = Converter.ui.toPointX(UIGrid("Add"))
  def delPt   = Converter.ui.toPointX(UIGrid("Del"))
  def smallPt = Converter.ui.toPointX(UIGrid("Small"))
  def midPt   = Converter.ui.toPointX(UIGrid("Mid"))
  def largePt = Converter.ui.toPointX(UIGrid("Large"))
  def bluePt  = Converter.ui.toPoint(UIGrid("Blue"))
  def greenPt = Converter.ui.toPoint(UIGrid("Green"))
  def grayPt  = Converter.ui.toPoint(UIGrid("Gray"))
  def triPt   = Converter.ui.toPoint(UIGrid("Tri"))
  def squPt   = Converter.ui.toPoint(UIGrid("Squ"))
  def cirPt   = Converter.ui.toPoint(UIGrid("Cir"))
  def blockPt = Converter.ui.toPointY(UIGrid("Block"))
