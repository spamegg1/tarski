package tarski

case class FormulaBox(formula: FOLFormula, checkBox: CheckBox = Ready):
  def toImage = formula.toImage.beside(checkBox.toImage)
  def reset   = copy(checkBox = Ready)
  def update(using blocks: Blocks) =
    copy(checkBox = if eval(formula) then Valid else Invalid)

extension (f: FOLFormula) def toImage = Text(f.toString).font(TheFont)
