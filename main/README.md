# Main

Users will need:

- a Tarski world: grid with blocks placed on it
- a list of formulas to be evaluated

Often users will be given only one of these:

- given a list of formulas, create a world that evaluates them to desired T/F values:
  - place blocks at correct locations to get the correct results;
- given a world and some English sentences, write formulas that express the sentences:
  - then evaluate the sentences to see if they are true or false,
  - sometimes move blocks around, or fix the formulas themselves, to make them true.

In any case, users will need access to:

- `Block` and its parts: `Sizes, Tone, Shape`
- `PosGrid`: the chess board that holds the blocks
- `gapt.expr.stringInterpolationForExpressions` to write formulas
- a way to "run" Tarski's world (taking blocks and formulas as input)
- a way to scale the size of the UI if it does not fit into their screen.

## Work in progress

- Stay tuned!
