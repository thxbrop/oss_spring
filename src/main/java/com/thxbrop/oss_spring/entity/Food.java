package com.thxbrop.oss_spring.entity;

import javax.persistence.*;

@Entity
@Table(name = "food")
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    public String name;

    public double price;

    public String img;

    public String description;
}
