package com.nikolay.AutoPartInventory.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "brand")
public class Brand extends AbstractModel{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "rest_id")
    private int restId;

    @Column(name = "name")
    private String name;

    public Brand() {}

    public Brand(String name) {
        this.setName(name);
    }

    public Brand(int restId, String name) {
        this.setRestId(restId);
        this.setName(name);
    }

    public int getRestId() {
        return restId;
    }

    public void setRestId(int restId) {
        this.restId = restId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
