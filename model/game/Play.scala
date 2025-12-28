package tarski
package model

/** The state of the game.
  *
  * The game is played between two players: user and computer. User starts with a formula and a world, and an initial
  * commitment to whether they believe the formula is true or false in that world. Computer takes the opposite position
  * and tries to falsify user's claims.
  *
  * @param formula
  *   Current formula whose truth value we are trying to determine.
  * @param commitment
  *   User's belief as to whether the formula is true or false.
  * @param left
  *   A choice between two formulas (for example, when evaluating `And` or `Or`).
  * @param right
  *   A choice between two formulas (for example, when evaluating `And` or `Or`).
  */
case class Play(
    formula: FOLFormula,
    commitment: Option[Boolean] = None,
    left: Option[FOLFormula] = None,
    right: Option[FOLFormula] = None
):
  def setFormula(f: FOLFormula)              = copy(left = None, right = None, formula = f)
  def sub(c: Name, x: FOLVar, f: FOLFormula) = copy(formula = f.sub(x, c))

  def negate = (formula, commitment) match
    case (Neg(f), Some(commit)) => copy(formula = f, commitment = Some(!commit))
    case _                      => this

  // def chooseFormula(f: FOLFormula) = pos match
  //   case Off   => this // should not happen
  //   case Wait  => copy(formula = f, left = None, right = None, pos = Off)
  //   case On(_) => this // should not happen
