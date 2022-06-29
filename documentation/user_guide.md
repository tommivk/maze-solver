* Download `mazesolver.jar` file from the [final release](https://github.com/tommivk/maze-solver/releases/tag/v1.0.0)

* Start the app with `java -jar mazesolver.jar`

![user_guide_1](https://user-images.githubusercontent.com/52420413/176131708-0ea0e284-a67c-425d-ae04-64995d432b93.png)
The app opens up to a maze generation selection view where it's possible to change the delay of the animation and the size of the maze.
30 is currently the largest maze that will fit the application window. By clicking either the `Kruskal's` or `Growing tree` buttons a new maze is generated
by using the selected algorithm.

![user_guide_3](https://user-images.githubusercontent.com/52420413/176131732-5e274eac-410f-4a66-9f83-f7fb98386c09.png)
After the maze has been generated, a main view will open. By changing the value in `Delay in ms` field, it is possible to change the
speed of the maze solving algorithm animation. By clicking `Wall Follower` `Tremaux's` or `A*` buttons the maze is solved with the selected algorithm. 

![user_guide_4](https://user-images.githubusercontent.com/52420413/176131737-2b7b01fe-f96d-4e84-a455-597da7c63fc7.png)
The amount of time it took to solve the maze is shown in a table on the right of the maze.   
The green square represents the currently active square. The red squares mean that the square is visited by the algorithm and the color gets darker the more often the square has been visited.  


Clicking `Clear maze` will clear all the backgrounds of the maze. The maze will also clear after clicking any of the maze solving algorithm buttons again. Clicking `New maze` will take you back to the maze generation selection. 
