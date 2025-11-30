# Tarski's world

![world](.images/world.png)

Working prototype with some features missing and some bugs:

<https://github.com/user-attachments/assets/b6eb04ee-151d-46f3-9e47-ee536aaa79c6>

## About

Attempting to recreate Barwise and Etchemendy's
[Tarski's world](https://www.gradegrinder.net/Products/tw-index.html)
in [Doodle's Reactor](https://github.com/creativescala/doodle)
using [Scala 3](https://www.scala-lang.org).
(I might switch to ScalaFX later.)

They use 3D objects (cube, tetrahedron, dodecahedron) but I'm going with 2D as in
[Epp's book](https://github.com/spamegg1/Epp-Discrete-Math-5th-solutions/).
The image above is taken from there.

## Installation

Current version is 0.0.1

**This library has not been released yet! Coming soon...**
**But once it is released, you can do the following.**

For Scala-cli, add to your `project.scala` (or any file):

```scala
//> using dep io.github.spamegg1::tarski:<version>
```

For SBT, add to your `build.sbt`:

```scala
libraryDependencies += "io.github.spamegg1" %% "tarski" % "<version>"
```

Replace `<version>` with the latest version on the right =>

## Usage

To get a quick look and feel, you can execute `tarski.main.Example.runExample`.

Tarski's world is intended to be used interactively inside an IDE
such as IntelliJ or Visual Studio Code.
Generally, in an educational setting, a world and a list of formulas are given to you.
Then you run the program to evaluate the formulas, move or change the blocks,
add or change the formulas if necessary, based on what you are asked to do in exercises.

All you need is to `import tarski.main.*`.
Optionally you can also `import Shape.*, Sizes.*, Tone.*`
to avoid repeatedly writing `Shape.`, `Sizes.` or `Tone.`.

Then you can write a `Grid`, a map of positions `Pos` to `Block`s, to define the board;
and write a list of first order logic formulas, `FOLFormula`.
The formulas use a special string interpolator `fof"..."`,
and can use the Unicode symbols or their ASCII equivalents for logical connectives:

|Connective|ASCII|Unicode|
|:-|:-|:-|
|and|`&`|`∧`|
|or|`\|`|`∨`|
|not|`-`|`¬`|
|implies|`->`|`→`|
|biconditional|`<->`|`↔`|
|forall|`!`|`∀`|
|exists|`?`|`∃`|

Then run it with `runWorld` to start interacting. Here are the details:

```scala
import tarski.main.*, Shape.*, Sizes.*, Tone.*

val grid: Grid = Map(
  (1, 2) -> Block(Small, Tri, Green, "a"),
  (4, 3) -> Block(Mid, Tri, Blue),
  (5, 6) -> Block(Large, Cir, Gray, "d"),
  (6, 3) -> Block(Small, Squ, Blue)
)

val formulas = Seq(
  fof"¬(∃x Large(x))",
  fof"∀x Squ(x)",
  fof"∀x ¬ Cir(x)",
  fof"¬(∀x Small(x))",
  fof"∃x Tri(x)",
  fof"∀x Large(x)",
  fof"∃x Cir(x)",
  fof"a = b",
  fof"∀x ∃y Larger(x, y)",
  fof"c != d",
  fof"∀x (Squ(x) → Tri(x))",
  fof"∃x (Tri(x) ↔ Mid(x))",
  fof"¬(∃x (Cir(x) ∧ Small(x)))",
)

@main
def runExample = runWorld(grid, formulas)
```

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

## Work in progress

Stay tuned!
