# logic-tut

*HackDay Project: Logic and Relations Programming with Clojure's core.logic*


## Resources

* [API Reference](https://clojure.github.io/core.logic/)
* [core.logic Primer](https://github.com/clojure/core.logic/wiki/A-Core.logic-Primer)
* [core.logic Examples](https://github.com/clojure/core.logic/wiki/Examples)
* [Clojure translation of Reasoned Schemer examples](https://github.com/matlux/the-reasoned-schemer-clojure/blob/master/src/reasoned_schemer_clj/core.clj)


## Usage

```
$ lein repl
```
```clj
logic-tut.dev=> (simple/sensors-for-mission
                  simple/sat-graph
                  "Landsat")
("TM" "TM" "ETM")
```

## License

Copyright © 2016 David Hill, Duncan McGreggor
