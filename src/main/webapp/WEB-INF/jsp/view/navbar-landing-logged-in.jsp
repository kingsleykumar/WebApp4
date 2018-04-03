<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: Kingsley Kumar
  Date: 01/02/2018
  Time: 22:33
  To change this template use File | Settings | File Templates.
--%>
<nav class="navbar navbar-fixed-top">
    <div class="container-fluid">

        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">

            <a class="navbar-brand" href="<c:url value="/main/landing" />">Spend Book</a>

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

                <li class="dropdown">
                    <button class="btn btn-info dropdown-toggle" type="button" data-toggle="dropdown"><span
                            class="glyphicon glyphicon-user"></span> ${username}&nbsp<span
                            class="caret"></span></button>

                    <ul class="dropdown-menu">

                        <li><a href="<c:url value="/main" />"><span
                                class="glyphicon glyphicon-home"></span>
                            <span class="item-text"> Account Home</span>
                        </a></li>
                        <li class="divider"></li>

                        <li><a href="<c:url value="/logout" />"><span
                                class="glyphicon glyphicon-log-out"></span>
                            <span class="item-text"> Sign Out</span>
                        </a></li>
                    </ul>
                </li>

            </ul>
        </div>
    </div>
</nav>
