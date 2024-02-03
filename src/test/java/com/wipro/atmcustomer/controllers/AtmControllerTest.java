package com.wipro.atmcustomer.controllers;

import com.wipro.atmcustomer.entities.BankCheque;
import com.wipro.atmcustomer.entities.BankCustomer;
import com.wipro.atmcustomer.services.AtmService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(AtmController.class)
class AtmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private AtmController atmController;

    @MockBean
    private AtmService atmService;

    @Mock
    private HttpSession session;

    @Mock
    private RedirectAttributes attributes;


    @Test
    public void testHomePage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("homepage"));
    }

    @Test
    public void testRestPin_basic() throws Exception {
        MockHttpSession session = mock(MockHttpSession.class);
        when(session.getAttribute("loginStatus")).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.get("/reset_pin").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("changePin"));
    }

    @Test
    public void testDepositCheckPage() throws Exception{
        MockHttpSession session = mock(MockHttpSession.class);
        when(session.getAttribute("loginStatus")).thenReturn(true);
        RequestBuilder request = MockMvcRequestBuilders.get("/deposit_cheque");
        mockMvc.perform(MockMvcRequestBuilders.get("/deposit_cheque").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("depositCheque"));
    }

    @Test
    public void testChangePinHandler() throws Exception {
        // Create a mock HttpSession
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("loginStatus", true);

        BankCustomer bankCustomer = new BankCustomer();
        bankCustomer.setAccountNo("123");
        bankCustomer.setAtmPin("456");

        // Set the BankCustomer in the session
        session.setAttribute("customerDetails", bankCustomer);

        when(atmService.resetPin(eq(bankCustomer.getUsername()), eq(bankCustomer.getAccountNo()), eq(bankCustomer.getAtmPin())))
                .thenReturn("Pin successfully changed");

        // Act
//        RedirectView redirectView = atmController.changePinHandler(bankCustomer, attributes, session);

        // Verify interactions with the 'attributes' mock
        verify(attributes, times(1)).addFlashAttribute(eq("showAlert"), eq(true));
        verify(attributes, times(1)).addFlashAttribute(eq("message"), eq("Pin successfully changed"));

        // Verify interactions with the 'session' mock
        verify(session, times(1)).removeAttribute("customerDetails");
        verify(session, times(1)).removeAttribute("loginStatus");
    }

    @Test
    public void testDepositCheque() throws Exception{
        MockHttpSession session = mock(MockHttpSession.class);
        when(session.getAttribute("loginStatus")).thenReturn(true);
        RequestBuilder request = MockMvcRequestBuilders.get("/deposit_cheque");
        mockMvc.perform(MockMvcRequestBuilders.get("/deposit_cheque").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("depositCheque"));
    }

    @Test
    void depositChequeHandler() throws Exception {
        // Arrange
        BankCheque bankCheque = new BankCheque();
        RedirectAttributes attributes = mock(RedirectAttributes.class);
        HttpSession session = mock(HttpSession.class); // Mocking the HttpSession

        // Stubbing the necessary methods
        when(session.getAttribute("pin")).thenReturn("123"); // Now this should work

        when(atmService.createCheque(any(BankCheque.class))).thenReturn("Cheque deposited successfully");

        // Act
//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/depositCheque")
//                        .session((MockHttpSession) session)
//                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                        .param("chequeNumber", "123")
//                        .param("amount", "1000.00"))
//                .andReturn();

        // Assert
        // Verify that securityGuard is called
        AtmController mockController = mock(AtmController.class);
//        verify(mockController).securityGuard(session);

        // Verify that necessary attributes are set in RedirectAttributes
//        verify(attributes).addFlashAttribute("showAlert", true);
//        verify(attributes).addFlashAttribute("message", "Cheque deposited successfully");

        // Verify that atmService.createCheque is called with the correct argument
//        verify(atmService).createCheque(any(BankCheque.class));

        // Verify the response status and view name
//        mockMvc.perform(MockMvcRequestBuilders.get(result.getResponse().getRedirectedUrl()))
//                .andExpect(status().isOk())
//                .andExpect(view().name("depositCheque"));
    }

    @Test
    void testFastWithdraw() throws Exception {

        MockHttpSession session = mock(MockHttpSession.class);
        when(session.getAttribute("loginStatus")).thenReturn(true);
        RequestBuilder request = MockMvcRequestBuilders.get("/fast_withdraw");
        mockMvc.perform(MockMvcRequestBuilders.get("/deposit_cheque").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("fastWithdraw"));
    }

}