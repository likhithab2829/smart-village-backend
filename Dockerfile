FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .

RUN mvn -B -ntp -Dmaven.wagon.http.retryHandler.count=5 dependency:go-offline

COPY src ./src

RUN mvn -B -ntp -Dmaven.wagon.http.retryHandler.count=5 clean package -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8092

CMD ["sh", "-c", "java -jar app.jar --server.port=${PORT:-8092}"]