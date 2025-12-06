package tarski:
  /** Package for all the global constants used everywhere and the [[Constants]] class. */
  package constants:
    export concurrent.duration.FiniteDuration
    export doodle.core.{Color, Point}
    export Color.{deepSkyBlue, lightGray, white, black, yellowGreen}
    export doodle.image.Image
    export doodle.core.font.{Font, FontFamily, FontStyle, FontWeight, FontSize}
    export doodle.java2d.effect.Frame

  /** Package for all the data definitions used throughout Tarski's world. It contains many types, the most important
    * being the [[World]] class. It is used by [[controller]], [[view]], [[testing]] and [[main]]. Depends on
    * [[constants]].
    */
  package model:
    export collection.immutable.ListMap
    export doodle.core.Color
    export gapt.expr.formula.fol.FOLFormula
    export constants.Constants, Constants.{BlueColor, GreenColor, GrayColor}

  /** Package to draw and render the interface. It is designed in a pure way and does not hold any mutable state.
    * [[Render]] simply consumes a [[World]] and produces a [[doodle.image.Image]]. It is used by [[main]]. Depends on
    * [[constants]], [[model]] and [[controller]].
    */
  package view:
    export doodle.core.{Color, Point}
    export Color.{black, red, green, blue}
    export doodle.image.Image, Image.Elements.Text
    export gapt.expr.formula.fol.FOLFormula
    export constants.Constants
    export model.{Pos, PosGrid, World, Sizes, Tone}, Sizes.given, Tone.given
    export model.{Result, Formulas, Controls, Names, Status, Shape, Block}
    export controller.Converter

  /** Package to handle user input and communicate between [[model]] and [[view]]. Depends on [[constants]] and
    * [[model]]. It is used by [[view]], [[testing]] and [[main]]. It provides the [[Converter]] class that translates
    * between [[model.Pos]] and [[doodle.core.Point]]. It also provides the [[Handler]] object that manages user mouse
    * input to update the [[World]] state, and the [[Interpreter]] object that evaluates first order logic formulas.
    */
  package controller:
    export doodle.core.Point
    export gapt.expr.formula.fol.{FOLVar, FOLConst, FOLAtom, FOLFormula}
    export gapt.expr.subst.FOLSubstitution
    export gapt.expr.formula.{All, And, Atom, Or, Neg, Ex, Imp, Iff}
    export constants.Constants, Constants.{BoardSize, UISize, Dims, GridSize}
    export model.{Pos, Block, NameGrid, PosGrid, Status, Tone, Attr}
    export model.{World, Shape, Controls, Names, Result, Sizes}

  /** This package tests [[World]] from [[model]], and [[Converter]], [[Handler]] and [[Interpreter]] from
    * [[controller]]. Depends on [[constants]], [[model]] and [[controller]].
    */
  package testing:
    export doodle.core.Point
    export gapt.expr.stringInterpolationForExpressions
    export constants.Constants, Constants.{DefaultSize, Epsilon}
    export model.{World, Block, Pos, PosGrid, Grid, NameGrid}
    export model.{Status, Result, Shape, Sizes, Tone, Controls}
    export controller.{Interpreter, Converter, Handler}

  /** This package is the user-facing part of Tarski's world. Depends on [[constants]], [[model]], [[controller]] and
    * [[view]]. It provides the necessary data types from [[model]] for users to define their own worlds and write their
    * own first order logic formulas, and the [[run]] method to evaluate them in a world.
    */
  package main:
    export cats.effect.unsafe.implicits.global
    export gapt.expr.stringInterpolationForExpressions
    export gapt.expr.formula.fol.FOLFormula
    export doodle.reactor.syntax.all.animateWithFrame
    export doodle.reactor.Reactor
    export doodle.java2d.{java2dAnimationRenderer, java2dCanvasAlgebra, java2dRenderer}
    export constants.Constants, Constants.{DefaultSize, TickRate}
    export model.{World, Block, Grid, Formulas, Shape, Sizes, Tone}
    export view.Render
    export controller.React
