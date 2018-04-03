<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--

  Created by IntelliJ IDEA.
  User: Kingsley Kumar
  Date: 20/11/2016
  Time: 00:49
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Change Your Password</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="icon" href="/images/logo.png" type="image/png">
    <link rel="shortcut icon" href="/images/logo.png" type="image/png">

    <!-- Bootstrap CSS CDN -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <!-- Our Custom CSS -->
    <link rel="stylesheet" type="text/css" href="/css/main.css"/>
    <!-- jQuery CDN -->
    <script src="https://code.jquery.com/jquery-1.12.0.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>

    <!-- Bootstrap Js CDN -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <script type="text/javascript">

        $(document).ready(function () {

            /* attach a submit handler to the form */
            $("#form").submit(function (event) {

//                alert("jquery form submit.");

                /* stop form from submitting normally */
                event.preventDefault();

                /* get the action attribute from the <form action=""> element */
                var $form = $(this),
                    url = $form.attr('action');

//                alert(url);

                /* Send the data using post*/
                var posting = $.post(url, {
//                    email: $('#email').val(),
                    password: $('#password').val(),
                    confirmpassword: $('#confirmpassword').val()
                });

                /* Alerts the results */
                posting.done(function (data) {

                    console.log(data);

                    var json = JSON.parse(data);

                    var result = json.result;
                    var message = json.message;

                    console.log("result == " + result);
                    console.log("message == " + message);

                    if (result.toString() == "false") {

                        document.getElementById("result").innerHTML = "<p><strong><span class=\"mandatory\">" + message + "</span></strong></p>";

                    } else {

                        document.getElementById("result").innerHTML = "<p><strong>" + message + " Please Click here to <a href=\"/login-simple.jsp\" style=\"color: #31b0d5\">Login</a>. </strong></p>";

                        document.getElementById("formdiv").innerHTML = "";
                    }

//                    alert('success');
                });
            });
        });

    </script>

    <style>

        span.mandatory {
            color: red;
        }

        span.error {
            color: red;
        }

    </style>
</head>
<body>

<jsp:include page="navbar-not-logged-in.jsp"/>

<div class="wrapper">
    <div id="content-homepage">
        <%--<h3>Enter Password</h3>--%>
        <!-- the result of the form submission will be rendered inside this div -->

        <div class="row">
            <label class="control-label col-sm-4"></label>
            <div class="col-sm-4">
                <div id="result"></div>
            </div>
        </div>

        <div id="formdiv">

            <div class="container-fluid">

                <form id="form" method="POST" name="form"
                      action="<c:url value="/resetpassword" />" class="form-horizontal">

                    <c:set var="passwordcharacterslimit" value="${30}"></c:set>

                    <div class="form-group">
                        <label class="control-label col-sm-4"></label>
                        <div class="col-sm-4">
                            <h3>Change Your Password</h3>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-sm-4">New Password:<span class="mandatory">*</span></label>
                        <div class="col-sm-4">
                            <input type="password" name="password" id="password" maxlength="${passwordcharacterslimit}"
                                   class="form-control">
                            <small>(Password must be at least 8 characters long)</small>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-sm-4">Confirm New Password:<span
                                class="mandatory">*</span></label>
                        <div class="col-sm-4">
                            <input type="password" name="confirmpassword" id="confirmpassword"
                                   maxlength="${passwordcharacterslimit}" class="form-control">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-4 control-label"></label>
                        <div class="col-sm-4">
                            <input type="submit" class="btn btn-info" value="Save">
                        </div>
                    </div>

                    <%--<input type="submit" value="Submit">--%>
                </form>
            </div>

        </div>
    </div>

</div>

<jsp:include page="footer-element.jsp"/>

</body>
</html>