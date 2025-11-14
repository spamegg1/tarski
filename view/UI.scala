package tarski
package view

val controlGrid = Map[String, Pos](
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
  def evalPt  = Converter.control.toPointShiftX(controlGrid("Eval"))
  def movePt  = Converter.control.toPointShiftX(controlGrid("Move"))
  def addPt   = Converter.control.toPointShiftX(controlGrid("Add"))
  def delPt   = Converter.control.toPointShiftX(controlGrid("Del"))
  def smallPt = Converter.control.toPointShiftX(controlGrid("Small"))
  def midPt   = Converter.control.toPointShiftX(controlGrid("Mid"))
  def largePt = Converter.control.toPointShiftX(controlGrid("Large"))
  def bluePt  = Converter.control.toPoint(controlGrid("Blue"))
  def greenPt = Converter.control.toPoint(controlGrid("Green"))
  def grayPt  = Converter.control.toPoint(controlGrid("Gray"))
  def triPt   = Converter.control.toPoint(controlGrid("Tri"))
  def squPt   = Converter.control.toPoint(controlGrid("Squ"))
  def cirPt   = Converter.control.toPoint(controlGrid("Cir"))
  def blockPt = Converter.control.toPointShiftY(controlGrid("Block"))
