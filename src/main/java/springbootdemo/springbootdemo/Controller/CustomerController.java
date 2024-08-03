package springbootdemo.springbootdemo.Controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springbootdemo.springbootdemo.customer.Customer;
import springbootdemo.springbootdemo.Service.CustomerService;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("customer")
    public String saveCustomer(@RequestBody Customer customer){
        customerService.saveCustomer(customer);
        return "Customer saved successfully!";
    }

    @PostMapping("customers")
    public String saveAllCustomers(@RequestBody List<Customer> customers){
        customerService.saveAllCustomers(customers);
        return "All Customers saved successfully!";
    }

    @GetMapping("customers")
    public List<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    @GetMapping("customer/{customerId}")
    public Optional<Customer> getCustomer(@PathVariable Long customerId){
        return customerService.getCustomer(customerId);
    }

    @PutMapping("updateCustomer/{customerId}")
    public String updateCustomer(@RequestBody Customer customer, @PathVariable Long customerId){
        if (customerService.isCustomerAvailable(customerId)){
            customerService.updateCustomer(customer, customerId);
            return "Customer with customer Id - "+customerId+" updated successfully!";
        }else
            return "Customer with customer Id - "+customerId+" NOT FOUND!";
    }

    @DeleteMapping("deleteCustomer")
    public String deleteCustomer(@RequestParam Long customerId){
        if(customerService.isCustomerAvailable(customerId)){
            customerService.deleteCustomerById(customerId);
            return "Customer with customer Id - "+customerId+" deleted successfully!";
        }else
            return "Customer with customer Id - "+customerId+" NOT FOUND!";
    }
}
