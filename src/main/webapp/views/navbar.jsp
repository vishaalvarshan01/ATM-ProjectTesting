<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">ATM Client</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup"
                aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
                <div class="navbar-nav">
                <c:if test="${!loginStatus}">
                    <a class="nav-link active" aria-current="page" href="/">Home</a>
                    <a class="nav-link active" href="/login">Login</a>
                </c:if>
                <c:if test="${loginStatus}">
                    <a class="nav-link active" href="/mini_statement">Generate Statement</a>
                    <a class="nav-link active" href="/deposit_cheque">Deposit Cheque</a>
                    <a class="nav-link active" href="/fast_withdraw">Fast Withdraw</a>
                    <a class="nav-link active" href="/withdraw_cash">Withdraw Cash</a>
                    <a class="nav-link active" href="/reset_pin">Reset Pin</a>
                    <a class="nav-link active" href="/logout">Logout</a>
                </c:if>
                </div>
            </div>
        </div>
    </nav>

    <style>
            body {
                background-color: rgb(90, 214, 212) !important;
            }
        </style>