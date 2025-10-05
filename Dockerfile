FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY target/e-commerce.jar .

#it does not open port for the host it just tell people that
#this container listen on port 8080
EXPOSE 8080
#i may add it in editor config
#or on system not sure yet
#ENV JWT_SECRET_KEY="here we write our secret key"
ENTRYPOINT ["java", "-jar", "/app/e-commerce.jar"]

#CMD ["--spring.profiles.active=prod"]
