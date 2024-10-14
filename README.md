# Game Sales Application
Game Sales Application is a Java Spring Boot application with Relational Database.
It is designed and developed using MySQL. However, it can be replaced with other relational database easily with minimal effort.
## Requirements
Requirements for building and running this application:
- JDK 17
- Maven (and access to Maven repository or the like e.g. Artifactory)
<img src="doc/java.PNG" width="1000"/>

## Steps
### Clone this repository
>
> git clone https://github.com/kievondy/gamesales.git
>
### Database setup
- This application requires connectivity to MySQL database. Either a new or existing database is needed.
- To create the tables (and indexes), SQL script (create-db.sql) can be found in src/main/resource directory.
- **Please execute the script against the database that will be used for this application**
- Configuration for the database connectivity can be found in src/main/resources/application.properties file.
- **Please edit and amend accordingly, especially for datasource url, username and password**
### Build the application
To build, go to root directory of gamesales and run 
>
> mvn clean install
>
<img src="doc/mvnbuild.PNG" width="1000"/>

### Starting up the application
To start the application, go to gamesales/target directory and run:
>
> java -jar gamesales-0.0.1-SNAPSHOT.jar
>
As an alternative to MySQL, this application also has the capability to run using H2 in-memory database (without MySQL). To run this application using H2 database instead, run the following:
>
> java -Dspring.profiles.active=h2 -jar gamesales-0.0.1-SNAPSHOT.jar
>

To check H2 database: (user/pw: sa/password)
- http://localhost:8080/h2-console/login.jsp

### Using the application
For convenience purpose, Swagger is available at the following URL:
- http://localhost:8080/swagger-ui/index.html
<img src="doc/swagger.PNG" width="1000"/>

Available end points:
- http://localhost:8080/task1/import (POST)
- http://localhost:8080/task3/getGameSales (GET)
- http://localhost:8080/task3/getGameSalesBySaleDate (GET)
- http://localhost:8080/task3/getGameSalesWhereSalePriceLessThan (GET)
- http://localhost:8080/task3/getGameSalesWhereSalePriceMoreThan (GET)
- http://localhost:8080/task4/getDailyTotalSales (GET)
- http://localhost:8080/task4/getDailyTotalSalesByGameNo (GET)

Alternative import end point - Asynchronous import is implemented, where users have the option to upload CSV file and does not have to wait, especially for big files (e.g. 1 million rows). The file is being processed at the background asynchronously. The end point is available at:
- http://localhost:8080/task1/importAsync (POST)

Sample data are available in src/main/resources/sample-data directory. Alternatively, users have the flexibilty to generate sample data from the following end point:
- http://localhost:8080/task5/generateCsv (GET)

## Performance
When it is specified that queries must be completed under 50ms, it is found to be a bit ambiguous (apologies!), because there are a few factors affecting performance:
- The code (of course)
- Data set
- Hardware
- Network (probably not so much effect in currect scenario)

To show that that performance is affected by data set, tests are done against 2 different sets:
- Queries against 10k records. Please see in screenshot below line "Imported 10000 records", then several query results after that.
- Queries against 1 million records. Please see in screenshot below line "Imported 1000000 records", then several query results after that.

Performance Result:
<img src="doc/performance.PNG" width="1000"/>
