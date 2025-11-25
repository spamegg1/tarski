# Constants

This is the root dependency of the project; everything else depends on this.

## Design

The UI needs to be resizable, in case it's too big to fit onto laptop screens.

The mouse controls for the UI are implemented with the following idea:

- each particular section of the UI is a rectangle, with its own origin
- it has dimensions (height and width), and is split into a grid of smaller rectangles
- it has grid sizes (number of rows, number of columns)

With these variables known, no matter what the dimensions are,
or how many rows / columns it is split into,
it is possible to calculate which part (small rectangle)
of the grid gets clicked at any point.

These parts (small rectangles) of the grid can then be:

- positions on the chess board
- UI control buttons

### Dimensions

```scala
type Dims = (h: Double, w: Double)
```

## Work in progress

- Stay tuned!
