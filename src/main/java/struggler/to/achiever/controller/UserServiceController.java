package struggler.to.achiever.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import struggler.to.achiever.dto.UserDto;
import struggler.to.achiever.dto.UserLoginDto;
import struggler.to.achiever.model.UserLoginEntity;
import struggler.to.achiever.service.UserLoginService;
import struggler.to.achiever.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/userservice/")
public class UserServiceController {

    @Autowired
    UserService userService;

    @Autowired
    UserLoginService userLoginService;

    @GetMapping("getAllUsers")
    List<UserDto> getUserEntityList(){
        List<UserDto> userEntityList= userService.getAllUsers();
        return userEntityList;
    }

    @GetMapping("getUser")
    List<UserLoginEntity> getUserDtoList(){
        List<UserLoginEntity> userEntityList= userLoginService.getAllUsers();
        return userEntityList;
    }

    @PostMapping("create/login")
    public String createUserLoginEntity(@RequestBody UserLoginDto userLoginDto){
        userLoginService.createUserLogin(userLoginDto);
        return "Successfully Created";
    }

   @PostMapping("create/user")
    public String createUserEntity(@RequestBody UserDto userDto){
         userService.createUser(userDto);
         return "Successfully Created";
    }
}
