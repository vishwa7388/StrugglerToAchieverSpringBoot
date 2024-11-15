package struggler.to.achiever.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import struggler.to.achiever.dto.UserDto;
import struggler.to.achiever.exceptions.UserServiceException;
import struggler.to.achiever.response.ErrorMessages;
import struggler.to.achiever.response.UserResponse;
import struggler.to.achiever.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/userservice/")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(path="/{id}",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserDto getUsers(@PathVariable String id){
        UserDto userDto= userService.getUserByUserId(id);
        System.out.println("UserId :" + id);
        System.out.println("UserDto :" + userDto.getEmail());

        return userDto;
    }


    @GetMapping("getUser")
    public List<UserDto> getUsers(){
        List<UserDto> userEntityList= userService.getAllUsers();
        return userEntityList;
    }

   /* @PostMapping(value = "create/user",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public String createUser(@RequestBody UserDto userDto) throws Exception {
        try {
            userService.createUser(userDto);
            return "Successfully Created";
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());  // Log the error
            throw e;  // Re-throw the exception to be handled globally
        }
    }*/

    @PostMapping("/user")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserDto user) {
        try {
            // Assuming the service throws a UserServiceException if something goes wrong
            UserResponse createdUser = userService.createUser(user);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (UserServiceException ex) {
            // If the UserServiceException is thrown, it will be caught here
            // This should be caught by the GlobalExceptionHandler, but you can handle here as well
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            // Catch any other exceptions and let the GlobalExceptionHandler handle it
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
