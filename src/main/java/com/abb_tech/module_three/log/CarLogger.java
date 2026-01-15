package com.abb_tech.module_three.log;

import com.abb_tech.module_three.enums.CarAction;
import com.abb_tech.module_three.model.Car;

public interface CarLogger {
    void log(CarAction action, Car car);
}
