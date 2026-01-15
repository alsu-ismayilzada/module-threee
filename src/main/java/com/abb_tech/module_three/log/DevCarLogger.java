package com.abb_tech.module_three.log;

import com.abb_tech.module_three.enums.CarAction;
import com.abb_tech.module_three.model.Car;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
@Slf4j
public class DevCarLogger implements CarLogger {

    @Override
    public void log(CarAction action, Car car) {
        log.debug(
                "CAR action={}, data={}", action, car
        );
    }
}

