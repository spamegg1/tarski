package tarski
package testing

class FormulaSuite extends munit.FunSuite:
  test("simple quantified formula has no free variables"):
    val formula = Forall(X, Atom(Small(X)))
    assert(formula.hasNoFreeVars)
