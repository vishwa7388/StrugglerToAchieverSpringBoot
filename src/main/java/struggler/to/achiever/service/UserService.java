package struggler.to.achiever.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
import struggler.to.achiever.dto.RoomDto;
import struggler.to.achiever.dto.UserDto;
import struggler.to.achiever.model.RoomEntity;
import struggler.to.achiever.model.UserEntity;
import struggler.to.achiever.repository.RoomRepository;
import struggler.to.achiever.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoomRepository roomRepository;

    @Transactional
    public List<UserDto> getAllUsers(){
        List<UserEntity> userEntityList = userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();

        for (UserEntity userEntity: userEntityList){
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(userEntity,userDto);
            List<RoomDto> roomDtoList = new ArrayList<>();
            for(RoomEntity roomEntity : userEntity.getRoomEntity()){
                RoomDto roomDto = new RoomDto();
                BeanUtils.copyProperties(roomEntity,roomDto);
                roomDto.setUserId(userEntity.getId());
                roomDtoList.add(roomDto);
            }
            userDto.setRoomDto(roomDtoList);
            userDtoList.add(userDto);
        }
       return userDtoList;
    }

    @Transactional
    public void createUser(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto,userEntity);
        List<RoomEntity> roomEntities = new ArrayList<>();
        for(RoomDto room : userDto.getRoomDto()){
            RoomEntity roomEntity = new RoomEntity();
            BeanUtils.copyProperties(room,roomEntity);
            roomEntity.setUserEntity(userEntity);
            roomEntities.add(roomEntity);
        }
        userEntity.setRoomEntity(roomEntities);
        userRepository.save(userEntity);

    }
}