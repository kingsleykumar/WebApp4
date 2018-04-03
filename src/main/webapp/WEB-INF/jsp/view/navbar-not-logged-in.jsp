<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Kingsley Kumar
  Date: 18/12/2017
  Time: 22:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
                    <i class="glyphicon glyphicon-log-in"></i>&nbsp;&nbsp;&nbsp;Sign In
                </a>
            </ul>
        </div>

    </div>
</nav>
