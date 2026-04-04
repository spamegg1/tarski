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

![world](https://github.com/user-attachments/assets/8fd0c32a-f6ff-46ad-b6e1-4f2570cd0d85)

## Work in progress

- Stay tuned!
