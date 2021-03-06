<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Kingsley Kumar
  Date: 09/11/2016
  Time: 21:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${title}</title>
    <meta name="description" content="Reset Password">
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
                    email: $('#email').val(),
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

                        document.getElementById("result").innerHTML = "<p><strong><span class=\"error\">" + message + "</span></strong></p>";

                    } else {

                        document.getElementById("result").innerHTML = "<p><strong>" + message + "</strong></p>";

                        document.getElementById("formdiv").innerHTML = "";
                    }

//                    alert('success');
                });
            });
        });

    </script>
    <style>
        span.error {
            color: red;
        }

    </style>
</head>
<body>

<jsp:include page="navbar-not-logged-in.jsp"/>

<div class="wrapper">

    <div id="content-homepage">

        <%--<h3>Reset Password</h3>--%>
        <!-- the result of the form submission will be rendered inside this div -->
        <%--<div id="result"></div>--%>
        <div class="container-fluid" >

            <form id="form" method="POST" name="form"
                  action="<c:url value="/resetemail" />" class="form-horizontal">


                <div class="row">
                    <div class="col-sm-8 col-sm-offset-2 col-md-6 col-md-offset-3 col-lg-4 col-lg-offset-4"
                         style="padding-left: 0px">
                        <h3>Reset Password</h3>
                    </div>
                </div>

                <div class="row">
                    <div class="col-sm-8 col-sm-offset-2 col-md-6 col-md-offset-3 col-lg-4 col-lg-offset-4"
                         style="padding-left: 0px" id="result">
                    </div>
                </div>

                <div class="row" id="formdiv">

                    <div class="col-sm-8 col-sm-offset-2 col-md-6 col-md-offset-3 col-lg-4 col-lg-offset-4">
                        <%--<h3>Reset Password</h3>--%>

                        <div class="form-group">
                            <%--<div class="col-sm-12">--%>
                            <input type="text" name="email" size="30" id="email" maxlength="120"
                                   class="form-control"
                                   placeholder="Enter Email" autofocus>
                            <%--</div>--%>
                        </div>

                        <div class="form-group">
                            <input type="submit" class="btn btn-info" value="Submit">

                        </div>
                    </div>
                </div>

                <%--<div class="row">--%>

                <%--<div class="col-sm-5 container-fluid">--%>
                <%--<div class="form-group">--%>
                <%--<div class="col-sm-12">--%>
                <%--<input type="text" name="email" size="30" id="email" maxlength="120"--%>
                <%--class="form-control"--%>
                <%--placeholder="Enter Email" required autofocus>--%>
                <%--</div>--%>
                <%--</div>--%>
                <%--</div>--%>
                <%--</div>--%>

                <%--<div class="form-group">--%>
                <%--<div class="col-sm-2">--%>
                <%--<input type="submit" class="btn btn-info" value="Submit">--%>
                <%--</div>--%>
                <%--</div>--%>
            </form>
        </div>
    </div>
</div>

<jsp:include page="footer-element.jsp"/>

</body>
</html>
