package tarski:
  export cats.effect.unsafe.implicits.global
  export concurrent.duration.FiniteDuration
  export doodle.core.{Color, Point, OpenPath}
  export Color.{deepSkyBlue, lightGray, white, black, yellowGreen, red, green}
  export doodle.core.font.{Font, FontSize}
  export doodle.image.Image, Image.Elements.Text
  export doodle.image.syntax.all.*
  export doodle.java2d.effect.Frame
  export doodle.java2d.{Algebra, java2dAnimationRenderer, Java2dToPicture}
  export doodle.java2d.{java2dCanvasAlgebra, java2dRenderer, java2dFrame}
  export doodle.reactor.Reactor
  export doodle.core.syntax.all.*

  export gapt.expr.{stringInterpolationForExpressions, Const}
  export gapt.expr.ty.{Ti, To}
  export gapt.expr.formula.fol.{FOLVar, FOLConst, FOLFunction, FOLAtom, FOLFormula}
  export gapt.expr.formula.{All, And, Atom, Or, Neg, Ex, Imp, Iff}
  export gapt.expr.subst.FOLSubstitution

  extension (f: FOLFormula)
    def substitute(x: FOLVar, c: FOLConst) = FOLSubstitution((x, c)).apply(f)

  package model:
    export view.{Block, FormulaBox, Controls}

  package view:
    export Constants.*
    export model.{Grid, World}
    export controller.Converter.*

  package controller:
    export model.{Pos, Blocks, Grid, GridSize, World}, Pos.*
    export view.{Controls, FormulaBox, Constants}, Constants.*
    export Converter.*

  package testing:
    export model.{World, Grid, Blocks, Status}, Status.*
    export view.{Block, Constants}, Constants.*
    export controller.{eval, Converter}
    export Converter.{BoardConverter, convertPointConditionally}

  package main:
    export view.{Block, Renderer, Constants}, Constants.*, Renderer.*
    export controller.Reactor.*
    export model.{World, GridSize}
