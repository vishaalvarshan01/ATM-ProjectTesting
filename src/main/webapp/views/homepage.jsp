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
                    <h1 class="mt-2 text-center">Homepage</h1>
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