package struggler.to.achiever.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import struggler.to.achiever.dto.UserDto;
import struggler.to.achiever.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/userservice/")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("getUser")
    List<UserDto> getUsers(){
        List<UserDto> userEntityList= userService.getAllUsers();
        return userEntityList;
    }

    @PostMapping("create/user")
    public String createUser(@RequestBody UserDto userDto){
        userService.createUser(userDto);
        return "Successfully Created";
    }
}
