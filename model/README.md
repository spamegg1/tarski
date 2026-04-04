# Model

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

![world](https://github.com/user-attachments/assets/039365bc-b5f4-4815-8720-ec340c0733f4)

## Work in progress

- Stay tuned!
