package tarski
package view

case class FormulaBox(formula: FOLFormula, result: Result = Ready):
  def reset                  = copy(result = Ready)
  def update(value: Boolean) = copy(result = if value then Valid else Invalid)
