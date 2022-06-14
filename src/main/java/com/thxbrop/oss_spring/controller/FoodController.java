package com.thxbrop.oss_spring.controller;

import com.thxbrop.oss_spring.Result;
import com.thxbrop.oss_spring.entity.Food;
import com.thxbrop.oss_spring.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @see <a href="https://restfulapi.cn/">RESTful API</a>
 */
@RestController
public class FoodController {
    private final FoodRepository repository;

    @Autowired
    public FoodController(FoodRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/food")
    public Result<List<Food>> random(@RequestParam(defaultValue = "10") int limit) {
        List<Food> list = ((List<Food>) repository.findAll()).stream()
                .limit(limit)
                .collect(Collectors.toList());
        Collections.shuffle(list);
        return new Result<>(list);
    }

    @PostMapping("/food")
    public Result<Food> add(
            @RequestParam String name,
            @RequestParam double price,
            @RequestParam String description,
            @RequestParam String img
    ) {
        Food food = new Food();
        food.name = name;
        food.price = price;
        food.description = description;
        food.img = img;
        repository.save(food);
        return new Result<>(food);
    }

    @GetMapping("/food/{id}")
    public Result<Food> getById(@PathVariable("id") int id) {
        Food food = repository.findById(id).orElse(null);
        if (food != null) return new Result<>(food);
        return new Result<>("Cannot find food. ( id = " + id + " )", 1001);
    }
}
