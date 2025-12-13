# A sample transcript of the game

1. commit=true turn=you f=∃y(Sqr(y)↔Sml(y)) left=None right=None sel=None
  You believe ∃y (Sqr(y) ↔ Sml(y)) is true
  You believe some object satisfies (Sqr(y): o) ↔ (Sml(y): o)
  You will try to choose an instance that satisfies it
  Choose a block then click OK

    - USER CLICKS ON b0, which changes the state
    - State should not change if clicked position has no block!

2. commit=true turn=you f=∃y(Sqr(y)↔Sml(y)) left=None right=None sel=b0

    - USER clicks OK, which changes the state
    - State should not change if sel is None!

3. commit=true turn=you f=Sqr(b0)↔Sml(b0) left=None right=None sel=None
  You believe (Sqr(b0): o) ↔ (Sml(b0): o) is true
  This can be rewritten as:
  Sqr(b0)→Sml(b0)∧Sml(b0)→Sqr(b0)

    - USER clicks OK, which changes the state
    - State should not change if formula is not being rewritten (NOT, IF, IFF)

4. commit=true turn=you f=Sqr(b0)→Sml(b0)∧Sml(b0)→Sqr(b0) left=None right=None sel=None
  You believe both Sqr(b0) → Sml(b0) and Sml(b0) → Sqr(b0) are true
  I will try to choose a false formula

    - USER clicks OK, which changes the state
    - State should not change if formula does not require choice (AND, OR, ALL, EX)

5. commit=true turn=me f=None left=Sqr(b0)→Sml(b0) right=Sml(b0)→Sqr(b0) sel=None
  I chose Sml(b0) → Sqr(b0) as false

    - USER clicks OK, which changes the state
    - State should not change if left/right are None

6. commit=true turn=you f=Sml(b0)→Sqr(b0) left=None right=None sel=None
  You believe Sml(b0) → Sqr(b0) is true
  Sml(b0) → Sqr(b0) can be written as ¬Sml(b0) ∨ Sqr(b0)

    - USER clicks OK, which changes the state
    - State should not change if formula is not being rewritten (NOT, IF, IFF)

7. commit=true turn=you f=¬Sml(b0)∨Sqr(b0) left=None right=None sel=None
  You believe ¬Sml(b0) ∨ Sqr(b0) is true
  You believe at least one of ¬Sml(b0) and Sqr(b0): o is true
  You will try to choose a true formula

    - USER clicks OK, which changes state
    - State should not change if formula does not require choice (AND, OR, ALL, EX)

8. commit=true turn=you f=¬Sml(b0)∨Sqr(b0) left=¬Sml(b0) right=Sqr(b0) sel=None
  Choose left or right

    - USER clicks on left, which changes state
    - State should not change if left/right are None

9. commit=true turn=you f=¬Sml(b0) left=None right=None sel=None
  You believe ¬Sml(b0) is true

    - USER clicks OK, which changes state
    - State should not change if formula is not being rewritten (NOT, IF, IFF)

10. commit=false turn=you f=Sml(b0) left=None right=None sel=None
  You believe Sml(b0): o is false
  You win! Sml(b0): o is false in this world.

    - State does not change if formula is an atomic sentence.
