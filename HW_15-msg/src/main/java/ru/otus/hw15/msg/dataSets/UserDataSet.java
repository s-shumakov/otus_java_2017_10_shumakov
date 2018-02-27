package ru.otus.hw15.msg.dataSets;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
public class UserDataSet extends DataSet {

    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PhoneDataSet> phone = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    private AddressDataSet address;

    public UserDataSet() {
    }

    public UserDataSet(String name, Set<PhoneDataSet> phone, AddressDataSet address) {
        this.setId(-1);
        this.name = name;
        this.phone.addAll(phone);
        this.address = address;
    }

    public UserDataSet(String name, PhoneDataSet phone, AddressDataSet address) {
        this.setId(-1);
        this.name = name;
        this.phone.add(phone);
        this.address = address;
    }

    public long getId(){
        return super.getId();
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public Set<PhoneDataSet> getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "UserDataSet{" +
                "id='" + getId() + '\'' +
                ", name='" + name + '\'' +
                ", phone=" + phone +
                '}';
    }
}

