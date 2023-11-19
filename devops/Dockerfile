# Utiliza una imagen base de OpenJDK 20
FROM openjdk:20-jdk-slim

# Copia el archivo JAR de tu proyecto Spring Boot al contenedor
COPY ssmu-api-0.0.1-SNAPSHOT.jar /usr/src/devops/ssmu-api-0.0.1-SNAPSHOT.jar

# Establece el directorio de trabajo en /app
WORKDIR /usr/src/devops

# Expone el puerto 8080 que utiliza Spring Boot
EXPOSE 8080

# Comando para ejecutar la aplicación Spring Boot
ENTRYPOINT ["java", "-jar", "/usr/src/devops/ssmu-api-0.0.1-SNAPSHOT.jar"]




