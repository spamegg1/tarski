package tarski
package view

/** Contains methods for setting the window title. */
object Title:
  import scala.quoted.*

  /** Macro to convert the name of a `val` into a string. This is useful for setting the window title based on the world
    * and formulas the user is working on.
    *
    * @param expr
    *   Any kind of value whose name we want to get.
    * @return
    *   The `String` that is the name of the value.
    */
  transparent inline def show(inline expr: Any): String =
    ${ showImpl('expr) }

  /** Macro implementation for [[show]].
    *
    * @param expr
    *   Any kind of value whose name we want to get.
    * @return
    *   The `Expr` version of the `String` we want (the name of the value).
    */
  private def showImpl(expr: Expr[Any])(using Quotes): Expr[String] =
    import quotes.reflect.*

    /** Recursive helper to extract the name we want from the AST.
      *
      * We are only interested in two cases: an identifier, or an inlined identifier.
      *
      * @param tree
      *   The abstract syntax tree representing a value.
      * @return
      *   A `String` which is the name of the value we want.
      */
    @annotation.tailrec
    def extract(tree: Tree): String = tree match
      case Ident(name)         => name
      case Inlined(_, _, term) => extract(term)
      case _                   => "?"

    val name = extract(expr.asTerm)
    Expr(name)
