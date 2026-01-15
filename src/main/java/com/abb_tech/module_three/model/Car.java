package com.abb_tech.module_three.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class Car {

    Long id;
    String brand;
    String color;
    String model;
}
