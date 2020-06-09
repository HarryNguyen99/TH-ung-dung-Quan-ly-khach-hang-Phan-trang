package service.impl;

import model.Customer;
import model.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import repository.CustomerRepository;
import service.CustomerService;
@Transactional
public class CustomerRepositoryImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Iterable<Customer> findAllByProvince(Province province) {
        return customerRepository.findAllByProvince(province);
    }

    @Override
    public Page<Customer> findAllByFirstNameContaining(String firstname, Pageable pageable) {
        return customerRepository.findAllByFirstNameContaining(firstname, pageable);
    }

    @Override
    public Page<Customer> findAll(Pageable pageable) throws Exception {
//        if (true) throw new Exception("a dummy exception");
        return customerRepository.findAll(pageable);
    }

    @Override
    public Customer findById(Long id) throws Exception {
        Customer target = customerRepository.findOne(id);
        if (target == null) {
            throw new Exception("customer not found!");
        }
        return target;
    }

    @Override
    public void save(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public void remove(Long id) {
        customerRepository.delete(id);
    }
}
