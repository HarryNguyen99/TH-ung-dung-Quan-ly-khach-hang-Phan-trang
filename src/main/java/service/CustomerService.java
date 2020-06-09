package service;

import model.Customer;
import model.Province;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {
    Page<Customer> findAll(Pageable pageable) throws Exception;
    Page<Customer> findAllByFirstNameContaining(String firstname, Pageable pageable);

    Customer findById(Long id) throws Exception;

    void save(Customer customer);

    void remove(Long id);

    Iterable<Customer> findAllByProvince(Province province);
}
