<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  Created by IntelliJ IDEA.
  User: Kingsley Kumar
  Date: 29/10/2017
  Time: 20:51
  To change this template use File | Settings | File Templates.
--%>

<%--@elvariable id="result" type="java.lang.String"--%>
<%--@elvariable id="username" type="java.lang.String"--%>
<%--@elvariable id="type" type="java.lang.String"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Spend Book - Manage your expenses and Save money</title>
    <meta charset="utf-8">
    <meta name="description" content="Spend Book is an expense management solution to manage your expenses and income. You can get better clarity on your money flow and take informative decisions on your expenses and savings. You can set up budget and view your financial summary by monthly, weekly, yearly and per budget breakdown.">
    <meta name="keywords" content="spend book, spendbook, expense, expense management, money, money management, financial, personal, household, finances, spending, income, outgoings, savings, management, category, sub-category, subcategory, budget, summary, monthly, daily, weekly, annual, yearly.">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="icon" href="/images/logo.png" type="image/png">
    <link rel="shortcut icon" href="/images/logo.png" type="image/png">

    <!-- Bootstrap CSS CDN -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

    <%--<link href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">--%>

    <!-- Our Custom CSS -->
    <link rel="stylesheet" type="text/css" href="/css/main.css"/>
    <link rel="stylesheet" type="text/css" href="/css/jquery.mCustomScrollbar.css"/>

    <!-- jQuery CDN -->
    <script src="https://code.jquery.com/jquery-1.12.0.min.js"></script>
    <%--<script src="/webjars/jquery/1.12.0/jquery.min.js"></script>--%>


    <jsp:include page="timeoutchecker.jsp"/>

    <%--<script type="text/javascript">--%>
    <%--</script>--%>
</head>
<body>
<div class="overlay"></div>
<nav class="navbar navbar-fixed-top">
    <div class="container-fluid">

        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">

            <c:choose>
                <c:when test="${not empty username}">

                    <c:if test="${empty type}">
                        <button type="button" id="sidebarCollapse" class="btn btn-default pull-left navbar-btn">
                                <%--<i class="glyphicon glyphicon-align-justify"></i>--%>
                            <span class="sr-only">Toggle navigation</span>
                            <span class="nav-icon-bar" style="margin-top: 4px"></span>
                            <span class="nav-icon-bar"></span>
                            <span class="nav-icon-bar"></span>
                        </button>
                    </c:if>

                    <a class="navbar-brand" href="<c:url value="/main/landing" />">Spend Book</a>

                </c:when>
                <c:otherwise>
                    <a class="navbar-brand" href="<c:url value="/main" />">Spend Book</a>
                </c:otherwise>
            </c:choose>

            <%--<c:if test="${not empty username}">--%>
            <%--<button type="button" id="sidebarCollapse" class="btn btn-info navbar-btn">--%>
            <%--<i class="glyphicon glyphicon-align-justify"></i>--%>
            <%--</button>--%>
            <%--</c:if>--%>

            <!-- <a class="navbar-brand" href="#">Spend Book</a> -->
            <%--<a class="navbar-brand-text" href="#" style="margin-top:8px">Spend Book</a>--%>

            <!-- style="border-color:#5bc0de" -->

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

                <c:choose>
                    <c:when test="${not empty username}">


                        <li class="dropdown">
                            <button class="btn btn-info dropdown-toggle" type="button" data-toggle="dropdown"><span
                                    class="glyphicon glyphicon-user"></span> ${username}&nbsp<span
                                    class="caret"></span></button>

                            <ul class="dropdown-menu">

                                <c:choose>
                                    <c:when test="${empty type}">
                                        <li><a href="<c:url value="/preferences" />"><span
                                                class="glyphicon glyphicon-cog"></span>
                                            <span class="item-text"> Preferences</span>
                                        </a></li>
                                        <li><a href="<c:url value="/changepassword" />"><span
                                                class="glyphicon glyphicon-lock"></span> <span
                                                class="item-text"> Change Password</span>
                                        </a></li>

                                    </c:when>
                                    <c:otherwise>
                                        <li><a href="<c:url value="/main" />"><span
                                                class="glyphicon glyphicon-home"></span>
                                            <span class="item-text"> Account Home</span>
                                        </a></li>
                                        <li class="divider"></li>
                                    </c:otherwise>
                                </c:choose>

                                <li><a href="<c:url value="/logout" />"><span
                                        class="glyphicon glyphicon-log-out"></span>
                                    <span class="item-text"> Sign Out</span>
                                </a></li>
                            </ul>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <%--<li class="dropdown">--%>
                        <%--<button type="button" id="signup" class="btn btn-info navbar-btn">--%>
                        <%--<i class="glyphicon glyphicon-user"></i> Sign Up--%>
                        <%--</button>--%>

                        <a href="<c:url value="/signup" />" class="btn btn-info navbar-btn" role="button">
                            <i class="glyphicon glyphicon-user"></i> Sign Up
                        </a>
                        <%--<button type="button" id="signin" class="btn btn-info navbar-btn">--%>
                        <%--<i class="glyphicon glyphicon-log-in"></i>&nbsp&nbsp&nbspSign In--%>
                        <%--</button>--%>
                        <%--<a href="<c:url value="/login-simple.jsp" />">Sign In</a>--%>
                        <a href="<c:url value="/login.jsp" />" class="btn btn-info navbar-btn" role="button">
                            <i class="glyphicon glyphicon-log-in"></i>&nbsp&nbsp&nbspSign In
                        </a>

                        <%--</li>--%>
                    </c:otherwise>
                </c:choose>

            </ul>
        </div>
    </div>
</nav>

<div class="wrapper">

    <c:choose>
        <c:when test="${not empty username}">

            <c:choose>
                <c:when test="${empty type}">

                    <jsp:include page="sidebar.jsp"/>

                    <div id="content">
                        <p style="padding-top: 20px">Welcome to SpendBook!</p>
                    </div>

                </c:when>
                <c:otherwise>
                    <jsp:include page="landingpage.jsp"/>
                </c:otherwise>
            </c:choose>

        </c:when>
        <c:otherwise>

            <jsp:include page="landingpage.jsp"/>

            <%--<div id="content">--%>
            <%--<p style="padding-top: 20px">Welcome to SpendBook!</p>--%>
            <%--</div>--%>

        </c:otherwise>
    </c:choose>


    <!-- Footer -->
    <!--<footer class="footer">
    <div class="row">
    <div class="col-lg-12">
    <p>Copyright &copy; SpendBook 2016-2017</p>
    </div>
    </div>
    </footer>-->

</div>

<jsp:include page="footer.jsp"/>