
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Welcome</title>
</head>
<body>
<h1><><><> Welcome !!!!!!!!!! <><><></h1>

<c:if test="${not empty message}">
    <p>Not Empty</p>
</c:if>
<c:forEach begin="0" end="10" varStatus="loop">
    <p>${message}</p>

    <%--Index: ${loop.index}<br/>--%>
</c:forEach>

<%--<c:forEach items="${messages}" var="element">--%>

<%--<p>${element}</p>--%>
<%--</c:forEach>--%>

<p>
          <a href="<c:url value="/main" />">Main</a>

</p>

</body>
</html>