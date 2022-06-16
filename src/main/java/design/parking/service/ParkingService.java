package design.parking.service;

import design.parking.entity.BaseCar;

//停车服务
public interface ParkingService {
    //停车
    int addCar(BaseCar car);

    //车辆驶出停车场
    int removeCar(BaseCar car);
}
