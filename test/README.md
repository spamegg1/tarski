# Testing

## Converter tests

A converter should:

- ✅ correctly convert `Pos` to `Point`.
- ✅ correctly convert `Point` to `Pos`.
- ✅ give the same conversion `Pos` result for every `Point` inside the same block.

## Handler tests

### Clicking the UI buttons

- Eval button should:
  - ✅ evaluate the formulas that have all of the necessary named blocks,
  - ✅ leave formulas unevaluated if they have missing named blocks.
- ✅ Move button should toggle move.
- ✅ Block display should do nothing if clicked.

Other buttons may depend on selected position.
There are 3 situations for selecting a position on the board.

#### No position is selected

- ✅ Add button should do nothing.
- Name buttons (a, b, c, d, e, f) should:
  - ✅ avail the name if it is occupied,
    - ✅ and reset formulas to un-evaluated,
  - ✅ do nothing if the name is available.
- ✅ Color buttons (Blue, Green, Coral) should change the selected color.
- ✅ Delete button should do nothing.
- ✅ Size buttons (Small, Mid, Large) should change the selected size.
- ✅ Shape buttons (Tri, Squ, Cir) should change the selected shape.

#### Selected position is empty

Different:

- ✅ Add button should add selected block (if available) to the position.
  - ✅ It should also reset the formulas to un-evaluated.

Same (not tested again):

- Name buttons (a, b, c, d, e, f) should:
  - avail the name if it is occupied,
    - and reset formulas to un-evaluated,
  - do nothing if the name is available.
- Color buttons (Blue, Green, Coral) should change the selected color.
- Delete button should do nothing.
- Size buttons (Small, Mid, Large) should change the selected size.
- Shape buttons (Tri, Squ, Cir) should change the selected shape.

#### Selected position has a block on it

- ✅ Add button should do nothing.
- Name buttons (a, b, c, d, e, f) should:
  - avail the name if it is occupied, (same, not tested again)
    - and reset formulas to un-evaluated, (same, not tested again)
  - ✅ add the name to the selected block if it is available:
    - ✅ The name should become occupied.
    - ✅ And formulas should be reset to un-evaluated.
- Color buttons (Blue, Green, Coral) should:
  - ✅ change the color of the block at selected position (test only 1, not all 3),
  - ✅ reset formulas to un-evaluated,
  - change the selected color. (same, not tested again)
- Size buttons (Small, Mid, Large) should:
  - ✅ change the size of the selected block (test only 1, not all 3),
  - ✅ reset formulas to un-evaluated,
  - change the selected size. (same, not tested again)
- Shape buttons (Tri, Squ, Cir) should:
  - ✅ change the shape of the selected block (test only 1, not all 3),
  - ✅ reset formulas to un-evaluated,
  - change the selected shape. (same, not tested again)
- Delete button should:
  - ✅ delete the block at selected position,
  - ✅ reset formulas to un-evaluated,
  - ✅ if the block has a name, avail that name.
  - ✅ The block display does not change (remains the same as deleted block).
  - ✅ Selected position also does not change.

### Clicking on the board

We need to consider selected position and clicked position.

#### Selected = None

- ✅ Clicking the position should make it selected.
- If clicked position has a block on it, UI display should:
  - ✅ update size,
  - ✅ update color,
  - ✅ update shape,
  - ✅ update block display.
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
      - ✅ disable move,
      - ✅ reset formulas.
  - if selected has no block:
    - ✅ if clicked has a block: do nothing.
    - ✅ if clicked has no block: do nothing.
- If move is disabled:
  - if selected has a block:
    - ✅ if clicked has a block: do nothing.
    - ✅ if clicked has no block: do nothing.
  - if selected has no block:
    - ✅ if clicked has a block: do nothing.
    - ✅ if clicked has no block: do nothing.

## Interpreter tests

Interpreter should:

- ✅ correctly interpret complex sentences in a world with many objects.
- ✅ throw `NoSuchElementException` on formulas with missing objects.

## World tests

### Empty world

An empty world should:

- ✅ have no named blocks.
- ✅ have no positioned blocks.
- ✅ have all 6 names (a, b, c, d, e, f) available.
- ✅ fail to remove a block.
- ✅ fail to move a block from one position to another.
- ✅ fail to remove name from a block.

### World with 1 unnamed block

In a world with only 1 block, which is unnamed:

- ✅ the position grid should only have one position in its keys.
- ✅ there should be a block at the only grid position.
- ✅ all 6 names should still be available.
- ✅ removing a block at a wrong position should fail.
- ✅ removing a name from a block at a wrong position should fail.
- ✅ removing a name from the unnamed block should fail.
- ✅ adding a name to a block at a wrong position should fail.
- adding a name to the unnamed block should:
  - ✅ change its label correctly,
  - ✅ make exactly 1 name occupied, leaving the other 5 names available.
- ✅ adding a name to an already named block should fail.
- ✅ moving a block at a wrong position should fail.
- ✅ moving the only block at the correct position should succeed.
  - ✅ It should not affect name availability (still 1 occupied, 5 available).
- ✅ adding a second block on top of the existing block should fail.
- ✅ adding a second, unnamed, block on an empty position should succeed.
  - ✅ It should not affect the name of the existing block,
  - ✅ it should not affect name availability (still 1 occupied, 5 available).

### World with 2 blocks (1 named, 1 unnamed)

- ✅ Moving a block to a position occupied by the other block should fail.
- ✅ Removing the name from the named block should:
  - ✅ succeed,
  - ✅ make the removed name available,
  - ✅ reset the label of the block to the empty string.
