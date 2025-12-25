package tarski
package model

/** The state of the game.
  *
  * The game is played between two players: user and computer. User starts with a formula and a world, and an initial
  * commitment to whether they believe the formula is true or false in that world. Computer takes the opposite position
  * and tries to falsify user's claims.
  *
  * @param board
  *   The board holding all the blocks of this world.
  * @param formula
  *   Current formula whose truth value we are trying to determine.
  * @param pos
  *   The state of the currently selected position on the board. It can be: `Off`, `Wait` or `On(p)`.
  * @param commitment
  *   User's belief as to whether the formula is true or false.
  * @param left
  *   A choice between two formulas (for example, when evaluating `And` or `Or`).
  * @param right
  *   A choice between two formulas (for example, when evaluating `And` or `Or`).
  */
case class Game(
    board: Board,
    formula: FOLFormula,
    pos: Select[Pos] = Off,
    commitment: Option[Boolean] = None,
    left: Option[FOLFormula] = None,
    right: Option[FOLFormula] = None
):
  def setPos(p: Pos) = board.grid.get(p) match
    case None    => copy(pos = Wait)
    case Some(_) => copy(pos = On(p))

  def subst(pos: Pos, x: FOLVar, f: FOLFormula) =
    val c = board.grid(pos).name
    copy(formula = f.sub(x, c), pos = Off)

  def negate(commit: Boolean, f: FOLFormula) = copy(formula = f, commitment = Some(!commit))

  def unsetPos = copy(pos = Wait)

  def chooseFormula(f: FOLFormula): Game = pos match
    case Off   => this // should not happen
    case Wait  => copy(formula = f, left = None, right = None, pos = Off)
    case On(_) => this // should not happen
