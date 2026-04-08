package tarski
package testing

import model.*

/** Data for [[WorldHandlerTest]]. */
object WorldHandlerTestData:
  import Shape.*, Status.*, Result.*, Sizes.*, Tone.*, controller.WorldHandler
  import gapt.expr.stringInterpolationForExpressions

  val b0 = Block(Sml, Cir, Blu)
  val b1 = Block(Mid, Tri, Lim)
  val b2 = Block(Big, Cir, Red)
  val p0 = (1, 2)
  val p1 = (3, 4)
  val p2 = (5, 6)
  val p3 = (4, 1)
  val f0 = fof"¬(∃x Big(x))"
  val f1 = fof"!x Cir(x)"
  val f2 = fof"a = b"
  val w0 = World.empty
    .addBlockAt(p0, b0)
    .addBlockAt(p1, b1)
    .addFormula(f0)
    .addFormula(f1)
    .addFormula(f2)
    .addNameToBlockAt(p0, "f")

  // For UI controls tests
  // No position selected
  val w000  = WorldHandler.uiButtons((0, 0), w0)     // Eval
  val w001  = WorldHandler.uiButtons((0, 1), w000)   // Eval
  val w100  = WorldHandler.uiButtons((1, 0), w001)   // Move
  val w101  = WorldHandler.uiButtons((1, 1), w100)   // Move
  val w101_ = w101.selectPos(p0)
  val w013  = WorldHandler.uiButtons((0, 13), w101_) // Left
  val w014  = WorldHandler.uiButtons((0, 14), w100)  // Block
  val w015  = WorldHandler.uiButtons((0, 15), w014)  // Block
  val w015_ = w015.selectPos(p1)
  val w113  = WorldHandler.uiButtons((1, 13), w015_) // Right
  val w114  = WorldHandler.uiButtons((1, 14), w015)  // Block
  val w115  = WorldHandler.uiButtons((1, 15), w114)  // Block
  val w002  = WorldHandler.uiButtons((0, 2), w115)   // Add
  val w003  = WorldHandler.uiButtons((0, 3), w115)   // Add
  val w004  = WorldHandler.uiButtons((0, 4), w003)   // a
  val w005  = WorldHandler.uiButtons((0, 5), w004)   // b
  val w006  = WorldHandler.uiButtons((0, 6), w005)   // c
  val w007  = WorldHandler.uiButtons((0, 7), w006)   // d
  val w008  = WorldHandler.uiButtons((0, 8), w007)   // e
  val w008_ = WorldHandler.uiButtons((0, 0), w008)   // Eval
  val w009  = WorldHandler.uiButtons((0, 9), w008_)  // f
  val w010  = WorldHandler.uiButtons((0, 10), w009)  // Blu
  val w011  = WorldHandler.uiButtons((0, 11), w010)  // Lim
  val w012  = WorldHandler.uiButtons((0, 12), w011)  // Coral
  val w102  = WorldHandler.uiButtons((1, 2), w012)   // Del
  val w103  = WorldHandler.uiButtons((1, 3), w102)   // Del
  val w104  = WorldHandler.uiButtons((1, 4), w103)   // Sml
  val w105  = WorldHandler.uiButtons((1, 5), w104)   // Sml
  val w106  = WorldHandler.uiButtons((1, 6), w105)   // Mid
  val w107  = WorldHandler.uiButtons((1, 7), w106)   // Mid
  val w108  = WorldHandler.uiButtons((1, 8), w107)   // Big
  val w109  = WorldHandler.uiButtons((1, 9), w108)   // Big
  val w110  = WorldHandler.uiButtons((1, 10), w109)  // Tri
  val w111  = WorldHandler.uiButtons((1, 11), w110)  // Sqr
  val w112  = WorldHandler.uiButtons((1, 12), w111)  // Cir

  // Selected position is empty
  val x     = w112.selectPos(p2)
  val x000  = WorldHandler.uiButtons((0, 0), x)     // Eval
  val x002  = WorldHandler.uiButtons((0, 2), x000)  // Add Coral Big Cir
  val x002_ = x002.unsetBlock.selectPos(p3)
  val x003  = WorldHandler.uiButtons((0, 3), x002_) // Add no block

  // Selected position has a block on it
  val y     = x002.selectPos(p2)
  val y002  = WorldHandler.uiButtons((0, 2), y)      // Add
  val y002_ = WorldHandler.uiButtons((0, 0), y002)   // Eval
  val y004  = WorldHandler.uiButtons((0, 4), y002_)  // a
  val y004_ = WorldHandler.uiButtons((0, 0), y004)   // Eval
  val y010  = WorldHandler.uiButtons((0, 10), y004_) // Blu
  val y010_ = WorldHandler.uiButtons((0, 0), y010)   // Eval
  val y104  = WorldHandler.uiButtons((1, 4), y010_)  // Sml
  val y104_ = WorldHandler.uiButtons((0, 0), y104)   // Eval
  val y110  = WorldHandler.uiButtons((1, 10), y104_) // Tri
  val y110_ = WorldHandler.uiButtons((0, 0), y110)   // Eval
  val y102  = WorldHandler.uiButtons((1, 2), y110_)  // Del

  // For board controls tests
  val z0  = WorldHandler.boardPos(p0, w0) // no pos selected, clicked p0 has a block
  val z2  = WorldHandler.boardPos(p2, w0) // no pos selected, clicked p2 has no block
  val z00 = WorldHandler.boardPos(p0, z0) // selected p0 == clicked p0

  // Selected != clicked
  // Move enabled
  val z0e1  = z0.toggleMove                        // selected p0 has block b0
  val z0e11 = WorldHandler.boardPos(p1, z0e1)      // block b1 at clicked p1
  val z0e1_ = WorldHandler.uiButtons((0, 0), z0e1) // Eval
  val z0e12 = WorldHandler.boardPos(p2, z0e1_)     // no block at clicked p2
  val z0e2  = z0e1.selectPos(p2)                   // selected p2 has no block
  val z0e21 = WorldHandler.boardPos(p1, z0e2)      // block b1 at clicked p1
  val z0e23 = WorldHandler.boardPos(p3, z0e2)      // no block at clicked p3
  // Move disabled
  val z0d1  = z0                              // selected p0 has block b0
  val z0d11 = WorldHandler.boardPos(p1, z0d1) // block b1 at clicked p1
  val z0d12 = WorldHandler.boardPos(p2, z0d1) // no block at clicked p2
  val z0d2  = z0d1.selectPos(p2)              // selected p2 has no block
  val z0d21 = WorldHandler.boardPos(p1, z0d2) // block b1 at clicked p1
  val z0d23 = WorldHandler.boardPos(p3, z0d2) // no block at clicked p3
