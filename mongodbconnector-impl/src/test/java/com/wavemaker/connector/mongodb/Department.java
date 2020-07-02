package com.wavemaker.connector.mongodb;

import java.util.List;

import org.bson.codecs.pojo.annotations.BsonProperty;

import com.wavemaker.connector.model.MongoBaseEntity;

/**
 * @author <a href="mailto:sunil.pulugula@wavemaker.com">Sunil Kumar</a>
 * @since 11/5/20
 */
public class Department extends MongoBaseEntity {

    @BsonProperty(value = "deptid")
    private Integer deptid;

    @BsonProperty(value = "name")
    private String deptName;

    @BsonProperty(value = "employees")
    List<Employee> employees;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id='" + id + '\'' +
                ", deptid=" + deptid +
                ", deptName='" + deptName + '\'' +
                ", employees=" + employees +
                '}';
    }
}
