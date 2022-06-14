package com.thxbrop.oss_spring.repository;

import com.thxbrop.oss_spring.entity.Food;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends CrudRepository<Food, Integer> {

}
