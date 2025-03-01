# Build Angular
FROM node:23 AS ng-build

WORKDIR /src

RUN npm i -g @angular/cli

COPY weather-client/public public
COPY weather-client/src src
COPY weather-client/*.json .

RUN npm ci && ng build

# Build Spring Boot
FROM openjdk:23-jdk AS j-build

WORKDIR /src

COPY weather-server/.mvn .mvn
COPY weather-server/src src
COPY weather-server/mvnw .
COPY weather-server/pom.xml .

# Copy angular files over to static
COPY --from=ng-build /src/dist/weather-client/browser/ src/main/resources/static

RUN chmod a+x mvnw && ./mvnw package -Dmaven.test.skip=true

# Copy the JAR file over to the final container
FROM openjdk:23-jdk 

WORKDIR /app

COPY --from=j-build /src/target/weather-server-0.0.1-SNAPSHOT.jar weatherapp.jar

ENV PORT=8080

EXPOSE ${PORT}

SHELL [ "/bin/sh", "-c" ]
ENTRYPOINT SERVER_PORT=${PORT} java -jar weatherapp.jar