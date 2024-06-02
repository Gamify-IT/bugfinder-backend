# Bugfinder-Backend

The bugfinder backend stores the data of bugfinder instances and communicates results with the overworld backend.

# Development

## Getting started

Make sure you have the following installed:

- Java: [JDK 1.17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) or higher
- Maven: [Maven 3.6.3](https://maven.apache.org/download.cgi)
- Docker: [Docker](https://www.docker.com/)

Firstly, you have to change the `spring.datasource.username` and the `spring.datasource.password` in the `application.properties` file.
Then, if you changed the postgres URL/ port, you also have to change `spring.datasource.url`.

### Run

#### Run with Docker-compose

Start all dependencies with our docker-compose files.
Check the [manual for docker-compose](https://github.com/Gamify-IT/docs/blob/main/dev-manuals/languages/docker/docker-compose.md).

To run the main branch with minimal dependencies use the `docker-compose.yaml` file.\
To run the latest changes on any other branch than `main` use the `docker-compose-dev.yaml` file.

#### Project build

```sh
mvn install
```

in the folder of the project.
Go to the target folder and run

```sh
java -jar bugfinder-service-0.0.1-SNAPSHOT.jar
```

### With Docker

Build the Docker container with

```sh
docker build  -t bugfinder-backend-dev .
```

And run it on port `8000` with

```
docker run -d -p 8000:80 -e POSTGRES_URL="postgresql://host.docker.internal:5432/postgres" -e POSTGRES_USER="postgres" -e POSTGRES_PASSWORD="postgres" --name bugfinder-backend-dev bugfinder-backend-dev
```

To monitor, stop and remove the container you can use the following commands:

```sh
docker ps -a -f name=bugfinder-backend-dev
```

```sh
docker stop bugfinder-backend-dev
```

```sh
docker rm bugfinder-backend-dev
```

To run the prebuild container use

```sh
docker run -d -p 8000:80 -e POSTGRES_URL="postgresql://host.docker.internal:5432/postgres" -e POSTGRES_USER="postgres" -e POSTGRES_PASSWORD="postgres" --name bugfinder-backend ghcr.io/gamify-it/bugfinder-backend:latest
```

### testing database

to setup a database with docker for testing you can use

```sh
docker run -d -p 5432:5432 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=postgres  --rm --name bugfinder-database postgres
```

To stop and remove it, use

```sh
docker stop bugfinder-database
```