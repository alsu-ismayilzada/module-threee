package com.abb_tech.module_three.service;

import com.abb_tech.module_three.model.Car;
import com.abb_tech.module_three.payload.request.CarRequest;
import com.abb_tech.module_three.payload.response.CarResponse;

import java.util.List;

public interface CarService {

    CarResponse getById(Long id);
    Car findCarByModel(String model);
    List<CarResponse> getAll();
    void save(CarRequest request);
    void update(Long id, CarRequest request);
    void deleteById(Long id);

}