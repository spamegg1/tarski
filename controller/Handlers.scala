package tarski
package controller

import Status.*, Result.*, Shape.*

def handleControls(pos: Pos, world: World): World = gridControl.get(pos) match
  case None => world
  case Some(value) => // make sure a button is clicked
    value match
      case "Eval"                            => handleEval(world)
      case "Add"                             => world.addBlockFromControls
      case "Del"                             => world.removeBlockAt(pos)
      case "Move"                            => world.toggleMove
      case "Block"                           => world
      case "a" | "b" | "c" | "d" | "e" | "f" => handleName(value, world)
      case "Blue" | "Green" | "Gray"         => handleColor(value, world)
      case "Small" | "Mid" | "Large"         => handleSize(value, world)
      case "Tri" | "Squ" | "Cir"             => handleShape(value, world)

def handleEval(world: World): World =
  val results = world.formulas.map: (formula, result) =>
    val bool = eval(formula)(using world.blocks)
    formula -> (if bool then Valid else Invalid)
  world.copy(formulas = results)

def handlePos(pos: Pos, world: World): World =
  world.controls.pos match
    case None => // no pos is currently selected
      val newControls = world.controls
        .selectPos(pos)                // set clicked pos as selected
        .setBlock(world.grid.get(pos)) // set selected block to its block (if any)
      world.copy(controls = newControls)
    case Some(p) => // a pos is currently selected
      if p == pos then // clicked pos is the same as currently selected
        val newControls = world.controls.deselectPos // deselect pos
          .unsetBlock // unset selected block
        world.copy(controls = newControls)
      else                        // clicked pos is different than currently selected
      if world.controls.move then // move is enabled
        world.moveBlock(from = p, to = pos) // move currently selected block
      else                                  // move is disabled
        val newControls = world.controls
          .selectPos(pos)                // set clicked pos as currently selected
          .setBlock(world.grid.get(pos)) // set selected block to its block (if any)
        world.copy(controls = newControls)

def handleName(name: String, world: World): World = world.names.get(name) match
  case None => world // name does not exist, do nothing
  case Some(Available) => // name is available, attempt to add name to selected block
    world.controls.pos match
      case None      => world // no block is selected, do nothing
      case Some(pos) => world.addNameToBlockAt(pos, name)
  case Some(Occupied) => // name is occupied, attempt to remove name from its block
    world.blocks.get(name) match
      case None           => world // there is no block with that name, should not happen!
      case Some((_, pos)) => world.removeNameFromBlockAt(pos)

def handleColor(color: String, world: World): World =
  val newControls = world.controls.setColor(Gray)
  val newGrid = world.controls.pos match
    case None => world.grid
    case Some(pos) =>
      world.grid.get(pos) match
        case None => world.grid
        case Some((block, name)) =>
          val newColor = color match
            case "Blue"  => Blue
            case "Gray"  => Gray
            case "Green" => Green
          val newBlock = block.copy(color = newColor)
          world.grid.updated(pos, (newBlock, name))
  world.copy(controls = newControls, grid = newGrid)

def handleShape(shape: String, world: World): World = world
// world.copy(controls = world.controls.setShape(Cir))

def handleSize(size: String, world: World): World = world
// world.copy(controls = world.controls.setSize(Large))
