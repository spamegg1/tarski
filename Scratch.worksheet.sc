import tarski.*

def testTerm(t: Term, v: Var) = t == v
testTerm(A, S)
testTerm(A, T)
testTerm(B, S)
testTerm(X, X)
testTerm(Y, X)
testTerm(Y, U)
