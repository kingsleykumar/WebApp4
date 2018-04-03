<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Kingsley Kumar
  Date: 27/12/2017
  Time: 21:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="type" type="java.lang.String"--%>
<footer class="footer">

    <%--<div class="container" style="text-align: center; ">--%>
    <%--<span class="text-muted" ><small>© 2017 SpendBook.net</small></span>--%>
    <%--</div>--%>

    <div class="container-fluid">
        <div class="row center">
            <p class="col-xs-7 text-right" id="footer-text"><a href="/main/landing">© 2018 SpendBook.net</a> </p>

            <c:choose>
                <c:when test="${empty type}">
                    <p class="col-xs-5 text-right" id="footer-text"><a href="/contactus">Contact Us</a> </p>
                </c:when>
                <c:otherwise>
                    <p class="col-xs-5 text-right" id="footer-text"><a href="/contactus/landing">Contact Us</a> </p>
                </c:otherwise>
            </c:choose>


        <%--<div class="col-xs-3 text-right">--%>
            <%--<a href="index.html"></a>--%>
            <%--</div>--%>
        </div>
        <%--<div class="row right">--%>
            <%--<p class="col-xs-6 text-center" id="footer-text">Contact Us</p>--%>
            <%--&lt;%&ndash;<div class="col-xs-3 text-right">&ndash;%&gt;--%>
            <%--&lt;%&ndash;<a href="index.html"></a>&ndash;%&gt;--%>
            <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
        <%--</div>--%>
    </div>
</footer>
