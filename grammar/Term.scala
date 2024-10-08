package tarski

enum Var:
  case S, T, U, V, W, X, Y, Z
export Var.*

enum Name:
  case A, B, C, D, E, F, G, H
export Name.*

type Term = Var | Name
