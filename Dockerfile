# Use official Maven image for building
FROM maven:3.9.11-eclipse-temurin-11-alpine AS build

# Set work directory
WORKDIR /app

# Copy only the pom.xml first to cache dependencies
COPY pom.xml .

# Download dependencies (avoids RC1 issues and speeds up rebuilds)
RUN mvn dependency:resolve

# Copy the rest of the source code
COPY src ./src

# Build the WAR file (skip tests for faster build)
RUN mvn clean package -DskipTests

# Use Tomcat 10 (supports Jakarta EE 10 / JSP 3.0)
FROM tomcat:10.1.49-jdk21-temurin-noble

# Remove default ROOT webapp
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy the WAR built from previous stage as ROOT.war
COPY --from=build /app/target/blogapp.war /usr/local/tomcat/webapps/ROOT.war

# Expose default Tomcat port
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]
