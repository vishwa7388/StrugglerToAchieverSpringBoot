package struggler.to.achiever.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import struggler.to.achiever.dto.UserDto;
import struggler.to.achiever.exceptions.UserServiceException;
import struggler.to.achiever.model.UserEntity;
import struggler.to.achiever.repository.UserRepository;
import struggler.to.achiever.response.ErrorMessages;
import struggler.to.achiever.response.UserResponse;
import struggler.to.achiever.util.Utils;

import java.util.ArrayList;
import java.util.List;

@Service(value = "userService")
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDto getUser(String email) {
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        UserDto returnValue = new UserDto();
        returnValue.setUserId(user.getUserId());
        BeanUtils.copyProperties(user, returnValue);
        return returnValue;
    }

    @Transactional
    public List<UserDto> getAllUsers() {
        List<UserEntity> userEntityList = userRepository.findAll();

        List<UserDto> userLoginDtos = new ArrayList<>();
        userEntityList.stream().forEach(x -> {
            UserDto userLoginDto = new UserDto();
            BeanUtils.copyProperties(x, userLoginDto);
            userLoginDtos.add(userLoginDto);
        });

        return userLoginDtos;
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
        response.setUsername(userLoginDto.getUsername());
        response.setPassword(userLoginDto.getPassword());
        userRepository.save(userLoginEntity);
        try {
            return response;
        } catch (Exception ex) {
            throw new UserServiceException("Failed to create user");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new User(username, user.getEncrypted_password(), new ArrayList<>()); // Return the User entity as UserDetails
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
