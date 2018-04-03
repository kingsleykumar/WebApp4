<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Kingsley Kumar
  Date: 18/10/2016
  Time: 22:57
  To change this template use File | Settings | File Templates.
--%>

<%--@elvariable id="result" type="java.lang.String"--%>
<%--@elvariable id="username" type="java.lang.String"--%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sign In</title>
    <meta charset="utf-8">
    <meta name="description" content="Sign In to Spend Book.">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta http-equiv="Cache-Control" content="no-store,no-cache,must-revalidate">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">

    <link rel="icon" href="/images/logo.png" type="image/png">
    <link rel="shortcut icon" href="/images/logo.png" type="image/png">

    <!-- Bootstrap CSS CDN -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <!-- Our Custom CSS -->
    <link rel="stylesheet" type="text/css" href="css/main.css"/>

    <!-- jQuery CDN -->
    <script src="https://code.jquery.com/jquery-1.12.0.min.js"></script>

    <!-- Bootstrap Js CDN -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <script type="text/javascript">

        setTimeout(function () {
            location.reload();
        }, 1080000);

    </script>

    <style>
        span.error {
            color: red;
        }
    </style>
</head>
<body>

<nav class="navbar navbar-fixed-top">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <a class="navbar-brand" href="<c:url value="/main" />">Spend Book</a>

            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>

        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">

                <a href="<c:url value="/signup" />" class="btn btn-info navbar-btn" role="button">
                    <i class="glyphicon glyphicon-user"></i> Sign Up
                </a>

                <a href="<c:url value="/login.jsp" />" class="btn btn-info navbar-btn" role="button">
                    <i class="glyphicon glyphicon-log-in"></i>&nbsp&nbsp&nbspSign In
                </a>
            </ul>
        </div>

    </div>
</nav>

<div class="wrapper">

    <div id="content-homepage">

        <c:choose>
            <c:when test="${not empty pageContext.request.userPrincipal}">

                <%--<c:set var="username" value="${pageContext.request.userPrincipal.name}" scope="session"/>--%>
                <%--<c:out value="${sessionScope.username}" />--%>

                <c:redirect url="/main"/>

                <%--User <c:out value="${pageContext.request.userPrincipal.name}"/>--%>
            </c:when>
            <c:otherwise>

                <form name='f' action="${contextPath}/login" method='POST' class="form-horizontal">
                        <%--<form action="j_security_check" method="POST" class="form-horizontal">--%>
                    <div class="container-fluid">

                        <div class="row">
                            <div class="col-sm-8 col-sm-offset-2 col-md-6 col-md-offset-3 col-lg-4 col-lg-offset-4"
                                 style="padding-left: 0px">
                                <h3>Sign In</h3>
                            </div>
                        </div>

                            <%--<c:if test="${empty errorMsg}">--%>
                        <div class="row">
                            <div class="col-sm-8 col-sm-offset-2 col-md-6 col-md-offset-3 col-lg-4 col-lg-offset-4"
                                 style="padding-left: 0px">
                                <p><strong><span class="error"> ${errorMsg} </span></strong></p>
                            </div>
                        </div>
                            <%--</c:if>--%>
                        <div class="row">
                            <div class="col-sm-8 col-sm-offset-2 col-md-6 col-md-offset-3 col-lg-4 col-lg-offset-4 container-fluid well">

                                <div class="form-group">
                                    <div class="col-sm-12">
                                        <input type="text" name="username" maxlength="120" class="form-control"
                                               placeholder="Email address" required autofocus>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-sm-12">
                                        <input type="password" maxlength="30" name="password" class="form-control"
                                               placeholder="Password" required>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-xs-2">
                                        <input type="submit" class="btn btn-info" value="Sign In">
                                    </div>
                                        <%--<div class="col-sm-5">--%>
                                        <%--</div>--%>
                                    <div class="col-xs-10" style="margin-top:10px; text-align: right; ">
                                        <small><a href="<c:url value="/resetemail" />" style="color: #30a5d5">Forgot
                                            your password?</a></small>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-sm-8 col-sm-offset-2 col-md-6 col-md-offset-3 col-lg-4 col-lg-offset-4">
                                <div align="center">
                                    <div class="col-sm-12">
                                        <small><a href="<c:url value="/signup" />">Not a Member Yet?</a></small>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                </form>


            </c:otherwise>
        </c:choose>
    </div>
</div>

<%@ include file="view/footer-element.jsp" %>

</body>
</html>
