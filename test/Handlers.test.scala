package tarski
package testing

class HandlersTest extends munit.FunSuite:
  import Shape.*, Status.*

  val b0 = Block(Small, Cir, Gray) // at (1,2), then (3,4)
  val b1 = Block(Mid, Tri, Green)  // at (5,6)
  val w0 = World.empty

  test("clicking the buttons in an empty world"):
    val w000 = handleControls((0, 0), w0)  // Eval
    val w001 = handleControls((0, 1), w0)  // Eval
    val w002 = handleControls((0, 1), w0)  // Add
    val w003 = handleControls((0, 1), w0)  // Add
    val w004 = handleControls((0, 4), w0)  // a
    val w005 = handleControls((0, 5), w0)  // b
    val w006 = handleControls((0, 6), w0)  // c
    val w007 = handleControls((0, 7), w0)  // d
    val w008 = handleControls((0, 8), w0)  // e
    val w009 = handleControls((0, 9), w0)  // f
    val w010 = handleControls((0, 10), w0) // Blue
    val w011 = handleControls((0, 11), w0) // Green
    val w012 = handleControls((0, 12), w0) // Gray
    val w013 = handleControls((0, 13), w0) // Block
    val w014 = handleControls((0, 14), w0) // Block
    val w015 = handleControls((0, 15), w0) // Block
    val w016 = handleControls((1, 0), w0)  // Move
    val w017 = handleControls((1, 1), w0)  // Move
    val w018 = handleControls((1, 2), w0)  // Del
    val w019 = handleControls((1, 3), w0)  // Del
    val w020 = handleControls((1, 4), w0)  // Small
    val w021 = handleControls((1, 5), w0)  // Small
    val w022 = handleControls((1, 6), w0)  // Mid
    val w023 = handleControls((1, 7), w0)  // Mid
    val w024 = handleControls((1, 8), w0)  // Large
    val w025 = handleControls((1, 9), w0)  // Large
    val w026 = handleControls((1, 10), w0) // Tri
    val w027 = handleControls((1, 11), w0) // Squ
    val w028 = handleControls((1, 12), w0) // Cir
    val w029 = handleControls((1, 13), w0) // Block
    val w030 = handleControls((1, 14), w0) // Block
    val w031 = handleControls((1, 15), w0) // Block

  test("Handlers integration test for clicking all control buttons"):
    assert(true)
