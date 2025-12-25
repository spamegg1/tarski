package tarski
package view

/** Conditional given instance of [[Utility]].
  *
  * @return
  *   An instance of [[Utility]], given an instance of [[Constants]].
  */
given Constants => Utility = new Utility

/** Conditional given instance of [[UI]].
  *
  * @return
  *   An instance of [[UI]], given an instance of [[Constants]].
  */
given Constants => UI = new UI

/** Conditional given instance of [[ColorButtons]]. It actually depends on instances of [[Utility]] and [[UI]], which
  * can both be derived from an instance of [[Constants]].
  *
  * @return
  *   An instance of [[ColorButtons]], given an instance of [[Constants]].
  */
given Constants => ColorButtons = new ColorButtons

/** Conditional given instance of [[NameButtons]]. It actually depends on an instance of [[Utility]], which can be
  * derived from an instance of [[Constants]].
  *
  * @return
  *   An instance of [[NameButtons]], given an instance of [[Constants]].
  */
given Constants => NameButtons = new NameButtons

/** Conditional given instance of [[OpButtons]]. It actually depends on instances of [[Utility]] and [[UI]], which can
  * both be derived from an instance of [[Constants]].
  *
  * @return
  *   An instance of [[OpButtons]], given an instance of [[Constants]].
  */
given Constants => OpButtons = new OpButtons

/** Conditional given instance of [[ShapeButtons]]. It actually depends on instances of [[Utility]] and [[UI]], which
  * can both be derived from an instance of [[Constants]].
  *
  * @return
  *   An instance of [[ShapeButtons]], given an instance of [[Constants]].
  */
given Constants => ShapeButtons = new ShapeButtons

/** Conditional given instance of [[SizeButtons]]. It actually depends on instances of [[Utility]] and [[UI]], which can
  * both be derived from an instance of [[Constants]].
  *
  * @return
  *   An instance of [[SizeButtons]], given an instance of [[Constants]].
  */
given Constants => SizeButtons = new SizeButtons
