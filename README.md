## Assumptions and Approach
* I assumed and stick to the requirements of the task without expanding the scope too much e.g. I didn't try to add extra APIs for deleting, clearing, place order etc or what happens to the cart when an order is placed(finalized).
* I'm utilizing Strategy pattern to handle different types of offers using factory pattern so that it can be easily extended for other kinds of strategies.
* Used 1:1 mapping for offers and items as per my understanding of the requirements.
* I'm pre-loading the data for items and offers as I assumed creating items + offers are beyond the scope of this task and be handled by back office application.
* Flyway is used for database migrations to create the initial schema and pre-populate the items and offers.
* I didn't try to obfuscate the IDs to non-guessable strings or use UUIDs etc. before exposing to the APIs for simplicity for this task.

## Setup Requirements
- Java 21
- Gradle
- Docker and Docker Compose

### Running the app
Before running the app, start the Postgres DB by running:

```
docker-compose up
```
To run the app you can use the following gradle commands
```
./gradlew build
./gradlew test
./gradlew bootRun
```

Once the server is running you can access it at
```
http://localhost:8080
```

The APIs are documented using OpenAPI and can be accessed at
```
http://localhost:8080/swagger-ui/index.html
```
For quick reference, here are the APIs:
* We need to create a Cart first before adding Items: `POST http://localhost:8080/cart` 

* Add/Scan an item to the cart and to get the total: `PUT http://localhost:8080/cart/{cartId}/items/{itemId}`

Note: The database is pre-populated with items and their offers, so that items can be scanned using the `itemId` without needing to create them. I used the example table provided in the requirements for the initial data which is given below:
```
| ItemId | Name   | Price | Offer 
|   1    | Apple  |  30   | 2 for 45
|   2    | Banana |  50   | 3 for 130
|   3    | Peach  |  60   | -
|   4    | Kiwi   |  20   | -