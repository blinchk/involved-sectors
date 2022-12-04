# Involved Sectors
Technologies:
* Java 17
* Spring Boot
* Hibernate
* H2 Database
* JUnit + Mockito
* Python
* Bootstrap
* JavaScript

## How to run?
```sh
docker-compose up -d
```
Python script in `Dockerfile` will create `data.sql` for Gradle build and run application on port 8080.
Otherwise, you have to generate SQLs and build application manually.
```bash
python3 generate_sql.py ./data/index.html -o ./src/main/resources/data.sql
./gradlew bootRun
```

## Ports
8080 for Back-End, Front-End stored as a static content
