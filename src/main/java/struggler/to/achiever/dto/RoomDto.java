package struggler.to.achiever.dto;

public class RoomDto {
    Long id;
    String roomType;
    double roomPrice;
    String status;
    Long customerId;

    public RoomDto() {
    }

    public RoomDto(Long id, String roomType, double roomPrice, String status ,Long customerId) {
        this.id = id;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
        this.status = status;
        this.customerId = customerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
