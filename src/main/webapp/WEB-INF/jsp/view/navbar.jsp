<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: Kingsley Kumar
  Date: 03/11/2017
  Time: 22:30
  To change this template use File | Settings | File Templates.
--%>
<nav class="navbar navbar-fixed-top">
    <div class="container-fluid">

        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">

            <button type="button" id="sidebarCollapse" class="btn btn-default pull-left navbar-btn">
            <%--<i class="glyphicon glyphicon-align-justify"></i>--%>
                <span class="sr-only">Toggle navigation</span>
                <span class="nav-icon-bar" style="margin-top: 4px"></span>
                <span class="nav-icon-bar"></span>
                <span class="nav-icon-bar"></span>
            </button>

            <%--<button type="button" id="sidebarCollapse" class="btn btn-default pull-left navbar-btn">--%>
                <%--<span class="sr-only">Toggle navigation</span>--%>
                <%--<span class="nav-icon-bar"></span>--%>
                <%--<span class="nav-icon-bar"></span>--%>
                <%--<span class="nav-icon-bar"></span>--%>
            <%--</button>--%>

            <a class="navbar-brand" href="<c:url value="/main/landing" />">Spend Book</a>

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

                <li class="dropdown">

                    <button class="btn btn-info dropdown-toggle" type="button" data-toggle="dropdown"><span
                            class="glyphicon glyphicon-user"></span> ${username}&nbsp<span
                            class="caret"></span></button>
                    <ul class="dropdown-menu">
                        <li><a href="<c:url value="/preferences" />"><span
                                class="glyphicon glyphicon-cog"></span>
                            <span class="item-text"> Preferences</span>
                        </a></li>
                        <li><a href="<c:url value="/changepassword" />"><span
                                class="glyphicon glyphicon-lock"></span> <span
                                class="item-text"> Change Password</span>
                        </a></li>
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