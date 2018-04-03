<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Main Page</title>
</head>
<body>
<h1><><><> Welcome to Main Page 333333333 <><><></h1>

<p>
    <%--<a onclick="document.forms['logoutForm'].submit()">Logout</a>--%>
    <%--</h3></u>--%>
              <a href="<c:url value="/logout" />">Logout</a>


</p>

<%--<form id="logoutForm" method="POST" action="${contextPath}/logout">--%>
<%--</form>--%>

</body>
</html>