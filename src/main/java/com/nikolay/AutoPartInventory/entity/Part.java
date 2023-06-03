package com.nikolay.AutoPartInventory.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "part")
public class Part extends AbstractModel{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "rest_id")
    private int restId;

    @Column(name = "name")
    private String name;

    @Column(name = "qty")
    private int qty;

    @Column(name = "price")
    private float price;

    @ManyToOne(targetEntity = Model.class, fetch = FetchType.EAGER)
    private Model model;

    @ManyToOne(targetEntity = Category.class, fetch = FetchType.EAGER)
    private Category category;

    public Part() {}

    public Part(Model model, Category category, String name, int qty, float price) {
        this.setModel(model);
        this.setCategory(category);
        this.setName(name);
        this.setQty(qty);
        this.setPrice(price);
    }

    public Part(int restId, Model model, Category category, String name, int qty, float price) {
        this.setRestId(restId);
        this.setModel(model);
        this.setCategory(category);
        this.setName(name);
        this.setQty(qty);
        this.setPrice(price);
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

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
