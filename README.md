## Connector  Introduction

Connector is a Java based backend extension for WaveMaker applications. Connectors are built as Java modules & exposes java based SDK to interact with the connector implementation.
Each connector is built for a specific purpose and can be integrated with one of the external services. Connectors are imported & used in the WaveMaker application. Each connector runs on its own container thereby providing the ability to have it’s own version of the third party dependencies.

## Features of Connectors

1. Connector is a java based extension which can be integrated with external services and reused in many Wavemaker applications.
1. Each connector can work as an SDK for an external system.
1. Connectors can be imported once in a WaveMaker application and used many times in the applications by creating multiple instances.
1. Connectors are executed in its own container in the WaveMaker application, as a result there are no dependency version conflict issues between connectors.

## About mongodb Connector

## Introduction
MongoDB is an open-source document database and leading NoSQL database.MongoDB is a cross-platform, document oriented database that provides, high performance, high availability, and easy scalability. MongoDB works on concept of collection and document.

This connector exposes api to connect Mongo db and do all CRUD, query operations on database from WaveMaker application.

## Prerequisite

1. Mogogo DB
1. Java 1.8 or above
1. Maven 3.1.0 or above
1. Any java editor such as Eclipse, Intelij..etc
1. Internet connection


## Build
You can build this connector using following command
```
mvn clean install
```

## Deploy
You can import connector dist/mongodb.zip artifact in WaveMaker studio application using file upload option.On after uploading into wavemaker, you can update your profile properties such as mongodb host, post and credentials.


## Use Mongodb connector in WaveMaker

```
@Autowired
private MongoDBConnector mongoDBConnector;

String collectionName = "employee";
mongoDBConnector.createCollection(collectionName);

Employee employee = new Employee();
employee.setEmpId(106);
employee.setEmpName("Bobs");
employee.setEmpdesignation("Eng");

mongoDBConnector.addDocument(collectionName, employee);

Employee emp = (Employee) mongoDBConnector.findDocument(collectionName, eq("empid", 106), Employee.class);
Assert.assertEquals("Employee name is not matching", "Bobs", emp.getEmpName());

```

Apart from above apis, there are multiple apis exposed in this connector to intract with mongo database.Please visit connector interface in api module.









