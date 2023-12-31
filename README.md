Asynchronous API with Apache Kafka and Spring Boot HATEOAS
=

This is an example of how a Spring Boot application can behave asynchronously in combination with Apache Kafka.

Setup
-

Checkout the code and run

```
mvn -f api/pom.xml clean install spring-boot:repackage
```

and

```
mvn -f processor/pom.xml clean install spring-boot:repackage
```

Run
-

```
docker-compose up --build
```

Open `http://localhost:8080/` in your Browser, enter any lower case text into the input field and send it: After between
0 and 10s waiting time the uppercase response returns and will be displayed as an alert.

Note that the initial `POST` request to the `api` returns immediately after the Kafka message is published with an HTTP
status code `202 (Accepted)` and the URI of the "Task" via the `Location` header that then gets checked continuously by
the client until the response, also arriving as a Kafka message from the `processor`, is present. To make HATEOAS work
behind a reverse proxy, NGINX includes the `X-Forwarded-Host` and Spring Boot is configured to use
the `forward-headers-strategy: framework` which causes Spring Boot to process the forward headers in
the `org.springframework.web.filter.ForwardedHeaderFilter` that enables the use of
the `org.springframework.hateoas.server.mvc.WebMvcLinkBuilder` to create an absolute URI to a controller method.