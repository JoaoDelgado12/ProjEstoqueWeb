#criação da imagem docker
FROM tomcat:11.0-jdk21-temurin

#Remover os arquivos de criação do docker
RUN rm -rf C:\Users\Master\AppData\Local\tomcat\webapss\*


#copia das imagens do docker
COPY target\app.war C:\Users\Master\AppData\Local\tomcat\webapss\ROOT.war

EXPOSE 8080

#Execução do docker 
CMD ["catalina.sh", "run"]