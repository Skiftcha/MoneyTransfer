# MoneyTransfer test application

## To build and run unit tests
```
./gradlew build
```

## To run application
```
./gradlew run
```
Runs application on 8080 port

## To run intergation tests while application is running
```
./gradlew integrationTest
```
Runs integration tests accessing api via 8080 port
<br>
For multiple test runs you can use ```--rerun-tasks``` option
```
./gradlew --rerun-tasks integrationTest
```
