package com.nikolay.AutoPartInventory.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "model")
public class Model extends AbstractModel{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "rest_id")
    private int restId;

    @Column(name = "name")
    private String name;

    @ManyToOne(targetEntity = Brand.class, fetch = FetchType.EAGER)
    private Brand brand;

    public Model() {}

    public Model(Brand brand, String name) {
        this.setBrand(brand);
        this.setName(name);
    }

    public Model(int restId, Brand brand, String name) {
        this.setRestId(restId);
        this.setBrand(brand);
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

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
}
