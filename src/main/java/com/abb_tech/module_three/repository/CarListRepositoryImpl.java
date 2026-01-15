package com.abb_tech.module_three.repository;

import com.abb_tech.module_three.model.Car;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CarListRepositoryImpl implements CarListRepository{

    List<Car> carList = new ArrayList<>();

    @Override
    public Optional<Car> findById(Long id) {
        return carList.stream()
                .filter(car -> car.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<Car> findByModel(String model) {
        return Optional.empty();
    }

    @Override
    public List<Car> findAll() {
        return carList;
    }

    @Override
    public void save(Car car) {
        carList.add(car);
    }

    @Override
    public void update(Long id, Car car) {
        var carToUpdate = findById(id);
        carList.set(carList.indexOf(carToUpdate.get()), car);
    }

    @Override
    public void deleteById(Long id) {
        carList.removeIf(car -> car.getId().equals(id));
    }
}
