# Model

## Approach

Model is part of a typical Model-View-Controller approach.
However View is a pure, stateless renderer of Model,
so Model also contains some data for the state of the controls.
Thus it's a bit closer to Model-View-ViewModel-Controller actually.
Except that we don't have a separate ViewModel, control state is just built-in to Model.

This is generally not a good idea, however in our case the control state is
so closely related to the pure data types in Model (block, size, shape, tone etc.)
that it makes more sense to use Model for it rather than duplicate the data.
I could separate it into a ViewModel but it would make things needlessly complicated.

## General breakdown of data types

|layout|components|controls|
|:-----|:---------|:-------|
|World |Block     |Controls|
|Board |Names     |Result  |
|Grid  |Formulas  |Status  |
|Pos   |Name      |        |
|      |Sizes     |        |
|      |Shape     |        |
|      |Tone      |        |

## World

For the main application, the goal is the `World` data type:

```scala
World             // layout
├── Board         // layout
|   ├── Pos       // layout
|   ├── Name      // component
|   └── Block     // component
|       ├── Sizes // component
|       ├── Shape // component
|       ├── Tone  // component
|       └── Name  // component
├── Names         // component
|   ├── Name      // component
|   └── Status    // control
├── Formulas      // component
|   └── Result    // control
└── Controls      // control
    ├── Sizes     // component
    ├── Shape     // component
    ├── Tone      // component
    └── Pos       // layout
```

Diagramatically:

![world](https://github.com/user-attachments/assets/7f06c2af-5e0a-4565-86af-6efe7efefd65)

Pictorially on the UI:

![example](https://github.com/user-attachments/assets/fc93b7ca-4c1a-41e6-aa1c-01a7a4729262)

## Interaction with Controller

TODO

## Work in progress

- Stay tuned!
