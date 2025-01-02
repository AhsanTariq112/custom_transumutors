# ODF TRANSMUTOR BATCH SERVICE

ODF transmutor batch service is a batch service that performs ETL. It retrieves product data from ODF xml then transform and export it into CSV files.

## Installation dependencies

[Java](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html) version 1.8.0_312.

[Apache Maven](https://maven.apache.org/download.cgi) version 3.6.3

[MySQL Community Server](https://dev.mysql.com/downloads/mysql/) version 8.0.23.

[MongoDB](https://www.mongodb.com/try/download/community) version 4.4.4

## How to get the code

Clone the git repository using the URL on the Git home page:

Run `http://git.etilizepak.com/conquire/ODF-transmutor.git` to clone the project.

Run `cd ODF-transmutor` to switch to the project's directory.

## Setup

In order setup the batch service we need to configure it first.

### Configure

To configure the batch service. Open [application.yml] in `src/main/resources` directory of the project's folder. Provide database connection properties along with the credentials in `spring.datasource` for dev profile (i.e. `spring.profiles=dev`).

By default the application is configured to validate db schema on dev profile (see `spring.jpa.hibernate.ddl-auto=validate`). However, it will only work if the database with name `viewsonic_batch_service` along with all tables already exist in specified MySQl server. If database and its schema doesn't exist then change the property `spring.jpa.hibernate.ddl-auto` to `create`. Similarly, the batch service also uses MongoDB. Provide Mongodb configuration in `spring.data.mongodb`.

The property for output file is `application.output-file-path`. Configure is to specify the location of generated csv files.

### Build

Run `mvn clean package` to install all required dependencies specified in pom.xml file and build the jar.

### Run

Run `java -jar target/viewsonic-transmutor-0.0.1-SNAPSHOT.jar` to execute the jar file.

### Launch Jobs

Use CURL or POSTMAN to hit the following URLs:

> POST
> http://localhost:8080/viewsonic-batch-job/execute?username=test&file-name=input_xml_ODF;
> QUERY PARAMS
> username=test
> file-name=input_xml_viewsonic


### Source Code Overview

In this section implementation details of the project will be dicussed.

#### Project Structure

Packages in the project can be grouped into the following categories:

##### Controller

The package `com.etilize.conquire.viewsonicbatchservice.controller` contains class `ViewsonicBatchJobController.java` that implements endpoint to launch batch jobs.

##### Configuration

These packages contain classes that are responsible for overall configuration of batch jobs. All project related configuration classes are in `com.etilize.conquire.viewsonicbatchservice.config`. `JobConfig.java` implements job's workflow. `StepConfig.java` provides individual step's configuration. `ReaderConfig.java`, `ProcessorConfig.java` and `WriterConfig.java` provides step's reader, processor and writer configurations.

##### Schema
These packages contain classes that are used to retrieve or export data in json or csv. The package `com.etilize.conquire.viewsonicbatchservice.viewsonic` implements schema for viewsonic product.

##### Listeners

The package `com.etilize.conquire.viewsonicbatchservice.job.step` implement listener for steps.


