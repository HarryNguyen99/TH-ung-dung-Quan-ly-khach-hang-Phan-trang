package service;

import model.Customer;
import model.Province;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import service.exception.DuplicateEmailException;

public interface CustomerService {
    Page<Customer> findAll(Pageable pageable) throws Exception;
    Page<Customer> findAllByFirstNameContaining(String firstname, Pageable pageable);



    Customer findById(Long id) throws Exception;

    Customer save(Customer customer) throws DuplicateEmailException;

    void remove(Long id);

    Iterable<Customer> findAllByProvince(Province province);
}
