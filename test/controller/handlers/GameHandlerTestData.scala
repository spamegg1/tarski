package tarski
package testing

import model.*

/** Data for [[GameHandlerTest]]. */
object GameHandlerTestData:
  import Shape.*, Sizes.*, Tone.*, Select.*, controller.GameHandler
  import gapt.expr.stringInterpolationForExpressions

  val ba         = Block(Sml, Tri, Lim, "a")
  val b0         = Block(Mid, Sqr, Blu)
  val pa         = (row = 1, col = 2)
  val p0         = (row = 6, col = 3)
  val p1         = (row = 4, col = 4)
  val grid: Grid = Map(pa -> ba, p0 -> b0)
  val f          = fof"∀x ∃y (Mor(x, y) ∨ Abv(y, x))"
  val step0      = Step(f)       // initial step
  val game       = Game(f, grid) // starting game

  // Correct results
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
  // True + any other button except OK = gameA011
  // False + Back = game

  // False + pa
  val stepCpa = Step(playA111, msgsA111, On(pa))
  val gameCpa = Game(stepCpa, List(step0), game.board)

  // False + p0
  val stepCp0 = Step(playA111, msgsA111, On(p0))
  val gameCp0 = Game(stepCp0, List(step0), game.board)

  // False + p1 = gameA111

  // True + OK + pa
  val stepDpa = Step(playB113, msgsB113, On(pa))
  val gameDpa = Game(stepDpa, List(stepA011, step0), game.board)

  // True + OK + p0
  val stepDp0 = Step(playB113, msgsB113, On(p0))
  val gameDp0 = Game(stepDp0, List(stepA011, step0), game.board)

  // True + OK + p1 = gameB113
  // True + OK + Back = gameA011
  // True + OK + any other button = gameB113

  // False + pa + OK
  val playE113 = Play(fof"∃y (Mor(a, y) ∨ Abv(y, a))", Some(false), None, None)
  val msgsE113 = List(
    "You believe no object [y] satisfies:",
    "Mor(a, y) ∨ Abv(y, a)",
    "I choose a as my example"
  )
  val stepE113 = Step(playE113, msgsE113, Off)
  val gameE113 = Game(stepE113, List(stepCpa, step0), game.board)

  // False + pa + Back = game
  // False + pa + pa = gameA111
  // False + pa + p1 = gameCpa
  // False + pa + p0
  val stepEp0 = Step(playA111, msgsA111, On(p0))
  val gameEp0 = Game(stepEp0, List(step0), game.board)

  // False + pa + any other button = gameCpa

  // True + OK + pa + pa
  val stepFpa = Step(playB113, msgsB113, Wait)
  val gameFpa = Game(stepFpa, List(stepA011, step0), game.board)

  // True + OK + pa + p0
  val stepFp0 = Step(playB113, msgsB113, On(p0))
  val gameFp0 = Game(stepFp0, List(stepA011, step0), game.board)

  // True + OK + pa + p1
  val stepFp1 = Step(playB113, msgsB113, Wait)
  val gameFp1 = Game(stepFp1, List(stepA011, step0), game.board)

  // True + OK + pa + OK
  val msgsF113 = List(
    "You believe one of these is true:",
    "Mor(a, a) or Abv(a, a)",
    "Choose a true formula above."
  )
  val fF113    = fof"Mor(a, a) ∨ Abv(a, a)"
  val playF113 = Play(fF113, Some(true), Some(fof"Mor(a, a)"), Some(fof"Abv(a, a)"))
  val stepF113 = Step(playF113, msgsF113, Off)
  val gameF113 = Game(stepF113, List(stepDpa, stepA011, step0), game.board)

  // True + OK + pa + OK + Left
  val msgsG005 = List("You lose.", "Mor(a, a) is false in this world.")
  val fG005    = fof"Mor(a, a)"
  val playG005 = Play(fG005, Some(true), None, None)
  val stepG005 = Step(playG005, msgsG005, Off)
  val gameG005 = Game(stepG005, List(stepF113, stepDpa, stepA011, step0), game.board)

  // True + OK + pa + OK + Right
  val msgsG105 = List("You lose.", "Abv(a, a) is false in this world.")
  val fG105    = fof"Abv(a, a)"
  val playG105 = Play(fG105, Some(true), None, None)
  val stepG105 = Step(playG105, msgsG105, Off)
  val gameG105 = Game(stepG105, List(stepF113, stepDpa, stepA011, step0), game.board)

  // After game over at gameG005 + any button except Back = gameG005

  // False + pa + OK + OK
  val playH113 = Play(fof"Mor(a, a) ∨ Abv(a, a)", Some(false), None, None)
  val msgsH113 = List(
    "You believe both are false:",
    "Mor(a, a) and Abv(a, a)",
    "I choose Abv(a, a) as true."
  )
  val stepH113 = Step(playH113, msgsH113, Off)
  val gameH113 = Game(stepH113, List(stepE113, stepCpa, step0), game.board)

  // False + pa + OK + OK + OK
  val playI113 = Play(fof"Abv(a, a)", Some(false), None, None)
  val msgsI113 = List("You win!", "Abv(a, a) is false in this world.")
  val stepI113 = Step(playI113, msgsI113, Off)
  val gameI113 = Game(stepI113, List(stepH113, stepE113, stepCpa, step0), game.board)

  // After game over at gameI113 + any button except Back = gameI113

  // Games obtained from the handler
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
  val b113 = GameHandler.controls((1, 13), a011) // True + OK (move forward)
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
  val cpa  = GameHandler.boardPos(pa, a111)      // False + (1, 2) (move forward)
  val cp0  = GameHandler.boardPos(p0, a111)      // False + (6, 3)
  val cp1  = GameHandler.boardPos(p1, a111)      // False + (4, 4)

  // After clicking True + OK:
  val d011 = GameHandler.controls((0, 11), b113) // True + OK + True
  val d111 = GameHandler.controls((1, 11), b113) // True + OK + False
  val d005 = GameHandler.controls((0, 5), b113)  // True + OK + Left
  val d105 = GameHandler.controls((1, 5), b113)  // True + OK + Right
  val d013 = GameHandler.controls((0, 13), b113) // True + OK + Back
  val d113 = GameHandler.controls((1, 13), b113) // True + OK + OK
  val d014 = GameHandler.controls((0, 14), b113) // True + OK + Display
  val dpa  = GameHandler.boardPos(pa, b113)      // True + OK + (1, 2) (move forward)
  val dp0  = GameHandler.boardPos(p0, b113)      // True + OK + (6, 3)
  val dp1  = GameHandler.boardPos(p1, b113)      // True + OK + (4, 4)

  // After clicking False + (1, 2):
  val e011 = GameHandler.controls((0, 11), cpa) // False + (1, 2) + True
  val e111 = GameHandler.controls((1, 11), cpa) // False + (1, 2) + False
  val e005 = GameHandler.controls((0, 5), cpa)  // False + (1, 2) + Left
  val e105 = GameHandler.controls((1, 5), cpa)  // False + (1, 2) + Right
  val e013 = GameHandler.controls((0, 13), cpa) // False + (1, 2) + Back
  val e113 = GameHandler.controls((1, 13), cpa) // False + (1, 2) + OK (move forward)
  val e014 = GameHandler.controls((0, 14), cpa) // False + (1, 2) + Display
  val epa  = GameHandler.boardPos(pa, cpa)      // False + (1, 2) + (1, 2)
  val ep0  = GameHandler.boardPos(p0, cpa)      // False + (1, 2) + (6, 3)
  val ep1  = GameHandler.boardPos(p1, cpa)      // False + (1, 2) + (4, 4)

  // After clicking True + OK + pa:
  val fpa  = GameHandler.boardPos(pa, dpa)      // True + OK + pa + pa
  val fp0  = GameHandler.boardPos(p0, dpa)      // True + OK + pa + p0
  val fp1  = GameHandler.boardPos(p1, dpa)      // True + OK + pa + p1
  val f113 = GameHandler.controls((1, 13), dpa) // True + OK + pa + OK
  val g005 = GameHandler.controls((0, 5), f113) // True + OK + pa + OK + Left
  val g105 = GameHandler.controls((1, 5), f113) // True + OK + pa + OK + Right

  // After clicking False + (1, 2) + OK
  val h113 = GameHandler.controls((1, 13), e113) // False + (1, 2) + OK + OK
  val i113 = GameHandler.controls((1, 13), h113) // False + (1, 2) + OK + OK + OK

  // After game over:
  val j011 = GameHandler.controls((0, 11), g005) // True + OK + pa + OK + Left + True
  val j111 = GameHandler.controls((1, 11), g005) // True + OK + pa + OK + Left + False
  val j005 = GameHandler.controls((0, 5), g005)  // True + OK + pa + OK + Left + Left
  val j105 = GameHandler.controls((1, 5), g005)  // True + OK + pa + OK + Left + Right
  val j113 = GameHandler.controls((1, 13), g005) // True + OK + pa + OK + Left + OK
  val j014 = GameHandler.controls((0, 14), g005) // True + OK + pa + OK + Left + Display
  val jpa  = GameHandler.boardPos(pa, g005)      // True + OK + pa + OK + Left + (1, 2)
  val jp0  = GameHandler.boardPos(p0, g005)      // True + OK + pa + OK + Left + (6, 3)
  val jp1  = GameHandler.boardPos(p1, g005)      // True + OK + pa + OK + Left + (4, 4)

  val k011 = GameHandler.controls((0, 11), i113) // False + (1, 2) + OK + OK + OK + True
  val k111 = GameHandler.controls((1, 11), i113) // False + (1, 2) + OK + OK + OK + False
  val k005 = GameHandler.controls((0, 5), i113)  // False + (1, 2) + OK + OK + OK + Left
  val k105 = GameHandler.controls((1, 5), i113)  // False + (1, 2) + OK + OK + OK + Right
  val k113 = GameHandler.controls((1, 13), i113) // False + (1, 2) + OK + OK + OK + OK
  val k014 = GameHandler.controls((0, 14), i113) // False + (1, 2) + OK + OK + OK + Display
  val kpa  = GameHandler.boardPos(pa, i113)      // False + (1, 2) + OK + OK + OK + (1, 2)
  val kp0  = GameHandler.boardPos(p0, i113)      // False + (1, 2) + OK + OK + OK + (6, 3)
  val kp1  = GameHandler.boardPos(p1, i113)      // False + (1, 2) + OK + OK + OK + (4, 4)
