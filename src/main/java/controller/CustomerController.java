package controller;

import model.Customer;
import model.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import service.CustomerService;
import service.ProvinceService;
import service.exception.DuplicateEmailException;

import java.util.Optional;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProvinceService provinceService;

    @ModelAttribute("provinces")
    public Iterable<Province> provinces(){
        return provinceService.findAll();
    }

    @GetMapping("/customers")
    public ModelAndView listCustomers(@RequestParam("s") Optional<String> s, Pageable pageable) throws Exception {
        Page<Customer> customers;
        if(s.isPresent()){
            customers = customerService.findAllByFirstNameContaining(s.get(), pageable);
        } else {
            customers = customerService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("/customers/list");
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }

//    @GetMapping("/customers")
//    public ModelAndView listCustomer(Pageable pageable){
//        Page<Customer> customers = customerService.findAll(pageable);
//        ModelAndView modelAndView = new ModelAndView("/customers/list");
//        modelAndView.addObject("customers", customers);
//        return modelAndView;
//    }

    @GetMapping("/create-customer")
    public ModelAndView showCreateForm(){
        ModelAndView modelAndView = new ModelAndView("/customers/create");
        modelAndView.addObject("customers", new Customer());
        return modelAndView;
    }

    @PostMapping("/create-customer")
    public ModelAndView saveProvince(@ModelAttribute ("customer") Customer customer) throws DuplicateEmailException {
        customerService.save(customer);
        return new ModelAndView("redirect:/customers");
    }




    @GetMapping("edit-customer/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) throws Exception {
        Customer customer = customerService.findById(id);
        if (customer != null) {
            ModelAndView modelAndView = new ModelAndView("/customers/edit");
            modelAndView.addObject("customers", customer);
            return modelAndView;
        }else {
            return new ModelAndView("/error.404");
        }
    }

    @PostMapping("/edit-customer")
    public ModelAndView updateCustomer(@ModelAttribute("customer") Customer customer) throws DuplicateEmailException {
            customerService.save(customer);
            return new ModelAndView("redirect:/customers");
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ModelAndView showInputNotAcceptable() {
        return new ModelAndView("customers/inputs-not-acceptable");
    }

    @GetMapping("/delete-customer/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id) throws Exception {
        Customer customer = customerService.findById(id);
        if(customer != null) {
            ModelAndView modelAndView = new ModelAndView("/customers/delete");
            modelAndView.addObject("customers", customer);
            return modelAndView;

        }else {
            return new ModelAndView("/error.404");
        }
    }

    @PostMapping("/delete-customer")
    public String deleteCustomer(@ModelAttribute("customer") Customer customer){
        customerService.remove(customer.getId());
        return "redirect:provinces";
    }


}
