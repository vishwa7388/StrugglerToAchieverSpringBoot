package struggler.to.achiever.model;

public class RoomEntity {

    long id;
    String roomType;
    double roomPrice;
    String status;

    public RoomEntity() {
    }

    public RoomEntity(long id, String roomType, double roomPrice, String status) {
        this.id = id;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public double getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(double roomPrice) {
        this.roomPrice = roomPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
