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

    @RequestMapping(value = "/mon-compte", method = RequestMethod.GET)
    public String profileForm(Model model, Authentication authentication) {
        Customer currentUser = customerRepository.findOneByLogin(authentication.getName());
        model.addAttribute(new CustomerForm(currentUser));
        return "customer/edit-profile";
    }

    @RequestMapping(value = "/mon-compte", method = RequestMethod.POST)
    public String profile(@Valid CustomerForm form, BindingResult formBinding, Authentication authentication) {
        // Check if validation failed
        if (formBinding.hasErrors()) {
            return "customer/edit-profile";
        }

        try {
            customerRepository.updateCustomer(form.toCustomer(0, authentication.getName()));
        } catch (DuplicateKeyException e) {
            formBinding.rejectValue("email", "duplicate", "duplicate");
            return "customer/edit-profile";
        }

        return "redirect:/mon-compte";
    }

}
