package design.parking.entity;

import java.util.List;

/**
 * 停车场会有很多停车位
 * 所以需要维护一个未使用的停车位的链表
 */
public class BaseParkingPlace {
    //空闲停车位队列
    List<BaseParkingSpot> parkingSpotList;

    public List<BaseParkingSpot> getParkingSpotList() {
        return parkingSpotList;
    }

    public void setParkingSpotList(List<BaseParkingSpot> parkingSpotList) {
        this.parkingSpotList = parkingSpotList;
    }
}
