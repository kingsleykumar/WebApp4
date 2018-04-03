<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Kingsley Kumar
  Date: 19/11/2016
  Time: 01:13
  To change this template use File | Settings | File Templates.
--%>
<%--@elvariable id="message" type="java.lang.String"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Message</title>
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

    <style>
        #message {
            color: #575757;
            font-weight: bold;
        }

        #link {
            color: #125e9c;
            font-weight: bold;
        }

        /*a, a:hover {*/
        /*color: #31b0d5;*/
        /*font-weight: bold;*/
        /*}*/

    </style>
</head>
<body>

<jsp:include page="header.jsp"/>
<c:choose>
    <c:when test="${not empty username}">
        <c:choose>
            <c:when test="${empty type}">

                <jsp:include page="navbar.jsp"/>

            </c:when>
            <c:otherwise>
                <jsp:include page="navbar-landing-logged-in.jsp"/>
            </c:otherwise>
        </c:choose>

    </c:when>
    <c:otherwise>
        <jsp:include page="navbar-not-logged-in.jsp"/>
    </c:otherwise>
</c:choose>

<div class="wrapper">
    <c:if test="${not empty username && empty type}">
        <jsp:include page="sidebar.jsp"/>
    </c:if>

    <c:choose>
    <c:when test="${not empty username && empty type}">
    <div id="content" style="padding-top: 90px">
        </c:when>
        <c:otherwise>
        <div id="content-homepage" style="padding-top: 90px">
            </c:otherwise>
            </c:choose>

            <div class="container-fluid">

                <c:choose>
                <c:when test="${not empty username && empty type}">
                <div class="row col-sm-12 col-md-10 col-lg-8" style="padding-left: 0px">
                    </c:when>
                    <c:otherwise>
                    <div class="row col-sm-12 col-md-10 col-md-offset-1 col-lg-8 col-lg-offset-2"
                         style="padding-left: 0px">
                        </c:otherwise>
                        </c:choose>
                        <p id="message" style="text-align: center"> ${message} </p>
                    </div>
                </div>
            </div>
        </div>

        <jsp:include page="footer-element.jsp"/>

</body>
</html>
