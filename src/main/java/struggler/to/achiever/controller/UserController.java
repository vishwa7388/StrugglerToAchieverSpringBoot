package struggler.to.achiever.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
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
@Tag(name = "User Controller", description = "To implement spring security and manage roles and permissions this controller is designed.")
public class UserController {

    @Autowired
    UserService userService;

    @PostAuthorize("returnObject.userId == principal.userId")
    @GetMapping(path = "/user/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Get User By Id", description = "Returns User details based on user id.")
    public UserDto getUsers(@PathVariable String id) {
        UserDto userDto = userService.getUserByUserId(id);
        System.out.println("UserId :" + id);
        System.out.println("UserDto :" + userDto.getEmail());

        return userDto;
    }

    @GetMapping("/getAll")
    @Operation(summary = "Get All User", description = "Returns All User details.")
    public List<UserDto> getAllUsers() {
        List<UserDto> userEntityList = userService.getAllUsers();
        return userEntityList;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<UserDto> getUsers(@RequestParam(value = "page",defaultValue = "0") int page, @RequestParam(value = "limit",defaultValue = "25") int limit) {
        List<UserDto> userDtoList = userService.getUsers(page,limit);
        return userDtoList;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserDto user) throws UserServiceException {
        // No need to catch the UserServiceException here, let the global exception handler catch it
        UserResponse createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping(path="/user/{id}" , consumes = {MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserResponse updateUser(@PathVariable String id,@RequestBody UserDetailRequestModel userDetailRequestModel){
        UserResponse updatedUser = userService.updateUser(id,userDetailRequestModel);
        System.out.println("update called");
        return updatedUser;
    }

   @PreAuthorize("hasAuthority('DELETE_AUTHORITY') or #id == principal.userId")
   //@PreAuthorize("hasAuthority('DELETE_AUTHORITY')")
   @DeleteMapping(path="/user/{id}")
    public OperationStatusResponse deleteUser(@PathVariable String id){
        OperationStatusResponse deleteUser = userService.deleteUser(id);
        System.out.println("delete called");
        return deleteUser;
    }
}
