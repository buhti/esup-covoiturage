package org.esupportail.covoiturage.web.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.esupportail.covoiturage.domain.Customer;
import org.esupportail.covoiturage.repository.CustomerRepository;
import org.esupportail.covoiturage.web.form.CustomerForm;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CustomerController {

    @Resource
    private CustomerRepository customerRepository;

    @RequestMapping(value = "/customer/profile", method = RequestMethod.GET)
    public void profileForm(Model model, Authentication authentication) {
        Customer currentUser = customerRepository.findOneByLogin(authentication.getName());
        model.addAttribute(new CustomerForm(currentUser));
    }

    @RequestMapping(value = "/customer/profile", method = RequestMethod.POST)
    public String profile(@Valid CustomerForm form, BindingResult formBinding, Authentication authentication) {
        // Check if validation failed
        if (formBinding.hasErrors()) {
            return null;
        }

        Customer currentUser = new Customer(0, authentication.getName(), 
                form.getEmail(), form.getFirstname(), form.getLastname());

        try {
            customerRepository.updateCustomer(currentUser);
        } catch (DuplicateKeyException e) {
            formBinding.rejectValue("email", "duplicate", "duplicate");
            return null;
        }

        return "redirect:/customer/profile";
    }

}
