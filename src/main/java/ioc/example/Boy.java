package ioc.example;

import ioc.annotation.Autowired;
import ioc.annotation.Component;

@Component
public class Boy {
    @Autowired
    Car car;

    public void drive() {
        car.driver();
    }
}
