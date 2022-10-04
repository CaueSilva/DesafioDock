FROM openjdk:11
EXPOSE 8080
ADD target/desafiodock.jar desafiodock.jar
ENTRYPOINT ["java","-jar","/desafiodock.jar"]