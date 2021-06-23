This is Kalaha Game as REST api using spring boot, in memory database h2.

To build the project - 
```
mvn clean install
```

To run the project - 
```
mvn spring-boot:run
```

To create a new game -
```
curl --header "Content-Type: application/json" --request POST http://localhost:8080/games
```

To make a move -
```
curl --header "Content-Type: application/json" --request PUT http://<host>:<port>/games/{gameId}/pits/{pitId}
```



