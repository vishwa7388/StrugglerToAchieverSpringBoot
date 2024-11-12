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
import struggler.to.achiever.model.UserEntity;
import struggler.to.achiever.repository.UserRepository;
import struggler.to.achiever.util.Utils;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserDto returnValue;

    public UserDto getUser(String email) {
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        UserDto returnValue = new UserDto();
        returnValue.setUserId(user.getUser_id());
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
    public void createUser(UserDto userLoginDto) {
        UserEntity userLoginEntity = new UserEntity();
        BeanUtils.copyProperties(userLoginDto, userLoginEntity);

        String publicUserId = utils.generateUserId(30);
        userLoginEntity.setUser_id(publicUserId);
        userLoginEntity.setEncrypted_password(bCryptPasswordEncoder.encode(userLoginDto.getPassword()));
        userRepository.save(userLoginEntity);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new User(username, user.getEncrypted_password(), new ArrayList<>()); // Return the User entity as UserDetails
    }
}
