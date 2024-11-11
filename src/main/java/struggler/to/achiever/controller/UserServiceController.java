package struggler.to.achiever.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import struggler.to.achiever.dto.UserDto;
import struggler.to.achiever.model.UserEntity;
import struggler.to.achiever.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/userservice/")
public class UserServiceController {

    @Autowired
    UserService userService;

    @GetMapping("/getAll")
    List<UserDto> getUserEntityList(){
        List<UserDto> userEntityList= userService.getAllUsers();
        return userEntityList;
    }

   @PostMapping("test")
    public String createUserEntity(@RequestBody UserDto userDto){
         userService.createUser(userDto);
         return "Successfully Created";
    }
}
