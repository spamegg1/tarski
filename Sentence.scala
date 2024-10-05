package tarski

enum Logical:
  case AND, OR, NOT, IMPLIES, BICOND
import Logical.*

enum Operator:
  case EQ, NEQ
import Operator.*

enum Quantifier:
  case Forall, Exists
import Quantifier.*

enum Variable:
  case S, T, U, V, W, X, Y, Z
import Variable.*

enum Name:
  case A, B, C, D, E, F, G, H
import Name.*

enum Paren:
  case L, R
import Paren.*

type Token = Logical | Operator | Quantifier | Variable | Name | Paren

case class Sentence(stuff: Int)
