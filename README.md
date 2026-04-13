# Tarski's world

An educational tool for the semantics of
[first-order logic](https://en.wikipedia.org/wiki/First-order_logic)

<https://github.com/user-attachments/assets/3b1d3352-e1f1-436e-ac52-5110b62c98c3>

## About

Attempting to recreate Barwise and Etchemendy's
[Tarski's world](https://www.gradegrinder.net/Products/tw-index.html)
in [Doodle's Reactor](https://github.com/creativescala/doodle)
using [Scala 3](https://www.scala-lang.org).
(I might switch to ScalaFX later.)

They use 3D objects (cube, tetrahedron, dodecahedron) but I'm going with 2D as in
[Epp's book](https://github.com/spamegg1/Epp-Discrete-Math-5th-solutions/).

## Acknowledgements

Thanks a lot to [Noel Welsh](https://github.com/noelwelsh) for his awesome Doodle library
and all the help on Discord.

Thanks to Jon Barwise (1942-2000) and John Etchemendy for their awesome idea and book
on Tarski's world.

## Dev Blog

See my adventures in bad design on my
[Github Pages](https://spamegg1.github.io/tarski's-world/)

## Info

This is a [Scala-cli](https://scala-cli.virtuslab.org/) project.
With Scala 3.5.0 and above, you can simply run `scala compile .` and `scala test .`.

## Module dependency

```scala
//     main
//       |
//     view   testing
//       |     /
//    controller
//       |
//     model
//       |
//    constants
```

You can read more about each module at:

- [Constants](constants/README.md)
- [Model](model/README.md)
- [Controller](controller/README.md)
- [View](view/README.md)
- [Test](test/README.md)
- [Main](main/README.md)

## Installation

Current version is 1.0.1 (Apr 09, 2026). Released for Scala 3 only.

You will need a JVM, and Scala 3. [This](https://www.scala-lang.org/download/)
should give you everything you need.

An easier way is to install [Scala-cli](https://scala-cli.virtuslab.org/install),
which can then automatically install a JVM for you.

Also you'll need an IDE:

- [Metals](https://scalameta.org/metals/) extension on
  [Visual Studio Code](https://code.visualstudio.com/)
- [IntelliJ](https://www.jetbrains.com/idea/download/) with Scala plugin

For Scala-cli (or just plain `scala`), add to your `project.scala` (or any file):

```scala
//> using dep io.github.spamegg1::tarski:1.0.1
```

For SBT, add to your `build.sbt`:

```scala
libraryDependencies += "io.github.spamegg1" %% "tarski" % "1.0.1"
```

## API Docs and artifacts

Docs can be found at [Javadoc](https://javadoc.io/doc/io.github.spamegg1/tarski_3/latest/index.html)

Artifacts at [Maven Central](https://repo1.maven.org/maven2/io/github/spamegg1/tarski_3/)

## Usage

To get a quick look and feel, you can execute `tarski.main.Example.runExample`.

To play a quick game, you can execute `tarski.main.Example.playGame`.
See below for more on game mode.

*Note:* In Scala 3.8.2+ you can run the example directly from the Scala REPL.
Just `:dep io.github.spamegg1::tarski:1.0.1` then `tarski.main.Example.runExample`.

Tarski's world is intended to be used interactively inside an IDE
such as IntelliJ or Visual Studio Code.

Generally, in an educational setting, a world and a list of formulas are given to you.
Then you run the program to evaluate the formulas, move or change the blocks,
add or change the formulas if necessary, based on what you are asked to do in exercises.
Of course, you can write your own worlds and formulas too.

### Running: an example

Then run it with `tarski.main.runWorld` to start interacting.
You will see the interactive window like the one above in the video.
Here are the details:

```scala
//> using dep io.github.spamegg1::tarski:1.0.1

import tarski.main.*, Shape.*, Sizes.*, Tone.*

val grid: Grid = Map(
  (1, 2) -> Block(Sml, Tri, Lim, "a"),
  (4, 3) -> Block(Mid, Tri, Blu),
  (5, 6) -> Block(Big, Cir, Red, "d"),
  (6, 3) -> Block(Sml, Sqr, Blu)
)

val formulas = Seq(
  fof"¬(∃x Big(x))",
  fof"∀x Sqr(x)",
  fof"∀x ¬ Cir(x)",
  fof"¬(∀x Sml(x))",
  fof"∃x Tri(x)",
  fof"∀x (¬(Shp(c, x) ∨ Les(x, c)) → ¬Ton(x, c))",
  fof"∃x Cir(x)",
  fof"Eq(a, b)",
  fof"∀x ∃y Mor(x, y)",
  fof"c != d",
  fof"∀x (Squ(x) → Tri(x))",
  fof"∃x (Tri(x) ↔ Mid(x))",
  fof"¬(∃x (Cir(x) ∧ Sml(x)))",
)

// The interface is 1600x800 by default.
// If the interface is too small or too large,
// try a different scale factor than 1.0:
@main
def run = runWorld(grid, formulas, 1.0)
```

You can add or remove blocks interactively.
To edit the formulas, close the window, edit them in your IDE, then restart.

### Imports

All you need is to `import tarski.main.*`.
Optionally you can also `import Shape.*, Sizes.*, Tone.*`
to avoid repeatedly writing `Shape.`, `Sizes.` or `Tone.`.

### Blocks

Blocks have 3 attributes, each of which has 3 possible values:

|Attr | 1 | 2 | 3 |
|:----|:--|:--|:--|
|Tone |Blu|Lim|Red|
|Shape|Tri|Sqr|Cir|
|Sizes|Sml|Mid|Big|

Blocks can also have an optional name, only one of: `a, b, c, d, e, f`.
Other names are not allowed. Formulas can then refer to these names as constants.

### Grids

Then you can write a `Grid`, a map of positions `Pos` to `Block`s, to define the board.
It's an 8x8 standard chess board; coordinates are 0-indexed.
See above for details and an example.

### Formulas

Then you can write a list of first-order logic formulas, `FOLFormula`
(courtesy of [Gapt](https://github.com/gapt/gapt)).

The formulas use a special string interpolator `fof"..."`,
and can use the Unicode symbols or their ASCII equivalents for logical connectives:

|Connective   |ASCII|Unicode|
|:------------|:----|:------|
|and          |`&`  |`∧`    |
|or           |`\|` |`∨`    |
|not          |`-`  |`¬`    |
|implies      |`->` |`→`    |
|biconditional|`<->`|`↔`    |
|forall       |`!`  |`∀`    |
|exists       |`?`  |`∃`    |

### Predicates for atomic formulas

The following predicates are supported:

#### Unary

|Syntax  |Semantics        |
|:-------|:----------------|
|`Tri(x)`|x is a triangle  |
|`Sqr(x)`|x is a square    |
|`Cir(x)`|x is a circle    |
|`Blu(x)`|x has color blue |
|`Lim(x)`|x has color lime |
|`Red(x)`|x has color red  |
|`Sml(x)`|x has small size |
|`Mid(x)`|x has medium size|
|`Big(x)`|x has big size   |

#### Binary

|Syntax     |Semantics                                    |
|:----------|:--------------------------------------------|
|`Lft(x, y)`|x is to the left of y                        |
|`Rgt(x, y)`|x is to the right of y                       |
|`Bel(x, y)`|x is below y                                 |
|`Abv(x, y)`|x is above y                                 |
|`Adj(x, y)`|x is adjacent (but not diagonally) to y      |
|`Les(x, y)`|x is smaller in size than y                  |
|`Mor(x, y)`|x is bigger in size than y                   |
|`Row(x, y)`|x is on the same row as y                    |
|`Col(x, y)`|x is on the same column as y                 |
|`Siz(x, y)`|x has the same size as y                     |
|`Shp(x, y)`|x has the same shape as y                    |
|`Ton(x, y)`|x has the same tone as y                     |
|`Eq(x, y)` |x is equal to y (in size, shape and tone)    |
|`x = y`    |x and y are at the same location on the board|

#### Ternary

|Syntax        |Semantics                                                          |
|:-------------|:------------------------------------------------------------------|
|`Btw(x, y, z)`|"x is between y and z (vertically, horizontally or 45° diagonally)"|

##### Note on equality

Normally in first-order logic, equality is interpreted as "reference equality",
meaning, `x = y` if both `x` and `y` refer to the same object (number, set, etc.)

The original Tarski's World app lets you assign multiple names to the same block,
which makes it possible for `x = y` reference equality to be true with 1 block.
Here we do not allow that, because having multiple names is a bit confusing.
Our blocks can only have up to 1 name, so `x = y` is always false
whenever `x` and `y` refer to separate blocks.

We interpret `=` as reference equality, where `x = y` if
`x` and `y` are at the same location (row and column) on the board,
therefore they represent the same block.
This allows us to express things like "there are exactly four blocks" kind of sentences.

However reference equality is not always desirable.
So we have another binary predicate `Eq` as "value equality",
where two separate blocks can be equal
if they have all the same attributes (size, shape, tone).

So we have both kinds of equality available to us 😄

## Game mode

<https://github.com/user-attachments/assets/b9b08a2d-42dd-4ec4-882c-3fd1d41cf307>

You can play a game against Tarski's world to defend your position
about the truth of a formula in a world.
You need a grid and a formula, then run `playGame` with them:

```scala
//> using dep io.github.spamegg1::tarski:1.0.1

import tarski.main.*, Shape.*, Sizes.*, Tone.*

val grid: Grid = Map(
  (1, 2) -> Block(Sml, Tri, Lim, "a"),
  (4, 3) -> Block(Mid, Tri, Blu),
  (5, 6) -> Block(Big, Cir, Red, "d"),
  (6, 3) -> Block(Sml, Sqr, Blu)
)

val formula = fof"∀x ∃y (Mor(x, y) ∨ Abv(y, x))"

// The interface is 1600x800 by default.
// If the interface is too small or too large,
// try a different scale factor than 1.0:
@main
def run = playGame(grid, formula, 1.0)
```

## Exercises

You can work through the examples in the
[companion repository](https://github.com/spamegg1/tarski-examples)
