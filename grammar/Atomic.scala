package tarski

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

  def hasFree(v: Var) = this match
    case Small(t)            => t == v
    case Medium(t)           => t == v
    case Large(t)            => t == v
    case Circle(t)           => t == v
    case Triangle(t)         => t == v
    case Square(t)           => t == v
    case Blue(t)             => t == v
    case Black(t)            => t == v
    case Gray(t)             => t == v
    case LeftOf(t1, t2)      => t1 == v || t2 == v
    case RightOf(t1, t2)     => t1 == v || t2 == v
    case FrontOf(t1, t2)     => t1 == v || t2 == v
    case BackOf(t1, t2)      => t1 == v || t2 == v
    case Adjoins(t1, t2)     => t1 == v || t2 == v
    case Smaller(t1, t2)     => t1 == v || t2 == v
    case Larger(t1, t2)      => t1 == v || t2 == v
    case Same(t1, t2)        => t1 == v || t2 == v
    case SameSize(t1, t2)    => t1 == v || t2 == v
    case SameShape(t1, t2)   => t1 == v || t2 == v
    case SameColor(t1, t2)   => t1 == v || t2 == v
    case SameRow(t1, t2)     => t1 == v || t2 == v
    case SameCol(t1, t2)     => t1 == v || t2 == v
    case Between(t1, t2, t3) => t1 == v || t2 == v || t3 == v
