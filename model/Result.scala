package tarski
package model

/** Enum for evaluation results of formulas.
  *
  * Valid/Invalid correspond to true/false.
  *
  * Ready is for the initial state before any evaluation has been done, or when a formula has to be left not evaluated
  * due to missing named objects in the world, or when formulas are reset back to their initial state due to a change in
  * the world state.
  */
enum Result:
  case Ready, Valid, Invalid
