package struggler.to.achiever.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import struggler.to.achiever.dto.RoomDto;
import struggler.to.achiever.dto.CustomerDto;
import struggler.to.achiever.model.RoomEntity;
import struggler.to.achiever.model.CustomerEntity;
import struggler.to.achiever.repository.RoomRepository;
import struggler.to.achiever.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    RoomRepository roomRepository;

    @Transactional
    public List<CustomerDto> getAllCustomers(){
        List<CustomerEntity> userEntityList = customerRepository.findAll();
        List<CustomerDto> userDtoList = new ArrayList<>();

        for (CustomerEntity userEntity: userEntityList){
            CustomerDto userDto = new CustomerDto();
            BeanUtils.copyProperties(userEntity,userDto);
            List<RoomDto> roomDtoList = new ArrayList<>();
            for(RoomEntity roomEntity : userEntity.getRoomEntity()){
                RoomDto roomDto = new RoomDto();
                BeanUtils.copyProperties(roomEntity,roomDto);
                roomDto.setCustomerId(userEntity.getId());
                roomDtoList.add(roomDto);
            }
            userDto.setRoomDto(roomDtoList);
            userDtoList.add(userDto);
        }
       return userDtoList;
    }

    @Transactional
    public void createCustomer(CustomerDto userDto) {
        CustomerEntity customerEntity = new CustomerEntity();
        BeanUtils.copyProperties(userDto,customerEntity);
        List<RoomEntity> roomEntities = new ArrayList<>();
        for(RoomDto room : userDto.getRoomDto()){
            RoomEntity roomEntity = new RoomEntity();
            BeanUtils.copyProperties(room,roomEntity);
            roomEntity.setCustomerEntity(customerEntity);
            roomEntities.add(roomEntity);
        }
        customerEntity.setRoomEntity(roomEntities);
        customerRepository.save(customerEntity);

    }
}