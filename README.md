## Processor

### Summary

The `processor` service reads, processes and writes records to one of the supported output storage layers.
It is built to handle data on `Product`s and `Price`s. It utilizes Spring Batch for data processing and Spring Boot Web
for the interface or triggering the jobs.

Spring Batch enables chunk-based processing that is utilized in this solution and offers a scalable
design that is able to provide the following characteristics:

* Scalability and Parallel processing
* Retrying failed operations
* Preventing repeated execution on the same data
* Repeatability of operations, scheduling
* Convenient logging and notifications

Although not used in this current project, parallel processing and scalability can be achieved using the following
options available with Spring Batch:

* Parallelization within a single-process - multi-threaded Spring Batch Steps, or Parallel Steps
* Parallelization in a multi-process setup - remote chunking and partitioning.

### Solution design

The following outlines design of the processor solution:

![design_image](/img/diagram.png)

#### Considerations for choices made

##### Output format

Avro is chosen as an output format due to its versatility. It has in-build schema, it's binary, it's widely supported
by databases and data engineering tools and frameworks.

##### S3 as storage layer

Object storage is a convenient storage layer, has a region level scope in AWS,
can be accessible from other services in AWS
(and can even be made publicly accessible) or even with AWS CLI tools,
can have access policies independently attached to the bucket the files are written into.

##### Java based service

Mature programming language with wide ecosystem of libraries and frameworks. Statically typed to help achieve
higher levels of quality.

### Building and running

The project is Gradle-based. Note that you need to have the following environment variables set in order to run
tests and launch application locally:

* `AWS_ACCESS_KEY_ID`
* `AWS_SECRET_ACCESS_KEY`
* `AWS_REGION`

To build and run tests, execute the following:

```bash
./gradlew build
```

This will produce a `.jar` file in `/build/libs/`

To execute, run:

```bash
./gradlew bootRun
```

To send a request to the processor to execute the job for the `Product`s data, run:

```bash
curl --location --request POST 'http://localhost:8080/api/v1/products?filepath=PATH_TO_FILE'
```

where `PATH_TO_FILE` is local filesystem path to a csv file.

To send a request to the processor to execute the job for the `Price`s data, run:

```bash
curl --location --request POST 'http://localhost:8080/api/v1/prices?filepath=PATH_TO_FILE'
```

where `PATH_TO_FILE` is local filesystem path to a csv file.

Note that executing processing on the same file more than once will be prevented.

