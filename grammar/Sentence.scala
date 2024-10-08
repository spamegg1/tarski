package tarski

case class Sentence(f: Formula):
  require(f.hasNoFreeVars)

  def eval(b: State): Boolean = true
