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
  *   A choice between two formulas (for example, when evaluating `And` or `Or`) to be displayed to the user.
  * @param right
  *   A choice between two formulas (for example, when evaluating `And` or `Or`) to be displayed to the user.
  */
case class Play(
    formula: FOLFormula,
    commitment: Option[Boolean] = None,
    left: Option[FOLFormula] = None,
    right: Option[FOLFormula] = None,
    pos: Option[Pos] = None
):
  /** Advances the state of play by setting a commitment (true or false).
    *
    * @param commit
    *   The commitment that the user chose.
    * @return
    *   New state of play updated with the given commitment.
    */
  def commitTo(commit: Boolean) = copy(commitment = Some(commit)).checkChoice

  /** Checks if we should wait for user input for the board position
    *
    * We wait for user input if the formula is a true existential, or a false universal.
    *
    * @return
    *   `true` if we have to wait for user input, `false` otherwise.
    */
  def checkWait = (commitment, formula) match
    case (Some(true), Ex(x, f))   => true
    case (Some(false), All(x, f)) => true
    case _                        => false

  /** Checks if we have to wait for user input for the formula selection, and sets up the two choices.
    *
    * We have to wait for user input if the formula is a true Or, or a false And.
    *
    * @return
    *   A copy of this `Play`, with `left` and `right` set accordingly if needed, unchanged otherwise.
    */
  def checkChoice = (commitment, formula) match
    case (Some(true), Or(a, b))   => copy(left = Some(a), right = Some(b))
    case (Some(false), And(a, b)) => copy(left = Some(a), right = Some(b))
    case _                        => this

  /** Advances the state of play when the user selects one of two formulas.
    *
    * @param f
    *   A first-order formula (coming from the user's choice between left and right).
    * @return
    *   New state of play where the formula is updated and left/right are both set to `None`.
    */
  def setFormula(f: FOLFormula) = copy(left = None, right = None, formula = f).checkChoice

  /** The formula to be displayed at bottom, depending on whether a choice is pending.
    *
    * @return
    *   The formula inside `right` if a choice is pending, `formula` otherwise.
    */
  val show = right match
    case None        => formula
    case Some(value) => value

  /** Advances the play to its next state when a formula is quantified. The quantifier can be eliminated by substituting
    * the name of a block in the world for the quantified variable.
    *
    * @param p
    *   The position of a block on the board.
    * @param c
    *   The name of a block to be substituted into the formula as a `FOLConst`.
    * @param x
    *   A first-order variable (which occurs in the quantifier of a formula in a previous state).
    * @param f
    *   A first-order formula (which is obtained by removing the quantifier of a formula in a previous state).
    * @return
    *   New state of play where the formula has its free occurrences of `x` replaced by the given name.
    */
  def sub(p: Pos, c: Name, x: FOLVar, f: FOLFormula) = copy(formula = f.sub(x, c), pos = Some(p)).checkChoice

  /** Advances the play to the next state when the formula is a negation.
    *
    * @return
    *   New state of play where commitment is reversed and negation is eliminated from the formula.
    */
  def negate = (formula, commitment) match
    case (Neg(f), Some(commit)) => copy(formula = f, commitment = Some(!commit)).checkChoice
    case _                      => this
