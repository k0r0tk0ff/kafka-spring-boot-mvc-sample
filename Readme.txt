Spring Boot 2.3.0 + ApacheKafka project

Additional materials:
https://mkyong.com/spring-boot/spring-boot-hello-world-example-thymeleaf/
https://www.baeldung.com/spring-boot-configuration-metadata
https://spring.io/projects/spring-kafka

Use next variables (optional):
--spring.config.location=classpath:/default.properties
java -Dspring.profiles.active=dev -Dspring.output.ansi.enabled=always -Dspring.application.admin.enabled=true -jar app.jar