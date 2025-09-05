package tarski

type Name = String

object Name:
  var counter = -1
  def generate: Name =
    counter += 1
    s"block$counter"
