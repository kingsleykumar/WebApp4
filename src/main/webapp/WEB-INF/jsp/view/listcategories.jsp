<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  Created by IntelliJ IDEA.
  User: Kingsley Kumar
  Date: 26/08/2016
  Time: 23:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="message" type="java.lang.String"--%>
<%--@elvariable id="categories" type="List<String[]>"--%>
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
                        <a href="<c:url value="/category/add" />" class="btn btn-info" role="button">
                            Add Category</a>
                    </p>
                </div>
            </div>
        </div>

        <div id="result">
            <c:if test="${not empty message}">
                <p style="color: red"><strong>${message}</strong></p>
            </c:if>
        </div>
        <div class="table-responsive">
            <table class="table table-hover table-bordered table-condensed">

                <thead>
                <tr>
                    <th>Category</th>
                    <th>Sub-Categories</th>
                    <th>Description</th>
                    <th>Delete</th>
                    <th>Edit</th>
                </tr>
                </thead>

                <c:choose>
                    <c:when test="${fn:length(categories) == 0}">
                        <i>No Category exists. You can <a href="<c:url value="/category/add" />" style="color: #31b0d5">add</a>
                            one.</i>
                        <br/>
                        <br/>
                    </c:when>
                    <c:otherwise>
                        <c:forEach items="${categories}" var="row">
                            <tr role="row" class="even">
                                <td><c:out value="${row[0]}"/></td>
                                <td><c:out value="${row[1]}"/></td>
                                <td><c:out value="${row[2]}"/></td>

                                <td align="center"><a class="btn btn-danger btn-xs" role="button" id="deletecategory"
                                                      href="<c:url value="/category/${row[0]}/delete">
                                                    <%--<c:param name="action" value="delete" />--%>
                                                    <%--<c:param name="categoryname" value="${row[0]}" />--%>
                                                    </c:url>">Delete</a></td>
                                    <%--</c:url>" onclick="return confirm_delete()">Delete</a></td>--%>
                                    <%--<a href="<c:url value="/signup" />" class="btn btn-info navbar-btn" role="button">--%>
                                    <%--<i class="glyphicon glyphicon-user"></i> Sign Up--%>
                                    <%--</a>--%>

                                <td align="center"><a class="btn btn-info btn-xs" role="button"
                                                      href="<c:url value="/category/${row[0]}/edit">
                        <%--<c:param name="action" value="edit" />--%>
                        <%--<c:param name="categoryname" value="${row[0]}" /> --%>
                                         </c:url>">Edit</a></td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </table>
        </div>

        <%--<p><input class="btn btn-info" type="submit" value="Save"/></p>--%>
    </div>
</div>

<script type="text/javascript">

    // $('#usersTable').DataTable({
    //     "drawCallback": function( settings ) {
    //         $("#usersTable").wrap( "<div class='table-responsive'></div>" );
    //     }
    // });

    $(document).on("click", "#deletecategory", function (e) {
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
</script>


<jsp:include page="footer.jsp"/>
