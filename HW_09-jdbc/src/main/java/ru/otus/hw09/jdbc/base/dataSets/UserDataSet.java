package ru.otus.hw09.jdbc.base.dataSets;

public class UserDataSet extends DataSet {
    private long id;
    private String name;
    private int age;
    private String phone;

    public UserDataSet() {

    }

    public UserDataSet(long id, String name, int age, String phone) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "UserDataSet{" +
                "id=" + id +
                ", name=" + name +
                ", age=" + age +
                ", phone=" + phone +
                '}';
    }

}

