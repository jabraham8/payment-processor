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

_Some notes or explaination of your solution..._

## :pushpin: Things to improve

_If u have more time or want to improve somthing..._

* Add Mapstruct for mappings
* Add circuit breaker with resilience4j to external calls
* Add SpotBugs and Sonar analysis maven plugins on mvn compile tasks (as for the shift-left movement)
* Configure database pool, rest call pools and timeouts properly
* Improve data quality (enum check, negative integer values, non-nullable ...)
* Move host and path fields of restClients to properties file
