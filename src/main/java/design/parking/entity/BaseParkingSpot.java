package design.parking.entity;

//停车位
public class BaseParkingSpot {
    int parkingSpotNum;
    boolean inUsing;
    int carNum;

    public int getCarNum() {
        return carNum;
    }

    public void setCarNum(int carNum) {
        this.carNum = carNum;
    }

    public int getParkingSpotNum() {
        return parkingSpotNum;
    }

    public void setParkingSpotNum(int parkingSpotNum) {
        this.parkingSpotNum = parkingSpotNum;
    }

    public boolean isInUsing() {
        return inUsing;
    }

    public void setInUsing(boolean inUsing) {
        this.inUsing = inUsing;
    }
}
