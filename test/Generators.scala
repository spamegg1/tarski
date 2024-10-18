package tarski
package testing

val varGen = Gen.oneOf[Var](Var.values)
val nameGen = Gen.oneOf[Name](Name.values)
val termGen = Gen.oneOf[Term](varGen, nameGen)

// won't test all 23 atomics, just select a few, of different arities.
val atomFreeGen =
  for
    vari <- varGen
    t1 <- termGen
    t2 <- termGen
  yield (vari, Seq(Medium(vari), LeftOf(t1, vari), Between(t1, vari, t2)))

val atomNonFreeGen =
  for
    vari <- varGen
    t1 <- termGen
    t2 <- termGen
    t3 <- termGen
    if vari != t1 && vari != t2 && vari != t3
  yield (vari, Seq(Circle(t1), RightOf(t1, t2), Between(t1, t2, t3)))
