package struggler.to.achiever.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import struggler.to.achiever.dto.UserDetailRequestModel;
import struggler.to.achiever.dto.UserDto;
import struggler.to.achiever.exceptions.UserServiceException;
import struggler.to.achiever.response.ErrorMessages;
import struggler.to.achiever.response.OperationStatusResponse;
import struggler.to.achiever.response.UserResponse;
import struggler.to.achiever.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/userservice")
public class UserController {

    @Autowired
    UserService userService;


    @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserDto getUsers(@PathVariable String id) {
        UserDto userDto = userService.getUserByUserId(id);
        System.out.println("UserId :" + id);
        System.out.println("UserDto :" + userDto.getEmail());

        return userDto;
    }

    @GetMapping("/getAll")
    public List<UserDto> getAllUsers() {
        List<UserDto> userEntityList = userService.getAllUsers();
        return userEntityList;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserDto user) throws UserServiceException {
        // No need to catch the UserServiceException here, let the global exception handler catch it
        UserResponse createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping(path="/{id}" , consumes = {MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserResponse updateUser(@PathVariable String id,@RequestBody UserDetailRequestModel userDetailRequestModel){
        UserResponse updatedUser = userService.updateUser(id,userDetailRequestModel);
        System.out.println("update called");
        return updatedUser;
    }

    @DeleteMapping(path="/{id}")
    public OperationStatusResponse deleteUser(@PathVariable String id){
        OperationStatusResponse deleteUser = userService.deleteUser(id);
        System.out.println("delete called");
        return deleteUser;
    }
}
