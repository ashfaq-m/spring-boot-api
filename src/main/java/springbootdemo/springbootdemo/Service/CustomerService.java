package springbootdemo.springbootdemo.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import springbootdemo.springbootdemo.customer.Customer;
import springbootdemo.springbootdemo.customer.CustomerRepository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public void saveCustomer(Customer customer){
        customerRepository.save(customer);
    }

    public void saveAllCustomers(List<Customer> customers){
        customerRepository.saveAll(customers);
    }

    public Optional<Customer> getCustomer(Long customerId){
        return customerRepository.findById(customerId);
    }

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    public void updateCustomer(Customer customer, Long customerId){
          Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
          if (optionalCustomer.isPresent()){
              Customer existingCustomer = optionalCustomer.get();
              existingCustomer.setAddress(customer.getAddress());
              existingCustomer.setAreacode(customer.getAreacode());
              existingCustomer.setCountry(customer.getCountry());
              existingCustomer.setCustomernumber(customer.getCustomernumber());
              existingCustomer.setFirstname(customer.getFirstname());
              existingCustomer.setLastname(customer.getLastname());
              existingCustomer.setSalary(customer.getSalary());
              customerRepository.save(existingCustomer);
          }
    }

    public void deleteCustomerById(Long customerID){
        customerRepository.deleteById(customerID);
    }

    public boolean isCustomerAvailable(Long customerId){
        return customerRepository.existsById(customerId);
    }
}
