# Challenge

## :computer: How to execute

**IMPORTANT**: I wasn't able to make the producer application (localhost:9000/start) to publish messages on kafka. I
monitored the topics with Conduktor and no message was being published, so I created my own publisher in class
_com.abraham.payments.configuration.TestProducers_. It starts with the application and keeps publishes 3 payments with
random data every second:

* An online payment
* An offline payment
* A failing payment (payment whose processing will cause a database failure) so error logging integration can be tested

First, to **build** the application, in the root directory execute:

```./mvnw clean install```

To run the application execute the following:

Move to the 'docker' directory and run:

```docker-compose up```

wait until all dockers have started. Then move to the 'payments-boot' module and run:

```../mvnw spring-boot:run```

that would start the application and start producing and consuming messages.

**IMPORTANT**: You will see some failures during application execution. This is normal as we are purposely producing
payments whose processing will cause these failures as stated at the beginning of this section

Aplication was built with:

* OpenJDK Runtime Environment 18.9 (build 11+28)
* Apache Maven 3.8.3

## :memo: Notes

_Some notes or explanation of your solution..._

### Architecture

Application is built based on Hexagonal architecture with 3 layers (from inside to outside):

* domain: which hosts the business logic in the domain model
* application: represents the use-cases of the application. It uses the domain to solve the business-logic. It
  orchestrates the flow of the application but without coupling to the infrastructure solutions.
* adapters: provide the integration of the infrastructure solutions (kafka, rest, postgresql ...) with the use-cases

I separated the adapters, having the 'payments-event' as input adapter consuming the events from kafka and
'payments-infrastructure' as the output adapter with the database and restclients integrations.

The 'payments-boot' module purpose is to configure the application startup and to hold the integration tests (some of
which require of all the layers' dependencies)

I made them different modules as this makes very explicit the boundaries of each layer and the dependency tree. As each
module has its dependencies defined in the pom, this prevents using them in the wrong place (i.e. capturing and managing
database exceptions in the application layer). Also, due to dependency graph, this leads to the creation of interfaces
in the inner layers that outside layers has to implement allowing not only better unit-testing but also the Dependency
Inversion Principle

### Testing

Based on the test pyramid I developed unit and integration tests. Each class has its own unit tests representing the
different use-cases. Regarding integration testing there is a test to test database queries and model definition. Also,
there is a whole-application integration test to test application startup, kafka consuming functionality, restclients
invocation ... There is no regression testing as it would make more sense outside the microservice (even though the
producers that generate the data are kind of a regression test)
Rest integrations (third-party payment validation API and error logging API) could be tested with some kind of API
contract approach

## :pushpin: Things to improve

_If u have more time or want to improve something..._

If I had more time I would:

* Add Mapstruct (https://mapstruct.org/) for mappings instead of writing them manually
* Add circuit breaker with resilience4j (https://resilience4j.readme.io/docs/circuitbreaker) for the external calls
* Add SpotBugs and Sonar analysis maven plugins on mvn compile task (as part of the shift-left movement)
* Improve data quality (enum check, negative integer values, non-nullable ...)
* Move host and path fields of restClients to properties file
