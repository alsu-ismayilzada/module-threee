package com.abb_tech.module_three.repository;

import com.abb_tech.module_three.model.Car;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarListRepository {

    Optional<Car> findById(Long id);
    Optional<Car> findByModel(String model);
    List<Car> findAll();
    void save(Car car);
    void update(Long id, Car car);
    void deleteById(Long id);

}
