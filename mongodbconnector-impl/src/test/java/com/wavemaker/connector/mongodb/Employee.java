package com.wavemaker.connector.mongodb;

import org.bson.codecs.pojo.annotations.BsonProperty;

import com.wavemaker.connector.model.MongoBaseEntity;

/**
 * @author <a href="mailto:sunil.pulugula@wavemaker.com">Sunil Kumar</a>
 * @since 11/5/20
 */
public class Employee extends MongoBaseEntity {


    @BsonProperty(value = "empid")
    private Integer empId;

    @BsonProperty(value = "name")
    private String empName;

    @BsonProperty(value = "designation")
    private String empdesignation;

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpdesignation() {
        return empdesignation;
    }

    public void setEmpdesignation(String empdesignation) {
        this.empdesignation = empdesignation;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", empId=" + empId +
                ", empName='" + empName + '\'' +
                ", empdesignation='" + empdesignation + '\'' +
                '}';
    }
}
