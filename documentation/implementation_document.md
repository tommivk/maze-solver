# Project structure

# Time and space complexities
 * ### Kruskal
* ### Wall Follower
* ### Tremaux's

# Comparative performance analysis

## Performance

 ### Kruskal 
 #### Time taken to generate a maze before the tree merging optimization
 
| Size of the maze | Average time in milliseconds |  
| :---:            | :---:        |
|    10x10         | 2            |
|     20x20       |  5           |
|      30x30       |  12          |
|      40x40       |   23          |
|      50x50    |   47          |
|       60x60     |   94         |
|      70x70      |       212      |
|       80x80     |      336       |
|       90x90     |       526      |
|        100x100    |      884       |
|       110x110    |  1411           |
|       120x120    |     2836        |
|        150x150   |    8798         |
|        200x200   |   82911         |
|        300x300   |      123192       |
|        400x400   |      986674       |

Generating mazes with the first version of Kruskal's seemed to be reasonably effective when the maze size is 100x100 or smaller. After this the algorithm slowed down considerably. This was due to increased recursive depth when checking if a tree is connected to another tree. I got an excellent tip from my peer reviewer who suggested to looking into optimizing the connect method that merges two trees together. After implementing connect by rank tree merging, the recursive depth is decreased and the algorithm is now able to generate larger mazes a lot faster.

 #### Time taken to generate a maze after the tree merging optimization
 
| Size of the maze | Average time in milliseconds |  
| :---:            | :---:        |
|    10x10         |    1        |
|     20x20       |     2    |
|      30x30       |     4    |
|      40x40       |     6     |
|      50x50    |      10     |
|       60x60     |       14    |
|      70x70      |      22    |
|       80x80     |     30       |
|       90x90     |       39    |
|        100x100    |      46    |
|       110x110    |      56      |
|       120x120    |      69     |
|        150x150   |      109     |
|        200x200   |     218     |
|        300x300   |     707     |
|        400x400   |       1680   |
|        500x500   |       3206   |
|        700x700   |       5853   |
|        900x900   |       11222   |
|        1000x1000   |      15260    |
     


 ### Growing Tree
 #### Time taken to generate a maze
 
| Size of the maze | Average time in milliseconds |  
| :---:            | :---:        |
|    10x10        |       0.38           |
|      20x20      |         0.85         |
|      30x30      |         1.64         | 
|      40x40      |         2.25         |
|      50x50      |           3.3       |
|      60x60      |          4.48        |
|      70x70      |           5.8    |
|       80x80     |          7.56      |
|       90x90     |          10.5   |
|        100x100    |        11.8   |
|       110x110    |         15.1   |
|       120x120    |           17.1 |
|        150x150   |           28.9  |
|        200x200   |          48.2  |
|        300x300   |          108.5   |
|        400x400   |          306 |
|        500x500   |       458   |
|        700x700   |       1536   |
|        900x900   |       2248   |
|        1000x1000   |      3232    |
|        1200x1200   |        8938  |

The Growing Tree algorithm seems to be  a lot faster than Kruskal's when it comes down to generating mazes. However when the maze size was bigger than 1200x1200 the java heap space limit was reached. 


## Possible flaws and improvements
