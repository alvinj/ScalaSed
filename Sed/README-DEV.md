# For People Developing These Sed Classes

In July, 2019, I needed a new version of the Sed algorithms that return a String rather than writing directly to STDOUT. I also needed some other features, all of which led me to develop the code in the *com.alvinalexander.sed.tostring* package.

The `SedFactory` approach in that package works for now, but may not be a good long-term solution. The problem is that it relies on the three classes taking function parameters that are of the types `Function1`, `Function2`, and `Function3`. If in the future I need another class that has the same signature as any of those current `Function[123]` function signatures, this approach will fail because of type erasure. More specifically, these two class signatures are seen as the same because of type erasure:

```scala
def getSed(source: Source, f:String => Int): SedTrait
def getSed(source: Source, f:Int => Int): SedTrait
```

Therefore, a better long-term solution might be to write the `SedFactory` with this API:

```scala
val rez = SedFactory(source)
            .withStringIntMapFunction(myFunc)
            .run

// TODO add other chainable methods to `SedFactory` to handle the other use-cases.
```




