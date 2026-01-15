package com.abb_tech.module_three.service.impl;

import com.abb_tech.module_three.config.AppConfig;
import com.abb_tech.module_three.enums.CarAction;
import com.abb_tech.module_three.exception.NotFoundException;
import com.abb_tech.module_three.log.CarLogger;
import com.abb_tech.module_three.model.Car;
import com.abb_tech.module_three.payload.request.CarRequest;
import com.abb_tech.module_three.payload.response.CarResponse;
import com.abb_tech.module_three.repository.CarListRepository;
import com.abb_tech.module_three.service.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarLogger logger;
    private final CarListRepository carListRepository;
    private final AppConfig appConfig;

    @Value("${app.env}")
    private String appEnv;

    private Car getCarById(Long id) {
        return carListRepository.findById(id)
                .orElseThrow(()-> new NotFoundException(String.format("Car with id %s not found", id)));
    }

    // ResponseStatusException example ucun yaradilib
    public Car findCarByModel(String model) {
        return carListRepository.findByModel(model)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Car with model " + model + " not found"
                        )
                );
    }

    @Override
    public CarResponse getById(Long id) {
        if("prod".equals(appEnv)){
            log.info("App is running on {} profile", appEnv);
        }
        var car = getCarById(id);
        logger.log(CarAction.GET_BY_ID, car);
        return new CarResponse(car.getId(), car.getBrand(), car.getColor(), car.getModel());
    }

    @Override
    public List<CarResponse> getAll() {
        if("dev".equals(appConfig.getEnv())){
            log.info("App is running on {} profile", appConfig.getEnv());
        }
        var cars = carListRepository.findAll();
        return cars.stream()
                .map(car -> new CarResponse(car.getId(), car.getBrand(), car.getColor(), car.getModel()))
                .toList();
    }

    @Override
    public void save(CarRequest request) {
        logger.log(CarAction.CREATE, Car.builder().brand(request.brand())
                .model(request.model())
                .color(request.color())
                .build());
        carListRepository.save(Car.builder().brand(request.brand()).color(request.color()).model(request.model()).build());
    }

    @Override
    public void update(Long id, CarRequest request) {
        logger.log(CarAction.UPDATE, Car.builder().brand(request.brand())
                .model(request.model())
                .color(request.color())
                .build());
        carListRepository.update(id, Car.builder().brand(request.brand()).color(request.color()).model(request.model()).build());
    }

    @Override
    public void deleteById(Long id) {
        carListRepository.deleteById(id);
    }

}
