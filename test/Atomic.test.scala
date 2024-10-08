package tarski
package testing

class AtomicSuite extends munit.FunSuite:
  test("unary atomic formula with a free variable"):
    val atom = Small(S)
    assert(atom.hasFree(S))
    assert(!atom.hasFree(T))

  test("unary atomic formula without free variables"):
    val atom = Circle(A)
    assert(!atom.hasFree(S))
    assert(!atom.hasFree(T))

  test("binary atomic formula with one free variable (left)"):
    val atom = LeftOf(U, D)
    assert(atom.hasFree(U))
    assert(!atom.hasFree(V))

  test("binary atomic formula with one free variable (right)"):
    val atom = Adjoins(G, W)
    assert(atom.hasFree(W))
    assert(!atom.hasFree(Z))

  test("binary atomic formula with two free variables"):
    val atom = SameSize(U, V)
    assert(atom.hasFree(U))
    assert(atom.hasFree(V))
    assert(!atom.hasFree(W))

  test("ternary atomic formula with three free variables"):
    val atom = Between(U, V, Y)
    assert(atom.hasFree(U))
    assert(atom.hasFree(V))
    assert(atom.hasFree(Y))
    assert(!atom.hasFree(Z))
