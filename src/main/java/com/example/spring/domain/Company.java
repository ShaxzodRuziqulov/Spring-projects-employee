package com.example.spring.domain;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "company")
public class Company implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "area_id",referencedColumnName = "id")
    private Area area;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
}
