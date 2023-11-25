# Use a imagem oficial do Ubuntu 20.04 como base
FROM openjdk:17

# Crie um diretório de trabalho no container
WORKDIR /app

# Copie todos os arquivos do projeto para o diretório de trabalho no container
COPY . /app/

# Exponha a porta 8080 (se o aplicativo Spring Boot estiver configurado para usar essa porta)
EXPOSE 8080

# Remova os caracteres de retorno de carro do script mvnw
RUN sed -i 's/\r$//' mvnw

# Torne o script mvnw executável
RUN chmod +x mvnw

# Compile e crie o arquivo JAR do projeto Spring Boot
RUN ./mvnw clean package

# Configure o comando de entrada para iniciar o aplicativo Spring Boot
CMD ["java", "-jar", "target/iriscareapi-0.0.1-SNAPSHOT.jar"]