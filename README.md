vertx-rest-demo
===============

This is a demo application to understand how Vert.x works and how to build REST APIs using the same.

How to run:
----------

```
mvn clean package
java -jar target/vertx-rest-demo-1.0.0-SNAPSHOT.jar

```

How to test:
------------

```
curl http://localhost:8081/products
curl http://localhost:8081/products/1
curl http://localhost:8081/products/2
curl http://localhost:8081/product                         #This is to test error response
```