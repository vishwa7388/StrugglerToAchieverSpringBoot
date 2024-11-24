package struggler.to.achiever.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import struggler.to.achiever.dto.UserDetailRequestModel;
import struggler.to.achiever.dto.UserDto;
import struggler.to.achiever.exceptions.UserServiceException;
import struggler.to.achiever.model.UserEntity;
import struggler.to.achiever.repository.UserRepository;
import struggler.to.achiever.repository.UserRepositoryForPagingAndSorting;
import struggler.to.achiever.response.ErrorMessages;
import struggler.to.achiever.response.OperationStatus;
import struggler.to.achiever.response.OperationStatusResponse;
import struggler.to.achiever.response.UserResponse;
import struggler.to.achiever.security.UserPrincipal;
import struggler.to.achiever.util.Utils;

import java.util.ArrayList;
import java.util.List;

@Service(value = "userService")
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRepositoryForPagingAndSorting userRepositoryForPagingAndSorting;

    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDto getUser(String email) {
        System.out.println("Get User Called first: " + email);
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        System.out.println("Get User Called second: " + email);

        UserDto returnValue = new UserDto();
        returnValue.setUserId(user.getUserId());
        BeanUtils.copyProperties(user, returnValue);
        System.out.println("Value Returned: " + returnValue);
        return returnValue;
    }

    @Transactional
    public List<UserDto> getAllUsers() {
        List<UserEntity> userEntityList = userRepository.findAll();

        List<UserDto> userLoginDtos = new ArrayList<>();
        if(null != userEntityList) {
            userEntityList.stream().forEach(x -> {
                UserDto userLoginDto = new UserDto();
                BeanUtils.copyProperties(x, userLoginDto);
                userLoginDtos.add(userLoginDto);
            });
        }
        return userLoginDtos;
    }

    @Transactional
    public List<UserDto> getUsers(int page,int limit) {
        List<UserDto> userDtos = new ArrayList<>();

        Pageable pageableRequest = PageRequest.of(page,limit);

        Page<UserEntity> userPage = userRepository.findAll(pageableRequest);

        List<UserEntity> userEntities = userPage.getContent();

        userEntities.stream().forEach(userEntity -> {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(userEntity,userDto);
            userDtos.add(userDto);
        });

        return userDtos;
    }

    @Transactional
    public UserResponse createUser(UserDto userLoginDto) {
        if (userLoginDto == null) {
            System.out.println("UserDto is null");
            throw new UserServiceException("UserDto cannot be null");
        }
        if (userLoginDto.getUsername() == null || userLoginDto.getUsername().isEmpty()) {
            throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }
        UserEntity userLoginEntity = new UserEntity();
        UserResponse response = new UserResponse();
        BeanUtils.copyProperties(userLoginDto, userLoginEntity);

        String publicUserId = utils.generateUserId(30);
        userLoginEntity.setUserId(publicUserId);
        userLoginEntity.setEncrypted_password(bCryptPasswordEncoder.encode(userLoginDto.getPassword()));
        userLoginEntity.setEmail_verification_status(true);
        response.setUsername(userLoginDto.getUsername());
        response.setPassword(userLoginDto.getPassword());
        userRepository.save(userLoginEntity);
        try {
            return response;
        } catch (Exception ex) {
            throw new UserServiceException("Failed to create user");
        }
    }

    @Transactional
    public UserResponse updateUser(String id , UserDetailRequestModel userDetailRequestModel) {
        if (userDetailRequestModel == null) {
            System.out.println("userDetailRequestModel is null");
            throw new UserServiceException("userDetailRequestModel cannot be null");
        }
        if (userDetailRequestModel.getId() == null || userDetailRequestModel.getId().isEmpty()) {
            throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }
        UserResponse response = new UserResponse();
        UserEntity userEntity = userRepository.findByUserId(id);

        if(null != userEntity)
        {
            userEntity.setUsername(userDetailRequestModel.getUsername());
            userEntity.setPassword(userDetailRequestModel.getPassword());
            userEntity.setEncrypted_password(bCryptPasswordEncoder.encode(userDetailRequestModel.getPassword()));
            userEntity.setEmail(userDetailRequestModel.getEmail());
        }
        response.setUsername(userEntity.getUsername());
        response.setPassword(userEntity.getPassword());
        userRepository.save(userEntity);
        try {
            return response;
        } catch (Exception ex) {
            throw new UserServiceException("Failed to update user");
        }
    }

    @Transactional
    public OperationStatusResponse deleteUser(String id) {
        UserEntity userEntity = userRepository.findByUserId(id);

        if (userEntity == null) {
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        userRepository.delete(userEntity);
        OperationStatusResponse operationStatusResponse = new OperationStatusResponse();
        operationStatusResponse.setOperationName(OperationStatus.DELETE.getStatus());
        operationStatusResponse.setOperationResult(OperationStatus.DELETED.getStatus());
        try {
            return operationStatusResponse;
        } catch (Exception ex) {
            throw new UserServiceException("Failed to delete user");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
       // return new User(username, user.getEncrypted_password(), new ArrayList<>()); // Return the User entity as UserDetails
    return new UserPrincipal(user);
    }

    public UserDto getUserByUserId(String userId) {
        UserDto returnValue = new UserDto();
        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null) {
            throw new UsernameNotFoundException(userId);

        }
        BeanUtils.copyProperties(userEntity, returnValue);
        return returnValue;
    }
}
