package com.wavemaker.connector.mongodb;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.wavemaker.connector.model.MongoBaseEntity;

import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MongoTestConfiguration.class)
@WebAppConfiguration
public class MongoDBConnectorTest {

    @Autowired
    private MongoDBConnector mongoDBConnector;

    @Test
    public void testConnection() {
        boolean status = mongoDBConnector.testConnection();
        System.out.println("connection status: " + status);
    }

    @Test
    public void listCollectionNames() {
        List<String> collections = mongoDBConnector.listCollections();
        System.out.println(collections);
    }

    @Test
    public void createCollection() {
        String collectionName = "employee" + getRandonNumber();
        mongoDBConnector.createCollection(collectionName);
        List<String> collections = mongoDBConnector.listCollections();
        System.out.println(collections);
        boolean found = false;
        for (String collection : collections) {
            if (collection.equals(collectionName)) {
                found = true;
            }
        }
        if (!found) {
            Assert.fail("Collection was not created in mongo db " + collectionName);
        }
    }

    @Test
    public void dropCollection() {
        String collectionName = "employee" + getRandonNumber();
        mongoDBConnector.createCollection(collectionName);
        Page<MongoBaseEntity> collection = mongoDBConnector.listDocuments(collectionName, Employee.class, PageRequest.of(0, 20));
        if (collection.hasNext()) {
            mongoDBConnector.dropCollection(collectionName);
        }
    }

    @Test
    public void getCollection() {
        String collectionName = "employee" + getRandonNumber();
        mongoDBConnector.createCollection(collectionName);

        Employee employee1 = new Employee();
        employee1.setEmpId(106);
        employee1.setEmpName("Bobs");
        employee1.setEmpdesignation("Eng");

        Employee employee2 = new Employee();
        employee2.setEmpId(107);
        employee2.setEmpName("nick");
        employee2.setEmpdesignation("marketing");

        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);

        mongoDBConnector.addDocuments(collectionName, employees);

        Page<MongoBaseEntity> collection = mongoDBConnector.listDocuments(collectionName, Employee.class, PageRequest.of(0, 20));
        System.out.println(collection.getContent());
    }

    @Test
    public void addCollection() {
        String collectionName = "employee" + getRandonNumber();
        mongoDBConnector.createCollection(collectionName);

        Employee employee = new Employee();
        employee.setEmpId(106);
        employee.setEmpName("Bobs");
        employee.setEmpdesignation("Eng");

        mongoDBConnector.addDocument(collectionName, employee);

        Employee emp = (Employee) mongoDBConnector.findDocument(collectionName, eq("empid", 106), Employee.class);
        Assert.assertEquals("Employee name is not matching", "Bobs", emp.getEmpName());
    }

    @Test
    public void addCollections() {
        String collectionName = "employee" + getRandonNumber();
        mongoDBConnector.createCollection(collectionName);

        Employee employee1 = new Employee();
        employee1.setEmpId(106);
        employee1.setEmpName("Bobs");
        employee1.setEmpdesignation("Eng");

        Employee employee2 = new Employee();
        employee2.setEmpId(107);
        employee2.setEmpName("nick");
        employee2.setEmpdesignation("marketing");

        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);

        mongoDBConnector.addDocuments(collectionName, employees);
    }


    @Test
    public void deleteDocument() {
        String collectionName = "employee" + getRandonNumber();
        System.out.println(collectionName);
        mongoDBConnector.createCollection(collectionName);

        Employee employee = new Employee();
        employee.setEmpId(100);
        employee.setEmpName("Bobs");
        employee.setEmpdesignation("Eng");

        mongoDBConnector.addDocument(collectionName, employee);

        Employee emp = (Employee) mongoDBConnector.findDocument(collectionName, eq("empid", 100), Employee.class);
        Assert.assertEquals("Employee name is not matching", "Bobs", emp.getEmpName());

        mongoDBConnector.deleteDocument(collectionName, employee);
        Employee emp1 = (Employee) mongoDBConnector.findDocument(collectionName, eq("empid", 100), Employee.class);

        Assert.assertNull(emp1);

    }

    @Test
    public void findByDocumentId() {
        String collectionName = "employee" + getRandonNumber();
        System.out.println(collectionName);
        mongoDBConnector.createCollection(collectionName);

        Employee employee = new Employee();
        employee.setEmpId(100);
        employee.setEmpName("Bobs");
        employee.setEmpdesignation("Eng");

        mongoDBConnector.addDocument(collectionName, employee);

        Employee emp = (Employee) mongoDBConnector.findDocument(collectionName, eq("empid", 100), Employee.class);
        Assert.assertNotNull(emp);

        Employee resultemp = (Employee) mongoDBConnector.getDocumentById(collectionName, employee.getId().toHexString());
        Assert.assertNotNull(resultemp);
    }


    @Test
    public void updateDocument() {

        String collectionName = "employee";
        mongoDBConnector.createCollection(collectionName);

        Employee employee = new Employee();
        employee.setEmpId(100);
        employee.setEmpName("Bobs");
        employee.setEmpdesignation("Eng");

        mongoDBConnector.addDocument(collectionName, employee);

        Employee emp = (Employee) mongoDBConnector.findDocument(collectionName, eq("empid", 100), Employee.class);
        employee.setEmpName("john");
        mongoDBConnector.updateDocument(collectionName, emp, employee);

    }


    @Test
    public void testMongodJavaMapping() {
        ConnectionString connectionString = new ConnectionString("mongodb://172.19.0.6:27017");
        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                pojoCodecRegistry);
        MongoClientSettings clientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .codecRegistry(codecRegistry)
                .build();

        MongoClient mongoClient = MongoClients.create(clientSettings);
        MongoDatabase db = mongoClient.getDatabase("mydb");
        MongoCollection<Department> customers = db.getCollection("customer", Department.class);
        FindIterable<Department> customersIterator = customers.find();
        MongoCursor<Department> cursor = customersIterator.cursor();
        while (cursor.hasNext()) {
            Department customer = cursor.next();
            System.out.println(customer);
        }
    }

    @Test
    public void createSpringContext() {
       String WMCONNECTOR_SPRING_XML = "wmconnector-spring.abc";
       ApplicationContext connectorApplicationContext = new ClassPathXmlApplicationContext(new String[]{WMCONNECTOR_SPRING_XML});

    }

    private Integer getRandonNumber() {
        return new Random().nextInt(90) + 10;
    }
}