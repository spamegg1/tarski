package tarski
package model

enum Select[+T]:
  case Off
  case Wait
  case On(t: T)
export Select.*
