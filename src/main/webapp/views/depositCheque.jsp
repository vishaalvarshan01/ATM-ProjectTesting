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
                    <h1 class="mt-2">Deposit Cheque</h1>

                    <div class="row">
                        <div class="col-md-4 mt-4">
                            <form action="#" method="post">
                            <c:if test="${showAlert}">
                                <div class="mx-5 mt-2">
                                    <div class="alert alert-warning" role="alert">
                                        <div>${message}</div>
                                    </div>
                                </div>
                            </c:if>


                                <div>
                                    <label>Cheque Number</label><br>
                                    <input class="w-100 form-control" type="text" name="chequeNo" minlength="3" max="16" required>
                                </div>
                                <div>
                                    <label>Assign Date</label><br>
                                    <input class="w-100 form-control" type="date" name="assignDate" required>
                                </div>
                                <div>
                                    <label>Expire Date</label><br>
                                    <input class="w-100 form-control" type="date" name="expireDate" required>
                                </div>
                                <div>
                                    <label>Amount</label><br>
                                    <input class="w-100 form-control" type="number" min="1" name="amount" required>
                                </div>



                                <button class="mt-4 btn btn-primary">Fast Withdraw Cash</button>
                            </form>
                        </div>
                    </div>
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