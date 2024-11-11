package struggler.to.achiever.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import struggler.to.achiever.dto.UserLoginDto;
import struggler.to.achiever.model.UserLoginEntity;
import struggler.to.achiever.repository.UserLoginRepository;
import struggler.to.achiever.util.Utils;

import java.util.List;

@Service
public class UserLoginService {

    @Autowired
    UserLoginRepository userLoginRepository;

    @Autowired
    Utils utils;

    @Transactional
    public List<UserLoginEntity> getAllUsers(){
        List<UserLoginEntity> userEntityList = userLoginRepository.findAll();
        return userEntityList;
    }

    @Transactional
    public void createUserLogin(UserLoginDto userLoginDto){
        UserLoginEntity userLoginEntity = new UserLoginEntity();
        BeanUtils.copyProperties(userLoginDto,userLoginEntity);

        String publicUserId = utils.generateUserId(30);
       //rLoginEntity.set
        userLoginRepository.save(userLoginEntity);
    }
}
