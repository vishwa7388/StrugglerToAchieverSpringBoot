package struggler.to.achiever.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class UserEntity {

   private  long id;
   private String name;
   private String dob;
   private int userCount;
   private long phoneNumber;
   private List<RoomEntity> roomEntity;
   private int roomCount;

   public UserEntity() {
   }

   public UserEntity(long id, String name, String dob, int userCount, long phoneNumber, List<RoomEntity> roomEntity, int roomCount) {
      this.id = id;
      this.name = name;
      this.dob = dob;
      this.userCount = userCount;
      this.phoneNumber = phoneNumber;
      this.roomEntity = roomEntity;
      this.roomCount = roomCount;
   }

   public long getId() {
      return id;
   }

   public void setId(long id) {
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

   public List<RoomEntity> getRoomEntity() {
      return roomEntity;
   }

   public void setRoomEntity(List<RoomEntity> roomEntity) {
      this.roomEntity = roomEntity;
   }

   public int getRoomCount() {
      return roomCount;
   }

   public void setRoomCount(int roomCount) {
      this.roomCount = roomCount;
   }
}
