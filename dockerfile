#criação da imagem docker
FROM maven:3.9-eclipse-temurin-21 AS Build

WORKDIR /app

COPY pom.xml

RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -DskipTests

FROM tomcat:11.0-jdk21-temurin

#Remover os arquivos de criação do docker
RUN rm -rf C:\Users\Master\AppData\Local\tomcat\webapss\*

#copia das imagens do docker
COPY --from=Build /app/target\*.war C:\Users\Master\AppData\Local\tomcat\webapss\ROOT.war

EXPOSE 8080

#Execução do docker 
CMD ["catalina.sh", "run"]