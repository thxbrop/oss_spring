package com.thxbrop.oss_spring.controller;

import com.thxbrop.oss_spring.Result;
import com.thxbrop.oss_spring.entity.Order;
import com.thxbrop.oss_spring.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class OrderController {
    private final OrderRepository repository;

    @Autowired
    public OrderController(OrderRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/order/user/{userId}")
    public Result<List<Order>> getByUserId(@PathVariable int userId) {
        List<Order> list = ((List<Order>) repository.findAll()).stream()
                .filter(order -> order.userId == userId)
                .collect(Collectors.toList());
        return new Result<>(list);
    }

    @PostMapping("/order")
    public Result<Order> add(
            @RequestParam int userId,
            @RequestParam String cargoes
    ) {
        Order order = new Order();
        order.createdAt = System.currentTimeMillis();
        order.userId = userId;
        order.cargoes = cargoes;
        repository.save(order);
        return new Result<>(order);
    }

    @GetMapping("/order/{id}")
    public Result<Order> getById(@PathVariable("id") int id) {
        Order cargo = repository.findById(id).orElse(null);
        if (cargo != null) return new Result<>(cargo);
        return new Result<>("Cannot find order. ( id = " + id + " )", 1001);
    }
}
