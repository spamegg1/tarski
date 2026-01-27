package tarski
package model

/** A mapping between first-order formulas and their evaluation results. */
type Formulas = ListMap[FOLFormula, Result]

/** Contains helper methods for the [[Formulas]] type alias. */
object Formulas:
  /** Generates a map where every formula is yet pending, not evaluated.
    *
    * @param fs
    *   A sequence of first-order formulas.
    * @return
    *   A map from formulas to [[Result]]s where each formula is ready to be evaluated.
    */
  def fromSeq(fs: Seq[FOLFormula]): Formulas = ListMap.from:
    fs.map(f => f -> Result.Ready)

extension (formulas: Formulas)
  /** Extension method for the [[Formulas]] type alias that resets evaluation results.
    *
    * @return
    *   The same map, where every formula is set as not evaluated yet.
    */
  def reset = formulas.map((f, _) => f -> Result.Ready)

  /** Extension method for the [[Formulas]] type alias that adds one formula. Convenient for quickly working on
    * examples.
    *
    * @return
    *   The map plus the given formula, not yet evaluated. The results of the other formulas stay the same.
    */
  def add(f: FOLFormula) = formulas + (f -> Result.Ready)
end extension

extension (f: FOLFormula)
  /** Extension method to substitute a [[Name]] into a [[FOLFormula]] for all free occurrences of a variable. Used in
    * [[eval]].
    *
    * @param x
    *   A first-order variable
    * @param c
    *   A [[Name]] to be used as a first-order constant
    * @return
    *   the formula, with all free occurrences of `x` replaced by `c`.
    */
  def sub(x: FOLVar, c: Name) = FOLSubstitution((x, FOLConst(c))).apply(f)
