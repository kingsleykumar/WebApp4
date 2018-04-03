<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  Created by IntelliJ IDEA.
  User: Kingsley Kumar
  Date: 27/10/2016
  Time: 22:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="budgets" type="List<com.sb.commands.BudgetCommand>"--%>
<%--@elvariable id="message" type="java.lang.String"--%>
<jsp:include page="header.jsp"/>
<jsp:include page="navbar.jsp"/>

<div class="wrapper">
    <jsp:include page="sidebar.jsp"/>

    <div id="content">

        <div class="container-fluid" style="padding-left: 0px; padding-right: 0px">
            <div class="row">
                <div class="col-xs-2"><h3 style="padding-top: 10px">${title}</h3></div>
                <div class="col-xs-10 add-action">
                    <p class="pull-right">
                        <a href="<c:url value="/budget/add" />" class="btn btn-info" role="button">
                            Add Budget</a>
                    </p>
                </div>
            </div>
        </div>
        <%--<h3>${title}</h3>--%>

        <div id="result">
            <c:if test="${not empty message}">
                <p style="color: red"><strong>${message}</strong></p>
            </c:if>
        </div

        <div class="container-fluid">
            <div class="table-responsive">
                <table class="table table-hover table-bordered table-condensed">

                    <%--<col width="150">--%>
                    <%--<col width="150">--%>
                    <%--<col width="70">--%>
                    <%--<col width="70">--%>
                    <%--<col width="300">--%>
                    <%--<col width="50">--%>
                    <%--<col width="50">--%>

                    <thead>
                    <tr>
                        <th>Budge Name</th>
                        <th>Allocated Amount</th>
                        <th>From</th>
                        <th>To</th>
                        <th>Budget Per Category</th>
                        <th>Delete</th>
                        <th>Edit</th>
                    </tr>
                    </thead>

                    <c:choose>
                        <c:when test="${fn:length(budgets) == 0}">
                            <i>No Budget exists. You can <a href="<c:url value="/budget/add" />"
                                                            style="color: #31b0d5">add</a> one.</i>
                            <br/>
                            <br/>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${budgets}" var="row">
                                <tr>
                                    <td>

                                        <a class="btn btn-success btn-xs" role="button" href="<c:url value="/summary/${row.name}/view">
                                            <%--<c:param name="action" value="budgetview" />--%>
                                            <%--<c:param name="budgetname" value="${row.name}" />--%>
                                            </c:url>">${row.name}</a>

                                            <%--<c:out value="${row.name}"/></td>--%>
                                    <td><c:out value="${row.amount}"/></td>
                                    <td><c:out value="${row.from}"/></td>
                                    <td><c:out value="${row.to}"/></td>
                                    <td>
                                        <c:forEach items="${row.categoryBudgets}" var="categorybudget">
                                            <c:out value="${categorybudget.name}"/>: <c:out
                                                value="${categorybudget.amount}"/></br>
                                        </c:forEach>
                                    </td>
                                        <%--<td><b>one</b></br><b>two</b></td>--%>
                                        <%--<td><c:out value="${row[4]}"/></td>--%>
                                    <td><a class="btn btn-danger btn-xs" role="button" id="deletebudget"
                                           href="<c:url value="/budget/${row.name}/delete">
                                            <%--<c:param name="action" value="delete" />--%>
                                            <%--<c:param name="budgetname" value="${row.name}" />--%>
                                            </c:url>">Delete</a></td>
                                        <%--onclick="return confirm_delete()">Delete</a></td>--%>
                                    <td><a class="btn btn-info btn-xs" role="button"
                                           href="<c:url value="/budget/${row.name}/edit">
                        <%--<c:param name="action" value="edit" />--%>
                        <%--<c:param name="budgetname" value="${row.name}" />--%>
                        </c:url>">Edit</a></td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </table>
            </div>
        </div>

    </div>
</div>

<script type="text/javascript">

    $(document).on("click", "#deletebudget", function (e) {
        var link = $(this).attr("href"); // "get" the intended link in a var
        e.preventDefault();

        var dialog = bootbox.confirm({
            message: "Are you sure you want to delete?",
            buttons: {
                confirm: {
                    className: 'btn-default'
                },
                cancel: {
                    className: 'btn-primary'
                }
            },
            callback: function (result) {

                if (result) {
                    document.location.href = link;  // if result, "set" the document location
                }
            }
        });
    });

    function confirm_delete() {

//        var result = bootbox.confirm("Are you sure want to delete?", function (result) {
//            return result;
//        });
//
//        return result;

        return bootbox.confirm("Are you sure want to delete?");
//        return confirm('Are you sure you want to delete?');
    }
</script>

<jsp:include page="footer.jsp"/>