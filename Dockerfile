# Imagen base con Java 24
FROM amazoncorretto:24-alpine

# Define el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el JAR al directorio de trabajo y lo renombra
COPY target/demo-0.0.1-SNAPSHOT.jar crud-PC03-v1.jar

# Expone el puerto 8080 (por defecto en Spring Boot)
EXPOSE 8086

# Comando de entrada
ENTRYPOINT ["java", "-jar", "crud-v1.jar"]