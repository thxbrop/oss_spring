package com.thxbrop.oss_spring.repository;

import com.thxbrop.oss_spring.entity.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {

}
