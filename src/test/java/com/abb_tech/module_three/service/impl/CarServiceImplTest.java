package com.abb_tech.module_three.service.impl;

import com.abb_tech.module_three.exception.NotFoundException;
import com.abb_tech.module_three.model.Car;
import com.abb_tech.module_three.payload.request.CarRequest;
import com.abb_tech.module_three.payload.response.CarResponse;
import com.abb_tech.module_three.repository.CarListRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

    @Mock
    private CarListRepository carRepository;

    @InjectMocks
    private CarServiceImpl carService;

    @Test
    void findById_success() {
        when(carRepository.findById(anyLong()))
                .thenReturn(Optional.of(Car.builder().id(1L).brand("Porsche").model("911").color("green").build()));

        CarResponse actual = carService.getById(1L);
        CarResponse expected = new CarResponse(1L, "Porsche", "green", "911");

        Assertions.assertEquals(expected.brand(), actual.brand());
        Assertions.assertEquals(expected.model(), actual.model());
        Assertions.assertEquals(expected.color(), actual.color());
    }

    @Test
    void findById_fail() {
        when(carRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        NotFoundException exception = Assertions.assertThrows(
                NotFoundException.class,
                () -> carService.getById(10L)
        );

        Assertions.assertEquals("Car with id 10 not found", exception.getMessage());
    }


    @Test
    void findCarByModel_success() {
        when(carRepository.findByModel(anyString()))
                .thenReturn(Optional.of(Car.builder().id(1L).brand("Porsche").model("911").color("green").build()));

        Car actual = carService.findCarByModel("911");
        Car expected = Car.builder().id(1L).brand("Porsche").model("911").color("green").build();

        Assertions.assertEquals(expected.getBrand(), actual.getBrand());
        Assertions.assertEquals(expected.getModel(), actual.getModel());
        Assertions.assertEquals(expected.getColor(), actual.getColor());
    }
    @Test
    void findCarByModel_fail() {
        when(carRepository.findByModel(anyString()))
                .thenReturn(Optional.empty());

        ResponseStatusException exception = Assertions.assertThrows(
                ResponseStatusException.class,
                () -> carService.findCarByModel("911")
        );

        Assertions.assertEquals("Car with model 911 not found", exception.getReason());
    }


    @Test
    void findAll() {
        when(carRepository.findAll())
                .thenReturn(List.of((Car.builder().id(1L).brand("Porsche").model("911").color("green").build())));

        List<CarResponse> actual = carService.getAll();
        List<CarResponse> expected = List.of(new CarResponse(1L, "Porsche", "green", "911"));

        Assertions.assertEquals(expected.get(0).brand(), actual.get(0).brand());
        Assertions.assertEquals(expected.get(0).model(), actual.get(0).model());
        Assertions.assertEquals(expected.get(0).color(), actual.get(0).color());
    }

    @Test
    void addCar() {
//        var car = new Car(1L, "Porsche", "911", 2022, "green");
        CarRequest request = new CarRequest("Porsche",  "green", "911");
        Assertions.assertDoesNotThrow(()-> carService.save(request));
        verify(carRepository, times(1)).save(any(Car.class));

    }

    @Test
    void updateCar_success() {
        var car = Car.builder().id(1L).brand("Porsche").model("911").color("green").build();
        CarRequest request = new CarRequest("Porsche",  "green", "911");
        Assertions.assertDoesNotThrow(()-> carService.update(1L, request));

        Assertions.assertEquals(request.brand(), car.getBrand());
        Assertions.assertEquals(request.model(), car.getModel());
        Assertions.assertEquals(request.color(), car.getColor());
        verify(carRepository, times(1)).update(anyLong(),any(Car.class));
    }

    @Test
    void updateCar_fail() {
        when(carRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class,
                () -> carService.getById(10L));

        verify(carRepository,times(0)).update(anyLong(), any());
    }

    @Test
    void delete_success(){
        var car = Car.builder().id(1L).brand("Porsche").model("Porsche").color("green").build();
        when(carRepository.findById(anyLong()))
                .thenReturn(Optional.of(car));

        carService.deleteById(1L);
        Assertions.assertDoesNotThrow(()-> carRepository.findById(1L));
    }

    @Test
    void delete_fail(){
        when(carRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class,
                () -> carService.getById(10L));
        verify(carRepository,times(0)).deleteById(anyLong());
    }

}