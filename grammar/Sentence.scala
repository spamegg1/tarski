package tarski

case class Sentence(f: Formula):
  require(f.hasNoFreeVars)
