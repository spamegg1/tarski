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

extension (ui: UI.type)(using Constants)
  def evalPt  = Converter.control.toPointShiftX(UIGrid("Eval"))
  def movePt  = Converter.control.toPointShiftX(UIGrid("Move"))
  def addPt   = Converter.control.toPointShiftX(UIGrid("Add"))
  def delPt   = Converter.control.toPointShiftX(UIGrid("Del"))
  def smallPt = Converter.control.toPointShiftX(UIGrid("Small"))
  def midPt   = Converter.control.toPointShiftX(UIGrid("Mid"))
  def largePt = Converter.control.toPointShiftX(UIGrid("Large"))
  def bluePt  = Converter.control.toPoint(UIGrid("Blue"))
  def greenPt = Converter.control.toPoint(UIGrid("Green"))
  def grayPt  = Converter.control.toPoint(UIGrid("Gray"))
  def triPt   = Converter.control.toPoint(UIGrid("Tri"))
  def squPt   = Converter.control.toPoint(UIGrid("Squ"))
  def cirPt   = Converter.control.toPoint(UIGrid("Cir"))
  def blockPt = Converter.control.toPointShiftY(UIGrid("Block"))
