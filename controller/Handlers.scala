package tarski
package controller

import Status.*, Result.*, Shape.*

def handleControls(pos: Pos, world: World): World = gridControl.get(pos) match
  case None => world
  case Some(value) => // make sure a button is clicked
    value match
      case "Eval"                            => handleEval(world)
      case "Add"                             => world.addBlockFromControls
      case "Del"                             => world.removeSelectedBlock
      case "Move"                            => world.toggleMove
      case "Block"                           => world
      case "a" | "b" | "c" | "d" | "e" | "f" => handleName(value, world)
      case "Blue" | "Green" | "Gray"         => handleColor(value, world)
      case "Small" | "Med" | "Large"         => handleSize(value, world)
      case "Tri" | "Squ" | "Cir"             => handleShape(value, world)

def handleEval(world: World): World =
  val results = world.formulas.map: (formula, result) =>
    val bool = eval(formula)(using world.blocks)
    formula -> (if bool then Valid else Invalid)
  world.copy(formulas = results)

def handlePos(pos: Pos, world: World): World =
  world.controls.pos match
    case None =>
      val newControls = world.controls.selectPos(pos).setBlock(world.grid.get(pos))
      println(newControls.pos)
      world.copy(controls = newControls)
    case Some(p) =>
      if p == pos then
        val newControls = world.controls.deselectPos.unsetBlock
        println(newControls.pos)
        world.copy(controls = newControls)
      else if world.controls.move then world.moveBlock(from = p, to = pos)
      else
        val newControls = world.controls.selectPos(pos).setBlock(world.grid.get(pos))
        println(newControls.pos)
        world.copy(controls = newControls)

def handleName(name: String, world: World): World = world.names.get(name) match
  case None => world
  case Some(Available) =>
    world.controls.pos match
      case None      => world
      case Some(pos) => world.addNameToBlockAt(pos, name)
  case Some(Occupied) =>
    world.blocks.get(name) match
      case None           => world
      case Some((_, pos)) => world.removeNameFromBlockAt(pos)

def handleColor(color: String, world: World): World =
  val newColor    = color.toColor
  val newControls = world.controls.setColor(newColor)
  val newGrid = world.controls.pos match
    case None => world.grid
    case Some(pos) =>
      world.grid.get(pos) match
        case None => world.grid
        case Some((block, name)) =>
          val newBlock = block.copy(color = newColor)
          world.grid.updated(pos, (newBlock, name))
  world.copy(controls = newControls, grid = newGrid)

def handleShape(shape: String, world: World): World =
  val newShape    = shape.toShape
  val newControls = world.controls.setShape(newShape)
  val newGrid = world.controls.pos match
    case None => world.grid
    case Some(pos) =>
      world.grid.get(pos) match
        case None => world.grid
        case Some((block, name)) =>
          val newBlock = block.copy(shape = newShape)
          world.grid.updated(pos, (newBlock, name))
  world.copy(controls = newControls, grid = newGrid)

def handleSize(size: String, world: World): World =
  val newSize     = size.toDouble
  val newControls = world.controls.setSize(newSize)
  val newGrid = world.controls.pos match
    case None => world.grid
    case Some(pos) =>
      world.grid.get(pos) match
        case None => world.grid
        case Some((block, name)) =>
          val newBlock = block.copy(size = newSize)
          world.grid.updated(pos, (newBlock, name))
  world.copy(controls = newControls, grid = newGrid)

extension (s: String)
  def toColor = s match
    case "Blue"  => Blue
    case "Gray"  => Gray
    case "Green" => Green
  def toDouble = s match
    case "Small" => Small
    case "Med"   => Med
    case "Large" => Large
  def toShape = s match
    case "Tri" => Tri
    case "Squ" => Squ
    case "Cir" => Cir
