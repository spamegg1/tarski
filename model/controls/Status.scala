package tarski
package model

/** Enum to keep track of named objects. We cannot assign the same name to more than one object */
enum Status:
  case Available, Occupied
export Status.*
