package struggler.to.achiever.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import struggler.to.achiever.dto.CustomerDto;
import struggler.to.achiever.service.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/api/customerservice/")
public class CustomerServiceController {

    @Autowired
    CustomerService userService;

    @GetMapping("getAllCustomers")
    List<CustomerDto> getAllCustomer(){
        List<CustomerDto> userEntityList= userService.getAllUsers();
        return userEntityList;
    }

   @PostMapping("create/customer")
    public String createCustomer(@RequestBody CustomerDto userDto){
         userService.createUser(userDto);
         return "Successfully Created";
    }
}
