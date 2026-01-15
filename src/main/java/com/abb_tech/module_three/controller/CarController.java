package com.abb_tech.module_three.controller;
import com.abb_tech.module_three.exception.NotFoundException;
import com.abb_tech.module_three.payload.request.CarRequest;
import com.abb_tech.module_three.payload.response.CarResponse;
import com.abb_tech.module_three.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping("/{id}")
    public CarResponse getById(@PathVariable Long id) {
        return carService.getById(id);
    }

    @GetMapping
    public List<CarResponse> getAll() {
        return carService.getAll();
    }

    @PostMapping
    public void save(@RequestBody CarRequest request) {
        carService.save(request);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody CarRequest request) {
        carService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        carService.deleteById(id);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFound(NotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

}
