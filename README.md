Developed and tested the "spring-boot-starter-swagger-registry" imported in the example project. "--spring.profiles.active=swagger-ui" profile to active in order to run in example the SwaggerConfiguration registry. Miss only to add typescript templates. You have to write in your application.properties (in this is the example project) "the swagger.yaml" property and specify the jar yaml dependency (you can add more artifacts with a "," to separate the names in the property value).
Run example project with the "SpringBootApplicationRunner" main class in Eclipse or in the jar with "java -jar swagger-example-1.0-SNAPSHOT.jar --spring.profiles.active=swagger-ui"
