# View

## Dependency structure

Everything is in `view`, except `Converter`, which is in `controller`.
Obviously a lot of stuff depends on members of `model` and `constants`.
Those are not explicitly mentioned (too many to count).

```scala
Render
├── selectedPos
│   └── Converter
├── blocksOnBoard
│   ├── Converter
│   └── Imager
├── renderUI
│   ├── OpButtons
│   │   ├── Utility
|   |   |   └── Converter
│   │   └── UI
│   ├── NameButtons
│   │   ├── Converter
│   │   └── Utility
|   |   |   └── Converter
│   ├── SizeButtons
│   │   ├── Utility
|   |   |   └── Converter
│   │   └── UI
│   ├── ColorButtons
│   │   ├── Utility
|   |   |   └── Converter
│   │   └── UI
│   ├── ShapeButtons
│   │   ├── Utility
|   |   |   └── Converter
│   │   └── UI
│   └── selectedBlock
│       └── Imager
└── formulaDisplay
    └── Imager
```

## Given structure

Everything is derived from a `given` instance of the `Constants` class.
If a given `Constants` is present, all else can be summoned.

```scala
                                  Constants
                    ┌─────────────────┴──────────────────┐
     ┌────────── Utility                                 UI
     │              ├───────────┬───────────┬────────────┤
 NameButtons  ColorButtons  OpButtons  ShapeButtons  SizeButtons
     └──────────────┴───────────┼───────────┴────────────┘
                              Render
```

## Design ideas

View just takes an instance of `World` and converts it to an image.

## Work in progress

- Stay tuned!
