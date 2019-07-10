package com.lvh.phan6.model;

public class Student {
    private int id;
    private String mshs;
    private String tenHS;
    private String lop;

    public Student() {
    }

    public Student(int id, String mshs, String tenHS, String lop) {
        this.id = id;
        this.mshs = mshs;
        this.tenHS = tenHS;
        this.lop = lop;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMshs() {
        return mshs;
    }

    public void setMshs(String mshs) {
        this.mshs = mshs;
    }

    public String getTenHS() {
        return tenHS;
    }

    public void setTenHS(String tenHS) {
        this.tenHS = tenHS;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", mshs='" + mshs + '\'' +
                ", tenHS='" + tenHS + '\'' +
                ", lop='" + lop + '\'' +
                '}';
    }
}
