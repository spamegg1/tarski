package tarski

enum Formula:
  case Atom(a: Atomic)
  case Not(f: Formula)
  case And(f1: Formula, f2: Formula)
  case Or(f1: Formula, f2: Formula)
  case Implies(f1: Formula, f2: Formula)
  case Bicond(f1: Formula, f2: Formula)
  case Forall(v: Var, f: Formula)
  case Exists(v: Var, f: Formula)

  def hasFree(v: Var): Boolean = this match
    case Atom(a)         => a.hasFree(v)
    case Not(f)          => f.hasFree(v)
    case And(f1, f2)     => f1.hasFree(v) || f2.hasFree(v)
    case Or(f1, f2)      => f1.hasFree(v) || f2.hasFree(v)
    case Implies(f1, f2) => f1.hasFree(v) || f2.hasFree(v)
    case Bicond(f1, f2)  => f1.hasFree(v) || f2.hasFree(v)
    case Forall(v1, f)   => v != v1 && f.hasFree(v)
    case Exists(v1, f)   => v != v1 && f.hasFree(v)

  def hasNoFreeVars = Var.values.forall(!hasFree(_))
