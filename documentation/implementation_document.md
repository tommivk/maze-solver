# Project structure
![packagage_diagram](https://user-images.githubusercontent.com/52420413/176149874-1565b751-90bd-4ccd-af18-07ad72ec4963.jpg)


The domain package contains all of the algorithms used by the application and also the `Rect` class. `Enums` package contains the direction enum used by the app. `UI` package contains the main JavaFX UI class. 

# Time and space complexities
 * ### Kruskal
* ### Wall Follower
* ### Tremaux's

# Comparative performance analysis

The time unit in all of the tables is milliseconds.

## Performance - Maze generation algorithms


 ### Kruskal 
 #### Time taken to generate a maze before the tree merging optimization
 
| Size of the maze | Time in ms |  
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

Generating mazes with the first version of Kruskal's seemed to be reasonably effective when the maze size is 100x100 or smaller. After this the algorithm slowed down considerably. This was due to increased recursive depth when checking if a tree is connected to another tree. I got an excellent tip from my peer reviewer who suggested me to look into optimizing the connect method that merges two trees together. After implementing connect by rank tree merging, the recursive depth is decreased and the algorithm is now able to generate larger mazes a lot faster.

### Time taken to generate a maze



| Size of the maze | Kruskal      | Growing Tree |
| ---------------- | ------------ | ------------ |
| 10x10            | 0.251725     | 0.070566     |
| 20x20            | 0.706168     | 0.256626     |
| 30x30            | 1.689757     | 0.645709     |
| 40x40            | 2.944493     | 1.189884     |
| 50x50            | 4.615844     | 2.444781     |
| 60x60            | 7.913067     | 2.885926     |
| 70x70            | 11.217885    | 4.000658     |
| 80x80            | 14.333877    | 5.277572     |
| 90x90            | 20.114153    | 7.335909     |
| 100x100          | 23.545194    | 8.316754     |
| 110x110          | 29.609004    | 10.221169    |
| 120x120          | 36.052559    | 12.257801    |
| 150x150          | 59.937832    | 18.95352     |
| 200x200          | 128.138225   | 44.763591    |
| 300x300          | 346.296018   | 92.068256    |
| 400x400          | 856.702058   | 235.225345   |
| 500x500          | 1429.235497  | 372.743698   |
| 700x700          | 3540.212188  | 879.311293   |
| 900x900          | 5676.246669  | 1434.749849  |
| 1000x1000        | 8469.4479    | 1856.657765  |
| 1200x1200        | 13853.748893 | 2076.447655  |
| 1300x1300        | 15387.534429 | 3114.726553  |
| 1400x1400        | 21119.372252 | 4084.454037  |
| 1500x1500        | 21129.379283 | 5117.723761  |
| 1600x1600        | 35717.053932 | 5793.871345  |
| 1700x1700        | 41646.901097 | 9201.056663  |


The Growing Tree algorithm seems to be  around 3-5 times faster than Kruskal's when it comes down to generating mazes. When the maze size was bigger than 1700x1700 the java heap space limit(8GB) was reached. 


![chart_maze_generating_algorithms](https://user-images.githubusercontent.com/52420413/175079977-4fd9e1bb-1231-4fb9-b362-b3fb296c5838.png)




## Performance -  Maze solving algorithms

| Size of the maze | Tremaux Kruskal | A\* Kruskal | WallFollower Kruskal | WallFollower GT | Tremaux GT | A\* GT      |
| ---------------- | --------------- | ----------- | -------------------- | --------------- | ---------- | ----------- |
| 10x10            | 0.007524        | 0.040243    | 0.002678             | 0.00207         | 0.003005   | 0.030541    |
| 20x20            | 0.022021        | 0.143188    | 0.008166             | 0.007051        | 0.007657   | 0.103147    |
| 30x30            | 0.047384        | 0.348144    | 0.01847              | 0.01784         | 0.017137   | 0.253534    |
| 40x40            | 0.081868        | 0.629913    | 0.032038             | 0.033348        | 0.030511   | 0.454357    |
| 50x50            | 0.127225        | 1.084514    | 0.054237             | 0.08062         | 0.073672   | 1.035221    |
| 60x60            | 0.206516        | 1.714561    | 0.08664              | 0.093307        | 0.081637   | 1.19318     |
| 70x70            | 0.281701        | 2.525897    | 0.123076             | 0.133253        | 0.109797   | 1.703837    |
| 80x80            | 0.373021        | 3.41557     | 0.165927             | 0.180172        | 0.152864   | 2.249703    |
| 90x90            | 0.478105        | 4.624044    | 0.210051             | 0.236305        | 0.205495   | 3.098376    |
| 100x100          | 0.590175        | 6.03895     | 0.276293             | 0.296823        | 0.26169    | 3.960972    |
| 110x110          | 0.805239        | 7.859638    | 0.357075             | 0.378632        | 0.325413   | 4.876804    |
| 120x120          | 0.921779        | 9.316904    | 0.435776             | 0.476261        | 0.388491   | 6.092008    |
| 150x150          | 1.747999        | 17.781649   | 0.81938              | 0.778121        | 0.65253    | 10.908559   |
| 200x200          | 3.854407        | 40.516214   | 1.884006             | 2.168729        | 1.598464   | 31.519186   |
| 300x300          | 9.148374        | 105.384963  | 4.548169             | 4.568177        | 3.180837   | 72.968034   |
| 400x400          | 16.827932       | 233.477302  | 8.567197             | 9.151081        | 5.506928   | 151.572542  |
| 500x500          | 28.952349       | 393.163252  | 13.232794            | 12.674496       | 9.922584   | 272.317964  |
| 700x700          | 56.997411       | 871.34989   | 27.759292            | 29.342313       | 21.739502  | 614.012318  |
| 900x900          | 88.239939       | 1471.948212 | 46.74965             | 56.562759       | 37.387183  | 958.334374  |
| 1000x1000        | 104.287576      | 1669.731186 | 54.190877            | 64.628278       | 40.525622  | 1172.22563  |
| 1200x1200        | 204.573523      | 2940.683509 | 71.814558            | 105.505178      | 85.965785  | 2138.083588 |
| 1300x1300        | 224.837384      | 3725.946132 | 87.876044            | 120.580731      | 102.017207 | 2530.915706 |
| 1400x1400        | 283.038973      | 5442.730257 | 115.39644            | 129.056103      | 106.308766 | 2785.46836  |
| 1500x1500        | 303.260536      | 5422.905601 | 148.888747           | 119.946986      | 123.519251 | 3622.748842 |
| 1600x1600        | 352.461428      | 5948.67056  | 252.335469           | 208.741089      | 150.912986 | 3986.189516 |
| 1700x1700        | 546.913763      | 6924.862041 | 311.378417           | 271.092344      | 184.39696  | 4320.467968 |

![chart_maze_solving_algorithms](https://user-images.githubusercontent.com/52420413/175077251-65fb233d-3c6f-4c23-8908-a6754a01d06e.png)

The Wall Follower seems to be about as effective on mazes generated by the Kruskal as with mazes generated with the Growing tree algorithm. However the A* and Tremaux seem to perform better with mazes generated with the Growing Tree algorithm. The reason for this is most likely that the Growing Tree usually seems to generate mazes with more long passages. Kruskal on the other hand seems to generate a lot more shorter passages.  



## Possible flaws and improvements
