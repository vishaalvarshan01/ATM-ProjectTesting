<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!doctype html>
        <html lang="en">

        <head>
            <!-- Required meta tags -->
            <meta charset="utf-8">
            <meta ̦name="viewport" content="width=device-width, initial-scale=1">

            <link
              href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.4.0/mdb.min.css"
              rel="stylesheet"
            />


            <title>ATM Client</title>
        </head>

        <body>

            <%@ include file="navbar.jsp" %>

                <div class="container">
                    <h1 class="mt-2 text-center">Generate Mini Statement</h1>



                    <c:if test="${showMiniStatement}">

                                <div class="row mt-4">
                                    <div class="col-md-12 card card-primary ">
                                       <div class="row mt-4 mb-3">
                                               <div class="col-md-6">
                                                    <h5>Name : ${customer.name}</h5>
                                               </div>
                                               <div class="col-md-6">
                                                   <h5>ATM Card : ${customer.username}</h5>
                                               </div>
                                               <div class="col-md-6">
                                                     <h5>Account Number : ${customer.accountNo}</h5>
                                               </div>
                                               <div class="col-md-6">
                                               <h5>IFSC Code : ${customer.branchIfscCode}</h5>
                                               </div>
                                               <div class="col-md-6">
                                               <h5>Balance : ${customer.balance}</h5>
                                               </div>
                                       </div>





                                    </div>
                                    <div class="col-md-12 ">
                                        <hr>
                                        <h3>Mini Statement</h3>
                                        <table class="table table-bordered">
                                            <tr>
                                                <th>Amount</th>
                                                <th>Account</th>
                                                <th>Date</th>
                                                <th>Type</th>
                                            </tr>
                                            <c:forEach items="${miniStatement}" var="statement">
                                                <tr>
                                                    <td>${statement.amount}</td>
                                                    <td>${statement.customer.accountNo}</td>
                                                    <td>${statement.date} - ${statement.time}</td>
                                                    <td>${statement.status}</td>
                                                </tr>
                                            </c:forEach>
                                        </table>

                                    </div>
                                </div>
                            </c:if>
                </div>

        <script type="text/javascript">
        function preventBack() {
        window.history.forward();
        }

        setTimeout("preventBack()", 0);

        window.onunload = function () { null };
        </script>
        </body>

        </html>