# A sample transcript of the game

Goes like this:

Current state => display text about current state => clicking OK/L/R/Back
triggers state transition => next state => display text => click => next state...

- commit=None f=∀x∃y(Sqr(y)↔Less(x,y)) L=true R=false sel=None
  - Choose your initial commitment left: true, right: false
    - USER clicks left (true)
- commit=true f=∀x∃y(Sqr(y)↔Less(x,y)) L=None R=None sel=None
  - You believe ∀x∃y(Sqr(y)↔Less(x,y)) is true
  - You believe every object [x] satisfies ∃y(Sqr(y)↔Less(x,y))
  - I choose b1 as my counterexample
    - USER clicks OK
- commit=true f=∃y(Sqr(y)↔Less(b1,y)) L=None R=None sel=None
  - You believe ∃y(Sqr(y)↔Less(b1,y)) is true
  - You believe some object [y] satisfies Sqr(y)↔Less(b1,y)
  - Click on a block for [y], then OK:
    - USER clicks on b0
- commit=true f=∃y(Sqr(y)↔Less(b1,y)) L=None R=None sel=b0
  - It's possible for user to click on other blocks too.
    - USER clicks OK
- commit=true f=Sqr(b0)↔Less(b1,b0) L=None R=None sel=None
  - You believe Sqr(b0)↔Less(b1,b0) is true
  - It can be rewritten as
  - Sqr(b0)→Less(b1,b0) ∧ Less(b1,b0)→Sqr(b0)
    - USER clicks OK
- commit=true f=Sqr(b0)→Less(b1,b0)∧Less(b1,b0)→Sqr(b0) L=None R=None sel=None
  - You believe both Sqr(b0)→Less(b1,b0)
  - and Less(b1,b0)→Sqr(b0) are true
  - I choose Sqr(b0)→Less(b1,b0) as false
    - USER clicks OK
- commit=true f=Sqr(b0)→Less(b1,b0) L=None R=None sel=None
  - You believe Sqr(b0)→Less(b1,b0) is true
  - It can be rewritten as ¬Sqr(b0) ∨ Less(b1,b0)
    - USER clicks OK
- commit=true f=¬Sqr(b0)∨Less(b1,b0) L=None R=None sel=None
  - You believe ¬Sqr(b0)∨Less(b1,b0) is true
  - You believe at least one of ¬Sqr(b0) or Less(b1,b0) is true
    - USER clicks OK
- commit=true f=¬Sqr(b0)∨Less(b1,b0) L=¬Sqr(b0) R=Less(b1,b0) sel=None
  - Choose a correct formula:
    - USER clicks left (¬Sqr(b0))
- commit=true f=¬Sqr(b0) L=None R=None sel=None
  - You believe ¬Sqr(b0) is true
    - USER clicks OK
- commit=false f=Sqr(b0) L=None R=None sel=None
  - You believe Sqr(b0) is false
  - You win! Sqr(b0) is false in this world.

- Atomic true/false -> just evaluate, then check against belief -> win/lose
- ¬f true   -> you believe f is false, eval
- ¬f false  -> you believe f is true, eval
- f∧g true  -> you believe both f,g are true, TW chooses false formula
- f∧g false -> you believe at least one of f,g is false, YOU choose false formula
- f∨g true  -> you believe at least one of f,g is true, YOU choose true formula
- f∨g false -> you believe both f,g are false, TW chooses true formula
- f→g true  -> rewrite as ¬f∨g
- f→g false -> rewrite as ¬f∨g
- f↔g true  -> rewrite as (f→g)∧(g→f)
- f↔g false -> rewrite as (f→g)∧(g→f)
- ∀xf true  -> you believe every object satisfies fx, TW chooses false counterexample
- ∀xf false -> you believe some object does not satisfy fx, YOU choose false counterexample
- ∃xf true  -> you believe some object satisfies fx, YOU choose true instance
- ∃xf false -> you believe no object satisfies fx, TW chooses true instance
