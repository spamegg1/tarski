package tarski
package view

case class FormulaBox(formula: FOLFormula, checkBox: CheckBox = Ready):
  def toImage                = formula.toImage.beside(checkBox.toImage)
  def reset                  = copy(checkBox = Ready)
  def update(value: Boolean) = copy(checkBox = if value then Valid else Invalid)

extension (f: FOLFormula) def toImage = Text(f.toString).font(TheFont)
