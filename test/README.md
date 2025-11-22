# Testing

## Converter tests

## Handler tests

### Clicking the UI buttons

- Eval button should:
  - ✅ evaluate the formulas that have all of the necessary named blocks
  - ✅ leave formulas unevaluated if they have missing named blocks
- ✅ Move button should toggle move.
- ✅ Block display should do nothing if clicked.

Other buttons may depend on selected position.
There are 3 situations for selecting a position on the board.

#### No position is selected

- ✅ Add button should do nothing.
- Name buttons (a, b, c, d, e, f) should:
  - ✅ avail the name if it is occupied,
  - ✅ do nothing if the name is available.
- ✅ Color buttons (Blue, Green, Gray) should change the selected color.
- ✅ Delete button should do nothing.
- ✅ Size buttons (Small, Mid, Large) should change the selected size.
- ✅ Shape buttons (Tri, Squ, Cir) should change the selected shape.

#### Selected position is empty

Different:

- ✅ Add button should add selected block (if available) to the position.

Same (not tested again):

- Name buttons (a, b, c, d, e, f) should:
  - avail the name if it is occupied,
  - do nothing if the name is available.
- Color buttons (Blue, Green, Gray) should change the selected color.
- Delete button should do nothing.
- Size buttons (Small, Mid, Large) should change the selected size.
- Shape buttons (Tri, Squ, Cir) should change the selected shape.

#### Selected position has a block on it

- ✅ Add button should do nothing.
- Name buttons (a, b, c, d, e, f) should:
  - avail the name if it is occupied, (same, not tested again)
  - ✅ add the name to the selected block if it is available:
    - ✅ The name should become occupied.
- Color buttons (Blue, Green, Gray) should:
  - ✅ change the color of the block at selected position (test only 1, not all 3),
  - change the selected color. (same, not tested again)
- Size buttons (Small, Mid, Large) should:
  - ✅ change the size of the selected block (test only 1, not all 3),
  - change the selected size. (same, not tested again)
- Shape buttons (Tri, Squ, Cir) should:
  - ✅ change the shape of the selected block (test only 1, not all 3),
  - change the selected shape. (same, not tested again)
- Delete button should:
  - ✅ delete the block at selected position,
  - ✅ if the block has a name, avail that name.
  - ✅ The block display does not change (remains the same as deleted block).
  - ✅ Selected position also does not change.

### Clicking on the board

We need to consider selected position and clicked position.

#### Selected = None

- ✅ Clicking the position should make it selected.
- If clicked position has a block on it, UI display should:
  - ✅ update size
  - ✅ update color
  - ✅ update shape
  - ✅ update block display
- ✅ If clicked position has no block, then nothing changes.

#### Selected = clicked

- ✅ Clicking the position should de-select it.
- ✅ UI controls and block display should be reset to None.

#### Selected is different than clicked

- It should:
  - ✅ set the clicked position as the new selected position,
  - ✅ change UI and block display accordingly (if clicked has block, update).

Additionally,

- If move is enabled:
  - if selected has a block:
    - ✅ if clicked has a block: do nothing.
    - if clicked has no block:
      - ✅ move the block,
      - ✅ disable move.
  - if selected has no block:
    - ✅ if clicked has a block: do nothing.
    - ✅ if clicked has no block: do nothing.
- If move is disabled:
  - if selected has a block:
    - If clicked has a block: do nothing.
    - If clicked has no block: do nothing.
  - if selected has no block:
    - If clicked has a block: do nothing.
    - If clicked has no block: do nothing.

## Interpreter tests

TODO

## World tests

TODO
