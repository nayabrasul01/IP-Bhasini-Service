package com.esic.checklist.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ip_details")
public class IpDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "ip_number", unique = true, nullable = false)
    private String ipNumber;

    private Integer age;

    private String sex;

    @Column(name = "residential_address")
    private String residentialAddress;

    @Column(name = "mobile_number")
    private String mobileNumber;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getIpNumber() { return ipNumber; }
    public void setIpNumber(String ipNumber) { this.ipNumber = ipNumber; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public String getSex() { return sex; }
    public void setSex(String sex) { this.sex = sex; }

    public String getResidentialAddress() { return residentialAddress; }
    public void setResidentialAddress(String residentialAddress) { this.residentialAddress = residentialAddress; }

    public String getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }
}
