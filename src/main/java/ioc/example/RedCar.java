package ioc.example;

import ioc.annotation.Component;

@Component
public class RedCar implements Car{
    @Override
    public void driver() {
        System.out.println("开红旗排汽车.......");
    }
}
