package struggler.to.achiever.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {
        try {
            // Create user in the service layer
            UserResponse userResponse = userService.createUser(userDto);

            return ResponseEntity.ok(userResponse);  // Respond with 200 OK if the user is created successfully
        } catch (UserServiceException ex) {
            // If validation fails or any exception occurs, it will be caught here and passed to GlobalExceptionHandler
            throw ex;
        }
    }
}
