package com.abb_tech.module_three.log;

import com.abb_tech.module_three.enums.CarAction;
import com.abb_tech.module_three.model.Car;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
@Slf4j
public class ProdCarLogger implements CarLogger {

    @Override
    public void log(CarAction action, Car car) {

        Long id = car.getId();

        if (id != null) {
            log.info("CAR action={}, id={}", action, id);
        } else {
            log.info("CAR action={}", action);
        }
    }

}
