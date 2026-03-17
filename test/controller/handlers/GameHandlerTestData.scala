package tarski
package testing

object GameHandlerTestData:
  import Shape.*, Sizes.*, Tone.*, Select.*, Choice.*, Commit.*

  val ba         = Block(Sml, Tri, Lim, "a")
  val b0         = Block(Mid, Sqr, Blu)
  val pa         = (row = 1, col = 2)
  val p0         = (row = 6, col = 3)
  val p1         = (row = 4, col = 4)
  val grid: Grid = Map(pa -> ba, p0 -> b0)
  val f          = fof"∀x ∃y (Mor(x, y) ∨ Abv(y, x))"
  val step0      = Step(f)       // initial step
  val game       = Game(f, grid) // starting game

  // After committing to True
  val playA011 = Play(f, Some(true), None, None)
  val msgsA011 = List(
    "You believe every object [x] satisfies:",
    "∃y (Mor(x, y) ∨ Abv(y, x))",
    "I choose a as my counterexample"
  )
  val stepA011 = Step(playA011, msgsA011, Off)
  val gameA011 = Game(stepA011, List(step0), game.board)

  // After committing to False
  val playA111 = Play(f, Some(false), None, None)
  val msgsA111 = List(
    "You believe some object [x] falsifies:",
    "∃y (Mor(x, y) ∨ Abv(y, x))",
    "Click on a block, then click OK"
  )
  val stepA111 = Step(playA111, msgsA111, Wait)
  val gameA111 = Game(stepA111, List(step0), game.board)

  // True + OK
  val fB113    = fof"∃y (Mor(a, y) ∨ Abv(y, a))"
  val playB113 = Play(fB113, Some(true), None, None)
  val msgsB113 = List(
    "You believe some object [y] satisfies:",
    "Mor(a, y) ∨ Abv(y, a)",
    "Click on a block, then click OK"
  )
  val stepB113 = Step(playB113, msgsB113, Wait)
  val gameB113 = Game(stepB113, List(stepA011, step0), game.board)

  // True + Back = game
  // True + any other button except OK = game
  // False + Back = game

  // False + pa
  val stepCpa = Step(playA111, msgsA111, On(pa))
  val gameCpa = Game(stepCpa, List(step0), game.board)

  // False + p0
  val stepCp0 = Step(playA111, msgsA111, On(p0))
  val gameCp0 = Game(stepCp0, List(step0), game.board)

  // False + p1 = gameA111

  // Beginning of the game
  val a011 = GameHandler.controls((0, 11), game) // True
  val a111 = GameHandler.controls((1, 11), game) // False
  val a005 = GameHandler.controls((0, 5), game)  // Left
  val a105 = GameHandler.controls((1, 5), game)  // Right
  val a013 = GameHandler.controls((0, 13), game) // Back
  val a113 = GameHandler.controls((1, 13), game) // OK
  val a014 = GameHandler.controls((0, 14), game) // Display
  val apa  = GameHandler.boardPos(pa, game)      // (1, 2)
  val ap0  = GameHandler.boardPos(p0, game)      // (6, 3)
  val ap1  = GameHandler.boardPos(p1, game)      // (4, 4)

  // After clicking True:
  val b011 = GameHandler.controls((0, 11), a011) // True + True
  val b111 = GameHandler.controls((1, 11), a011) // True + False
  val b005 = GameHandler.controls((0, 5), a011)  // True + Left
  val b105 = GameHandler.controls((1, 5), a011)  // True + Right
  val b013 = GameHandler.controls((0, 13), a011) // True + Back
  val b113 = GameHandler.controls((1, 13), a011) // True + OK
  val b014 = GameHandler.controls((0, 14), a011) // True + Display
  val bpa  = GameHandler.boardPos(pa, a011)      // True + (1, 2)
  val bp0  = GameHandler.boardPos(p0, a011)      // True + (6, 3)
  val bp1  = GameHandler.boardPos(p1, a011)      // True + (4, 4)

  // After clicking False:
  val c011 = GameHandler.controls((0, 11), a111) // False + True
  val c111 = GameHandler.controls((1, 11), a111) // False + False
  val c005 = GameHandler.controls((0, 5), a111)  // False + Left
  val c105 = GameHandler.controls((1, 5), a111)  // False + Right
  val c013 = GameHandler.controls((0, 13), a111) // False + Back
  val c113 = GameHandler.controls((1, 13), a111) // False + OK
  val c014 = GameHandler.controls((0, 14), a111) // False + Display
  val cpa  = GameHandler.boardPos(pa, a111)      // False + (1, 2) on board
  val cp0  = GameHandler.boardPos(p0, a111)      // False + (6, 3) on board
  val cp1  = GameHandler.boardPos(p1, a111)      // False + (4, 4) on board
