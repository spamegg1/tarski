package tarski
package model

/** Enum for evaluation results of formulas.
  *
  * Valid/Invalid correspond to true/false.
  *
  * Ready is for either when the initial state before any evaluation has been done, or when a formula has to be left not
  * evaluated due to missing named objects in the world (caught as `java.util.NoSuchElementException` in
  * [[controller.Handler.handleEval]]), or when formulas are reset back to their initial state due to a change in the
  * world state.
  *
  * Error is for when a syntactically correct but unsupported predicate is used (like `Happy(x)`). Such cases are caught
  * as `java.lang.IllegalArgumentException` in [[controller.Handler.handleEval]].
  *
  * Syntax errors coming from Gapt's `fof"..."` string interpolator (as `java.lang.IllegalArgumentException`) are not
  * caught, the program crashes instead. So the main program requires syntactically correct formulas in order to run.
  */
enum Result:
  case Ready, Valid, Invalid, Error
