package com.nuu.entity;

public class Attractions implements java.io.Serializable{
    private Integer id;
    private String name;
    private String area;
    private String address;
    private String description;
    private String category;
    private String time;
    private String pc1;
    private String pc2;
    private String nc1;
    private String nc2;
    private String tel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPc1() {
        return pc1;
    }

    public void setPc1(String pc1) {
        this.pc1 = pc1;
    }

    public String getPc2() {
        return pc2;
    }

    public void setPc2(String pc2) {
        this.pc2 = pc2;
    }

    public String getNc1() {
        return nc1;
    }

    public void setNc1(String nc1) {
        this.nc1 = nc1;
    }

    public String getNc2() {
        return nc2;
    }

    public void setNc2(String nc2) {
        this.nc2 = nc2;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
