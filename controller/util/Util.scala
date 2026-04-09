package tarski
package controller

object Util:
  import gapt.expr.formula.fol.FOLFormula

  /** Custom string interpolator to be used with `FOLFormula` in order to avoid `.toUntypedString` calls everywhere.
    *
    * @param args
    *   The arguments to be interpolated.
    * @return
    *   The interpolated combined string of the arguments.
    */
  extension (sc: StringContext) def ui(args: String*): String = sc.s(args*)

  /** This conversion enables us to utilize `.toUntypedString` in the [[ui]] custom interpolator. */
  given Conversion[FOLFormula, String] = _.toUntypedString

  /** This conversion lets us mix `FOLFormula` and other types in the [[ui]] custom interpolator. */
  given Conversion[AnyVal, String] = _.toString
end Util

export Util.*, Util.given
