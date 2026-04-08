package tarski:
  /** Package to handle user input and communicate between [[model]] and [[view]]. Depends on [[constants]] and
    * [[model]]. It is used by [[view]], [[testing]] and [[main]]. It provides the [[Converter]] class that translates
    * between [[model.Pos]] and [[doodle.core.Point]]. It also provides the [[Handler]] object that manages user mouse
    * input to update the [[World]] state, and the [[Interpreter]] object that evaluates first order logic formulas.
    */
  package controller:
    export doodle.core.Point
    export gapt.expr.formula.fol.{FOLVar, FOLAtom, FOLFormula}
    export gapt.expr.formula.{All, And, Atom, Or, Neg, Ex, Imp, Iff}
    export constants.Constants, Constants.{BoardSize, UISize, Dims, GridSize}
    export model.{Pos, Block, Name, Status, Tone, Attr, World, Shape, Board, Panel, Messages}
    export model.{Controls, Names, Result, Sizes, Play, Game, Step, Select}
    export model.{Action, Rotation, Letter, Click, Commit, Choice, GameClick, GameAction}
    export Board.*, model.Formulas.*

  /** Package to draw and render the interface. It is designed in a pure way and does not hold any mutable state.
    * [[Render]] simply consumes a [[World]] and produces a [[doodle.image.Image]]. It is used by [[main]]. Depends on
    * [[constants]], [[model]] and [[controller]].
    */
  package view:
    export doodle.core.{Color, Point}
    export doodle.image.Image, Image.Elements.Text
    export gapt.expr.formula.fol.FOLFormula
    export constants.Constants
    export model.{Pos, Board, World, Sizes, Tone, Result, Controls}
    export model.{Formulas, Names, Status, Shape, Block, Game, Message, Messages}
    export controller.Converter
