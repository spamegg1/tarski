package tarski
package testing

import model.*

/** Data for [[GameHandler2Test]]. */
object GameHandler2TestData:
  import Shape.*, Sizes.*, Tone.*, Select.*, controller.GameHandler
  import gapt.expr.stringInterpolationForExpressions

  val p0 = (2, 2)
  val p1 = (2, 4)
  val pa = (3, 3)
  val b0 = Block(Sml, Tri, Blu)
  val b1 = Block(Sml, Cir, Red)
  val ba = Block(Sml, Sqr, Lim, "a")
  val f  = fof"∀x (Big(x) ↔ x = a)"

  val grid: Grid = Map(p0 -> b0, p1 -> b1, pa -> ba)

  val step0  = Step(f)       // initial step
  val steps0 = List(step0)
  val game   = Game(f, grid) // starting game

  // Correct results
  // After committing to True
  val playA011 = Play(f, Some(true), None, None)
  val msgsA011 = List(
    "You believe every object [x] satisfies:",
    "Big(x) ↔ x = a",
    "I choose a as my counterexample"
  )
  val stepA011  = Step(playA011, msgsA011, Off)
  val stepsA011 = stepA011 :: steps0
  val gameA011  = Game(stepA011, steps0, game.board)

  // True + OK
  val fB113    = fof"Big(a) ↔ a = a"
  val playB113 = Play(fB113, Some(true), None, None)
  val msgsB113 = List(
    "Big(a) ↔ a = a",
    "can be written as:",
    "Big(a) → a = a ∧ a = a → Big(a)",
    "You believe both are true:",
    "Big(a) → a = a and a = a → Big(a)",
    "I choose a = a → Big(a) as false."
  )
  val stepB113  = Step(playB113, msgsB113, Off)
  val stepsB113 = stepB113 :: stepsA011
  val gameB113  = Game(stepB113, stepsA011, game.board)

  // True + OK + OK
  val fC113     = fof"a = a → Big(a)"
  val playC113  = Play(fC113, Some(true), None, None)
  val msgsC113  = List("a = a → Big(a)", "can be written as:", "a != a ∨ Big(a)")
  val stepC113  = Step(playC113, msgsC113, Off)
  val stepsC113 = stepC113 :: stepsB113
  val gameC113  = Game(stepC113, stepsB113, game.board)

  // True + OK + OK + OK
  val fE113    = fof"a != a ∨ Big(a)"
  val playE113 = Play(fE113, Some(true), Some(fof"a != a"), Some(fof"Big(a)"))
  val msgsE113 = List(
    "You believe one of these is true:",
    "a != a or Big(a)",
    "Choose a true formula above."
  )
  val stepE113  = Step(playE113, msgsE113, Off)
  val stepsE113 = stepE113 :: stepsC113
  val gameE113  = Game(stepE113, stepsC113, game.board)

  // True + OK + OK + OK + Right
  val fF105     = fof"Big(a)"
  val playF105  = Play(fF105, Some(true), None, None)
  val msgsF105  = List("You lose.", "Big(a) is false in this world.")
  val stepF105  = Step(playF105, msgsF105, Off)
  val stepsF105 = stepF105 :: stepsE113
  val gameF105  = Game(stepF105, stepsE113, game.board)

  // After committing to False
  val playA111 = Play(f, Some(false), None, None)
  val msgsA111 = List(
    "You believe some object [x] falsifies:",
    "Big(x) ↔ x = a",
    "Click on a block, then click OK"
  )
  val stepA111  = Step(playA111, msgsA111, Wait)
  val stepsA111 = stepA111 :: steps0
  val gameA111  = Game(stepA111, steps0, game.board)

  // False + pa
  val stepBpa  = Step(playA111, msgsA111, On(pa))
  val stepsBpa = stepBpa :: steps0
  val gameBpa  = Game(stepBpa, steps0, game.board)

  // False + pa + OK
  val fD113    = fof"Big(a) ↔ a = a"
  val playD113 = Play(fD113, Some(false), Some(fof"Big(a) → a = a"), Some(fof"a = a → Big(a)"))
  val msgsD113 = List(
    "Big(a) ↔ a = a",
    "can be written as:",
    "Big(a) → a = a ∧ a = a → Big(a)",
    "You believe one of these is false:",
    "Big(a) → a = a or a = a → Big(a)",
    "Choose a false formula above."
  )
  val stepD113  = Step(playD113, msgsD113, Off)
  val stepsD113 = stepD113 :: stepsBpa
  val gameD113  = Game(stepD113, stepsBpa, game.board)

  // False + pa + OK + Right
  val fE105     = fof"a = a → Big(a)"
  val playE105  = Play(fE105, Some(false), None, None)
  val msgsE105  = List("a = a → Big(a)", "can be written as:", "a != a ∨ Big(a)")
  val stepE105  = Step(playE105, msgsE105, Off)
  val stepsE105 = stepE105 :: stepsD113
  val gameE105  = Game(stepE105, stepsD113, game.board)

  // False + pa + OK + Right + OK
  val fF113    = fof"a != a ∨ Big(a)"
  val playF113 = Play(fF113, Some(false), None, None)
  val msgsF113 = List(
    "You believe both are false:",
    "a != a and Big(a)",
    "I choose Big(a) as true."
  )
  val stepF113  = Step(playF113, msgsF113, Off)
  val stepsF113 = stepF113 :: stepsE105
  val gameF113  = Game(stepF113, stepsE105, game.board)

  // False + pa + OK + Right + OK + OK
  val fH113    = fof"Big(a)"
  val playH113 = Play(fH113, Some(false), None, None)
  val msgsH113 = List("You win!", "Big(a) is false in this world.")
  val stepH113 = Step(playH113, msgsH113, Off)
  val gameH113 = Game(stepH113, stepsF113, game.board)

  // Games obtained from the handler
  val a011 = GameHandler.controls((0, 11), game) // True
  val b113 = GameHandler.controls((1, 13), a011) // True + OK
  val c113 = GameHandler.controls((1, 13), b113) // True + OK + OK
  val e113 = GameHandler.controls((1, 13), c113) // True + OK + OK + OK
  val f105 = GameHandler.controls((1, 5), e113)  // True + OK + OK + OK + Right
  val a111 = GameHandler.controls((1, 11), game) // False
  val bpa  = GameHandler.boardPos(pa, a111)      // False + pa
  val d113 = GameHandler.controls((1, 13), bpa)  // False + pa + OK
  val e105 = GameHandler.controls((1, 5), d113)  // False + pa + OK + Right
  val f113 = GameHandler.controls((1, 13), e105) // False + pa + OK + Right + OK
  val h113 = GameHandler.controls((1, 13), f113) // False + pa + OK + Right + OK + OK
