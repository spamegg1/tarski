package tarski
package model

/** Enumeration for the remaining actions for the buttons in a game.
  *
  * `Back` and `OK` are to navigate the game steps, and `Display` is the block display (does nothing when clicked).
  */
enum GameAction:
  case Back, OK, Display
