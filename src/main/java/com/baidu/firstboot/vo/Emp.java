package com.baidu.firstboot.vo;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;
public class Emp implements Serializable {
    private Integer eid;
    private String name;
    private String email;
    private Integer age;
    private Double sal;
    private Date hiredate;

    public Emp(Integer eid, String name, String email, Integer age, Double sal, Date hiredate) {
        this.eid = eid;
        this.name = name;
        this.email = email;
        this.age = age;
        this.sal = sal;
        this.hiredate = hiredate;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getSal() {
        return sal;
    }

    public void setSal(Double sal) {
        this.sal = sal;
    }

    public Date getHiredate() {
        return hiredate;
    }

    public void setHiredate(Date hiredate) {
        this.hiredate = hiredate;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "eid=" + eid +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", sal=" + sal +
                ", hiredate=" + hiredate +
                '}';
    }
}
