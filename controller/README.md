# Controller

## Dependency structure

Dependencies on `model` and `constants` are not mentioned (too many to count here).

```scala
React
├── click // used in main
│   ├── Converter.board
│   ├── Converter.ui
│   ├── Handler.boardPos  // updates model via World methods
│   └── Handler.uiButtons // updates model via World methods
│       └── Interpreter.eval
├── tick // used in main
├── move // used in main
└── stop // used in main
```

## Work in progress

- Stay tuned!
