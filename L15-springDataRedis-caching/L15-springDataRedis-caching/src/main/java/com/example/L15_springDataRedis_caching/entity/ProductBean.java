package com.example.L15_springDataRedis_caching.entity;


public class ProductBean {
    private Long id;
    private String name;
    private Double cost;

    public ProductBean(Long id, String name, Double cost) {
        this.id=id;
        this.name=name;
        this.cost=cost;
    }

    @Override
    public String toString() {
        return "ProductBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                '}';
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


}
