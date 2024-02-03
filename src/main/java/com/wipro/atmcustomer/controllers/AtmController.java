package com.wipro.atmcustomer.controllers;

import com.wipro.atmcustomer.entities.MiniStatement;
import com.wipro.atmcustomer.entities.BankCheque;
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
import java.util.List;

@Controller
public class AtmController {
    @Autowired
    private AtmService atmService;

    @GetMapping({"/", ""})
    public String homepage() {
        return "homepage";
    }
    @GetMapping("/reset_pin")
    public String changePin(HttpSession session) {
        securityGuard(session);
        return "changePin";
    }
    @PostMapping("/reset_pin")
    public RedirectView changePinHandler(@ModelAttribute("BankCustomer") BankCustomer bankCustomer, RedirectAttributes attributes,HttpSession session) {
        securityGuard(session);
        String result = atmService.resetPin(getCustomer(session).getUsername(), bankCustomer.getAccountNo(), bankCustomer.getAtmPin());
        attributes.addFlashAttribute("showAlert", true);
        attributes.addFlashAttribute("message", result);
        session.removeAttribute("customerDetails");
        session.removeAttribute("loginStatus");
        return new RedirectView("/login");
    }
    @GetMapping("/deposit_cheque")
    public String depositCheque(HttpSession session) {
        securityGuard(session);
        return "depositCheque";
    }

//    check method
    @PostMapping("/deposit_cheque")
    public RedirectView depositChequeHandler(@ModelAttribute("BankCheque") BankCheque bankCheque, RedirectAttributes attributes, HttpSession session) {
        String pin = (String) session.getAttribute("pin");
        if (pin == null) {
            // Handle the case when pin is not set (you may redirect the user or handle it accordingly)
            return new RedirectView("/error");
        }

        securityGuard(session);

        bankCheque.setAccountNo(getCustomer(session).getAccountNo());
        String result = atmService.createCheque(bankCheque);
        attributes.addFlashAttribute("showAlert", true);
        attributes.addFlashAttribute("message", result);
        return new RedirectView("/deposit_cheque");
    }

    @GetMapping("/fast_withdraw")
    public String fastWithdraw(HttpSession session) {
        securityGuard(session);
        return "fastWithdraw";
    }
    @PostMapping("/fast_withdraw")
    public RedirectView fastWithdrawHandler(@ModelAttribute("BankCustomer") BankCustomer bankCustomer, RedirectAttributes attributes, HttpSession session) {
        securityGuard(session);
        String pin = (String) session.getAttribute("pin");

        String result = atmService.withdrawCashFromUser(getCustomer(session).getUsername(), pin, bankCustomer.getBalance());
        attributes.addFlashAttribute("showAlert", true);
        attributes.addFlashAttribute("message", result);
        return new RedirectView("/fast_withdraw");
    }
    @GetMapping("/withdraw_cash")
    public String withdrawCash(HttpSession session) {
        securityGuard(session);
        return "withdrawCash";
    }
    @PostMapping("/withdraw_cash")
    public RedirectView withdrawCashHandler(@ModelAttribute("BankCustomer") BankCustomer bankCustomer, RedirectAttributes attributes, HttpSession session) {
        securityGuard(session);
        String pin = (String) session.getAttribute("pin");
        String result = atmService.withdrawCashFromUser(getCustomer(session).getUsername(), pin, bankCustomer.getBalance());
        attributes.addFlashAttribute("showAlert", true);
        attributes.addFlashAttribute("message", result);
        return new RedirectView("/withdraw_cash");
    }
    @GetMapping("/mini_statement")
    public String miniStatement(HttpSession session, Model model) {
        securityGuard(session);
        BankCustomer bankCustomer = getCustomer(session);
        List<MiniStatement> result = atmService.generateStatement(bankCustomer.getUsername(), bankCustomer.getAtmPin());
        model.addAttribute("showMiniStatement", true);
        model.addAttribute("miniStatement", result);
        model.addAttribute("customer", bankCustomer);
        return "miniStatement";
    }
    public BankCustomer getCustomer(HttpSession session){
        BankCustomer customer = (BankCustomer) session.getAttribute("customerDetails");
        return customer;
    }


    public void securityGuard(HttpSession session){
        Object raw = session.getAttribute("loginStatus");
        if(!(raw != null && !raw.equals(false))) throw new RuntimeException("User not valid");
    }
}
