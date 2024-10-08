package tarski
package testing

import org.scalacheck.{Gen, Test, Prop}, Prop.forAll

val varGen = Gen.oneOf[Var](Var.values)
val nameGen = Gen.oneOf[Name](Name.values)
val termGen = Gen.oneOf[Term](varGen, nameGen)
