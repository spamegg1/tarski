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

def evalPt(using Constants)  = ControlsConverter.toPointShiftX(controlGrid("Eval"))
def movePt(using Constants)  = ControlsConverter.toPointShiftX(controlGrid("Move"))
def addPt(using Constants)   = ControlsConverter.toPointShiftX(controlGrid("Add"))
def delPt(using Constants)   = ControlsConverter.toPointShiftX(controlGrid("Del"))
def smallPt(using Constants) = ControlsConverter.toPointShiftX(controlGrid("Small"))
def midPt(using Constants)   = ControlsConverter.toPointShiftX(controlGrid("Mid"))
def largePt(using Constants) = ControlsConverter.toPointShiftX(controlGrid("Large"))
def bluePt(using Constants)  = ControlsConverter.toPoint(controlGrid("Blue"))
def greenPt(using Constants) = ControlsConverter.toPoint(controlGrid("Green"))
def grayPt(using Constants)  = ControlsConverter.toPoint(controlGrid("Gray"))
def triPt(using Constants)   = ControlsConverter.toPoint(controlGrid("Tri"))
def squPt(using Constants)   = ControlsConverter.toPoint(controlGrid("Squ"))
def cirPt(using Constants)   = ControlsConverter.toPoint(controlGrid("Cir"))
def blockPt(using Constants) = ControlsConverter.toPointShiftY(controlGrid("Block"))
