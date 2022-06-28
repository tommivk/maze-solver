# Maze Solver

## Documentation
- [Specification document](https://github.com/tommivk/maze-solver/blob/main/documentation/specification.md)
- [Testing document](https://github.com/tommivk/maze-solver/blob/main/documentation/testing.md)
- [Implementation document](https://github.com/tommivk/maze-solver/blob/main/documentation/implementation_document.md)
- [User quide](https://github.com/tommivk/maze-solver/blob/main/documentation/user_guide.md)

### Weekly reports
- [Week 1](https://github.com/tommivk/maze-solver/blob/main/documentation/weekly_report_1.md)
- [Week 2](https://github.com/tommivk/maze-solver/blob/main/documentation/weekly_report_2.md)
- [Week 3](https://github.com/tommivk/maze-solver/blob/main/documentation/weekly_report_3.md)
- [Week 4](https://github.com/tommivk/maze-solver/blob/main/documentation/weekly_report_4.md)
- [Week 5](https://github.com/tommivk/maze-solver/blob/main/documentation/weekly_report_5.md)
- [Week 6](https://github.com/tommivk/maze-solver/blob/main/documentation/weekly_report_6.md)

## CLI Commands
All of the following commands should be run inside the `MazeSolver` directory.

### Starting the app
```
./gradlew run
```

### Running unit tests
```
./gradlew test
```

### Running integration tests
```
./gradlew integrationTest
```


### Running performance tests
```
./gradlew performanceTest -Dloops=<amount of loops> -DmazeSize=<size of the maze>
```

### Generating Jacoco test report
```
./gradlew jacocoTestReport
```

The html report will be generated to `app/build/jacocoReport/test`

### Generating CheckStyle report
```
./gradlew check
```

The html report will be generated to `app/build/reports/checkstyle`
