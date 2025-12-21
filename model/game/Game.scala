package tarski
package model

type Message = String

// There will be a handler that initially "sets up" the game by first
// asking the user for their commitment. So we don't have to use Option[Boolean].

enum Select:
  case Off
  case Wait
  case On(name: Name)

enum Choice:
  case Off, Wait

case class Game(
    commitment: Boolean,
    formula: FOLFormula,
    left: Option[FOLFormula] = None,
    right: Option[FOLFormula] = None,
    selected: Select = Select.Off,
    choice: Choice = Choice.Off,
    messages: List[Message] = Nil
):
  def chooseBlock(name: Name): Game = selected match
    case Select.Off   => this // should not happen
    case Select.Wait  => copy(selected = Select.On(name))
    case Select.On(_) => copy(selected = Select.On(name))

  def chooseFormula(f: FOLFormula): Game = choice match
    case Choice.Off  => this // should not happen
    case Choice.Wait => copy(formula = f, left = None, right = None, choice = Choice.Off)
