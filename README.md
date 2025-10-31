# Tarski's world

![world](.images/world.png)

Working prototype with some features missing and some bugs:

<https://github.com/user-attachments/assets/b6eb04ee-151d-46f3-9e47-ee536aaa79c6>

## Dev Blog

See my adventures in bad design on my
[Github Pages](https://spamegg1.github.io/tarski's-world/)

## About

Attempting to recreate Barwise and Etchemendy's
[Tarski's world](https://www.gradegrinder.net/Products/tw-index.html)
in [Doodle's Reactor](https://github.com/creativescala/doodle)
using [Scala 3](https://www.scala-lang.org).
(I might switch to ScalaFX later.)

They use 3D objects (cube, tetrahedron, dodecahedron) but I'm going with 2D as in
[Epp's book](https://github.com/spamegg1/Epp-Discrete-Math-5th-solutions/).
The image above is taken from there.

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

## Work in progress

Stay tuned!
