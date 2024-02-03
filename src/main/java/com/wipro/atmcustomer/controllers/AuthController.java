package com.wipro.atmcustomer.controllers;

import com.wipro.atmcustomer.entities.BankCustomer;
import com.wipro.atmcustomer.services.AtmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class AuthController {
    @Autowired
    private AtmService atmService;
    @GetMapping("/login")
    public String changePin() {
        return "login";
    }
    @PostMapping("/login")
    public RedirectView login(@ModelAttribute("BankCustomer") BankCustomer bankCustomer, RedirectAttributes attributes, HttpSession session) {
        Optional<BankCustomer> result = atmService.login(bankCustomer.getUsername(), bankCustomer.getAtmPin());
        if(result.isPresent()){
            session.setAttribute("customerDetails", result.get());
            session.setAttribute("loginStatus", true);
            session.setAttribute("pin",bankCustomer.getAtmPin());
            return new RedirectView("/fast_withdraw");
        }
        attributes.addFlashAttribute("showAlert", true);
        attributes.addFlashAttribute("message", "Invalid card details");
        return new RedirectView("/login");
    }
    @GetMapping("/logout")
    public RedirectView logout(HttpSession session, Model model) {

        session.removeAttribute("customerDetails");
        session.removeAttribute("loginStatus");
        return new RedirectView("/login");
    }


}
