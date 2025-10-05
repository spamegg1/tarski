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

val evalPt  = ControlsConverter.toPointShiftX(controlGrid("Eval"))
val movePt  = ControlsConverter.toPointShiftX(controlGrid("Move"))
val addPt   = ControlsConverter.toPointShiftX(controlGrid("Add"))
val delPt   = ControlsConverter.toPointShiftX(controlGrid("Del"))
val smallPt = ControlsConverter.toPointShiftX(controlGrid("Small"))
val midPt   = ControlsConverter.toPointShiftX(controlGrid("Mid"))
val largePt = ControlsConverter.toPointShiftX(controlGrid("Large"))
val bluePt  = ControlsConverter.toPoint(controlGrid("Blue"))
val greenPt = ControlsConverter.toPoint(controlGrid("Green"))
val grayPt  = ControlsConverter.toPoint(controlGrid("Gray"))
val triPt   = ControlsConverter.toPoint(controlGrid("Tri"))
val squPt   = ControlsConverter.toPoint(controlGrid("Squ"))
val cirPt   = ControlsConverter.toPoint(controlGrid("Cir"))
val blockPt = ControlsConverter.toPointShiftY(controlGrid("Block"))
