visit
=====

Graph visit problem solver implementation

# Usage

A small set of fluent APIs make visiting `Graph` instances more fun!

Given a generic Graph instance, users can visit it applying the [Breadth-First Search](http://en.wikipedia.org/wiki/Breadth-first_search) algorithm:

```
import static org.nnsoft.trudeau.visit.GraphVisitor.visit;

import org.nnsoft.trudeau.api.Graph;

…

Graph<V> g; // it can be whatever kind of Graph
V source; // the source node where the visit algorithm has to start

Graph<BaseLabeledVertex, BaseLabeledEdge> graphVisit =
    visit( g ).from( source ).applyingBreadthFirstSearch();

```

or  applying the [Depth-First Search](http://en.wikipedia.org/wiki/Depth-first_search) algorithm:

```
import static org.nnsoft.trudeau.visit.GraphVisitor.visit;

import org.nnsoft.trudeau.api.Graph;

…

Graph<V> g; // it can be whatever kind of Graph
V source; // the source node where the visit algorithm has to start

Graph<BaseLabeledVertex, BaseLabeledEdge> graphVisit =
    visit( g ).from( source ).applyingBreadthFirstSearch();

```

both algorithm accept also a `org.nnsoft.trudeau.visit.GraphVisitHandler` which allow customize the visit by intercepting the visit step-by-step