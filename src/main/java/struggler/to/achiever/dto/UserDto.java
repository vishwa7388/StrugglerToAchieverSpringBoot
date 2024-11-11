package struggler.to.achiever.dto;


import java.util.List;

public class UserDto {

    private Long id;
    private String name;
    private String dob;
    private int userCount;
    private long phoneNumber;
    private List<RoomDto> roomDto;
    private int roomCount;

    public UserDto() {
    }

    public UserDto(Long id, String name, String dob, int userCount, long phoneNumber, List<RoomDto> roomEntity, int roomCount) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.userCount = userCount;
        this.phoneNumber = phoneNumber;
        this.roomDto = roomDto;
        this.roomCount = roomCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<RoomDto> getRoomDto() {
        return roomDto;
    }

    public void setRoomDto(List<RoomDto> roomDto) {
        this.roomDto = roomDto;
    }

    public int getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(int roomCount) {
        this.roomCount = roomCount;
    }
}
