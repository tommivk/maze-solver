# Specification

This is a project for bachelor’s in science (bSc) data structures and algorithms project course. The plan is to create an app where it's possible to generate a maze using a maze generation algorithm and to be able to solve the generated maze with at least two different algorithms. Time will be measured so that the speed of different algorithms can be compared. The app will be coded in Java and there will be a GUI made with JavaFX. Besides Java I'm also familiar with Javascript/Typescript and to some extent Rust.

## Algorithms

### Generating the mazes

- Kruskal's algorithm will be used to generate the mazes.
  - Time complexity: O(E log V), V being the number of vertices
  - Space complexity: O(E+V)

### Solving the mazes

For solving the mazes, the following algorithms will be used:

- Trémaux
- Wall follower

Time complexity for both of these should be O(V + E), where V is the number of vertices and E is the number of edges in the graph.

I chose these algorithms as they seem to be specifically made for solving mazes. If there is enough time left i might also implement an A\* or some other algorithm.

### References

https://weblog.jamisbuck.org/2011/1/3/maze-generation-kruskal-s-algorithm
https://www.geeksforgeeks.org/difference-between-prims-and-kruskals-algorithm-for-mst
http://www.astrolog.org/labyrnth/algrithm.htm
