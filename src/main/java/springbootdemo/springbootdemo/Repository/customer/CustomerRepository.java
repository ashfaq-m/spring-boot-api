package springbootdemo.springbootdemo.Repository.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springbootdemo.springbootdemo.Entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
