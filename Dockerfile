# --- STAGE 1: build ---
FROM eclipse-temurin:17-jdk-jammy AS builder
WORKDIR /app

# instalar maven (opcional — se não usar mvnw)
RUN apt-get update && apt-get install -y maven

COPY pom.xml .
COPY src ./src
RUN mvn -B package -DskipTests

# --- STAGE 2: runtime ---
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]