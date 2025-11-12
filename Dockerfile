# Stage 1: Build the WAR file
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

# Copy pom.xml and download dependencies first (for faster caching)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the source code
COPY src ./src

# Build the WAR file
RUN mvn clean package -DskipTests

# Stage 2: Run the WAR with Tomcat
FROM tomcat:10.1-jdk17
WORKDIR /usr/local/tomcat

# Remove the default ROOT app
RUN rm -rf webapps/ROOT

# Copy your WAR from the builder stage to Tomcat
COPY --from=builder /app/target/*.war webapps/ROOT.war

# Expose port 8080 for Render
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]
