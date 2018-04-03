<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--
  Created by IntelliJ IDEA.
  User: Kingsley Kumar
  Date: 03/11/2017
  Time: 22:40
  To change this template use File | Settings | File Templates.
--%>


<!-- Sidebar Holder -->
<nav id="sidebar" style="margin-top:50px">
    <ul class="list-unstyled components">
        <%--<li>--%>
            <%--<a href="<c:url value="/category?action=add" />">Add Category</a>--%>
        <%--</li>--%>
        <li>
            <a href="<c:url value="/category/list" />">Categories</a>
        </li>
        <%--<li>--%>
            <%--<a href="<c:url value="/budget?action=add" />">Add Budget</a>--%>
        <%--</li>--%>
        <li>
            <a href="<c:url value="/budget/list" />">Budgets</a>
        </li>
        <li>
            <a href="<c:url value="/transaction/add" />">Add Transactions</a>
        </li>
        <li>
            <a href="<c:url value="/transaction/view" />">View/Edit Transactions</a>
        </li>
        <li>
            <a href="<c:url value="/summary/view" />">View Summary</a>
        </li>

    </ul>
</nav>