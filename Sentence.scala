package tarski

enum Variable:
  case S, T, U, V, W, X, Y, Z

enum Name:
  case A, B, C, D, E, F, G, H

type Term = Variable | Name

enum Atomic:
  case Small(t: Term)
  case Medium(t: Term)
  case Large(t: Term)
  case Circle(t: Term)
  case Triangle(t: Term)
  case Square(t: Term)
  case Blue(t: Term)
  case Black(t: Term)
  case Gray(t: Term)
  case LeftOf(t1: Term, t2: Term)
  case RightOf(t1: Term, t2: Term)
  case FrontOf(t1: Term, t2: Term)
  case BackOf(t1: Term, t2: Term)
  case Adjoins(t1: Term, t2: Term)
  case Smaller(t1: Term, t2: Term)
  case Larger(t1: Term, t2: Term)
  case Same(t1: Term, t2: Term)
  case SameSize(t1: Term, t2: Term)
  case SameShape(t1: Term, t2: Term)
  case SameColor(t1: Term, t2: Term)
  case SameRow(t1: Term, t2: Term)
  case SameCol(t1: Term, t2: Term)
  case Between(t1: Term, t2: Term, t3: Term)

enum Formula:
  case Atom(a: Atomic)
  case Not(f: Formula)
  case And(f1: Formula, f2: Formula)
  case Or(f1: Formula, f2: Formula)
  case Implies(f1: Formula, f2: Formula)
  case Bicond(f1: Formula, f2: Formula)
  case Forall(v: Variable, f: Formula)
  case Exists(v: Variable, f: Formula)

  def hasNoFreeVars = true

case class Sentence(f: Formula):
  require(f.hasNoFreeVars)
