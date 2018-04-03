<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%--
  Created by IntelliJ IDEA.
  User: Kingsley Kumar
  Date: 09/11/2016
  Time: 21:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <title>Sign Up</title>
    <meta name="description" content="Sign Up to create an account on Spend Book.">
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

        function validate() {
            var mail = document.getElementsByName("email").value;
            if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(mail)) {
                return (true);
            }
            document.getElementById("result").innerHTML = "<span class=\"error\">Email is not valid!</span>"
//            alert("You have entered an invalid email address!")
            return (false);
        }

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
                var posting = $.post(url, $("#form").serialize()
                //     {
                //     email: $('#email').val(),
                //     password: $('#password').val(),
                //     confirmpassword: $('#confirmpassword').val(),
                //     id: $('#id').val()
                // }
                );

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

                        document.getElementById("result").innerHTML = "<p><strong>" + message + " Please Click here to <a href=\"/login\" style=\"color: #31b0d5\">Login</a>. </strong></p>";

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

        <%--<div id="result">test</div>--%>
        <form:form id="form" method="POST" name="form"
              action="/signup"  modelAttribute="signup" class="form-horizontal">

            <c:set var="emaillimit" value="${120}"></c:set>
            <c:set var="passwordcharacterslimit" value="${30}"></c:set>

            <input type="text" name="id" id="id" style="display: none;">

            <div class="container-fluid">
                <div class="row">
                    <div class="col-sm-10 col-sm-offset-1 col-md-8  col-md-offset-2 col-lg-6 col-lg-offset-3" style="padding-left: 0px">
                        <h3>Sign Up</h3>
                    </div>
                </div>
                <div class="row">
                    <!-- the result of the form submission will be rendered inside this div -->
                    <div id="result" class="col-sm-10 col-sm-offset-1 col-md-8  col-md-offset-2 col-lg-6 col-lg-offset-3" style="padding-left: 0px">
                    </div>
                </div>
            </div>
            <div class="container-fluid" id="formdiv">
                <div class="row">

                    <div class="col-sm-10 col-sm-offset-1 col-md-8  col-md-offset-2 col-lg-6 col-lg-offset-3 well" style="padding-left: 15px">
                        <div class="form-group">
                            <form:label class="control-label col-sm-5 col-lg-4" path="email">E-mail:<span
                                    class="mandatory">*</span></form:label>
                            <div class="col-sm-7 col-lg-8">
                                <form:input type="text" name="email" size="30" id="email" class="form-control"
                                       maxlength="${emaillimit}" path="email"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <form:label class="control-label col-sm-5 col-lg-4" path="password">Password:<span
                                    class="mandatory">*</span></form:label>
                            <div class="col-sm-7 col-lg-8">
                                <form:input type="password" size="30" name="password" id="password" class="form-control"
                                       maxlength="${passwordcharacterslimit}" path="password"/>
                                <small>(Password must be at least 8 characters long)</small>
                            </div>
                        </div>

                        <div class="form-group">
                            <form:label  class="control-label col-sm-5 col-lg-4" path="confirmpassword">Confirm Password:<span
                                    class="mandatory">*</span></form:label>
                            <div class="col-sm-7 col-lg-8">
                                <form:input type="password" size="30" name="confirmpassword" id="confirmpassword"
                                       class="form-control"
                                       maxlength="${passwordcharacterslimit}" path="confirmpassword"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-5 col-lg-4 control-label"></label>
                            <div class="col-sm-7 col-lg-8">
                                <input type="submit" class="btn btn-info" value="Sign Up">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">

                    <div class="col-sm-8 col-sm-offset-2 col-md-6 col-md-offset-3" align="center">
                        <small>Already a member? <a href="<c:url value="/login" />" style="color: #31b0d5">
                            Sign In </a>
                        </small>
                    </div>
                </div>
            </div>
        </form:form>
    </div>
</div>

<jsp:include page="footer-element.jsp"/>

</body>
</html>
