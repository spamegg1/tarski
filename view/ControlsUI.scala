package tarski
package view

val controlGrid = Map[String, Pos](
  "Eval"   -> (0, 0),
  "Add"    -> (0, 2),
  "a"      -> (0, 4),
  "b"      -> (0, 5),
  "c"      -> (0, 6),
  "d"      -> (0, 7),
  "e"      -> (0, 8),
  "f"      -> (0, 9),
  "Blue"   -> (0, 10),
  "Green"  -> (0, 11),
  "Gray"   -> (0, 12),
  "Block"  -> (0, 14),
  "Move"   -> (1, 0),
  "Remove" -> (1, 2),
  "Small"  -> (1, 4),
  "Medium" -> (1, 6),
  "Large"  -> (1, 8),
  "Tri"    -> (1, 10),
  "Squ"    -> (1, 11),
  "Cir"    -> (1, 12)
)

val evalPt  = ControlsConverter.toPointShiftX(controlGrid("Eval"))
val movePt  = ControlsConverter.toPointShiftX(controlGrid("Move"))
val addPt   = ControlsConverter.toPointShiftX(controlGrid("Add"))
val remPt   = ControlsConverter.toPointShiftX(controlGrid("Remove"))
val smallPt = ControlsConverter.toPointShiftX(controlGrid("Small"))
val medPt   = ControlsConverter.toPointShiftX(controlGrid("Medium"))
val largePt = ControlsConverter.toPointShiftX(controlGrid("Large"))
val aPoint  = ControlsConverter.toPoint(controlGrid("a"))
val bPoint  = ControlsConverter.toPoint(controlGrid("b"))
val cPoint  = ControlsConverter.toPoint(controlGrid("c"))
val dPoint  = ControlsConverter.toPoint(controlGrid("d"))
val ePoint  = ControlsConverter.toPoint(controlGrid("e"))
val fPoint  = ControlsConverter.toPoint(controlGrid("f"))
val bluePt  = ControlsConverter.toPoint(controlGrid("Blue"))
val greenPt = ControlsConverter.toPoint(controlGrid("Green"))
val grayPt  = ControlsConverter.toPoint(controlGrid("Gray"))
val triPt   = ControlsConverter.toPoint(controlGrid("Tri"))
val squPt   = ControlsConverter.toPoint(controlGrid("Squ"))
val cirPt   = ControlsConverter.toPoint(controlGrid("Cir"))
