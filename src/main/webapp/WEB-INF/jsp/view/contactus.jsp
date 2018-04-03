<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Kingsley Kumar
  Date: 28/01/2018
  Time: 23:33
  To change this template use File | Settings | File Templates.
--%>
<%--@elvariable id="username" type="java.lang.String"--%>
<%--@elvariable id="type" type="java.lang.String"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp"/>
<c:choose>
    <c:when test="${not empty username}">
        <c:choose>
            <c:when test="${empty type}">

                <jsp:include page="navbar.jsp"/>

            </c:when>
            <c:otherwise>
                <jsp:include page="navbar-landing-logged-in.jsp"/>
            </c:otherwise>
        </c:choose>

    </c:when>
    <c:otherwise>
        <jsp:include page="navbar-not-logged-in.jsp"/>
    </c:otherwise>
</c:choose>

<div class="wrapper">

    <c:if test="${not empty username && empty type}">
        <jsp:include page="sidebar.jsp"/>
    </c:if>

    <c:choose>
    <c:when test="${not empty username && empty type}">
    <div id="content">
        </c:when>
        <c:otherwise>
        <div id="content-homepage" style="padding-top: 70px">
            </c:otherwise>
            </c:choose>

            <%--<p style="padding-top: 20px">Contact Us</p>--%>

            <%--<h3>Contact Us</h3>--%>
            <%--<br>--%>
            <%--<div id="result"></div>--%>
            <%--<div class="container-fluid">--%>
            <%--<div id="formdiv">--%>
            <form:form id="form" method="POST" name="form"
                       action="/contactus" modelAttribute="contactus" class="form-horizontal">

                <c:set var="namelimit" value="${50}"></c:set>
                <c:set var="emaillimit" value="${120}"></c:set>
                <c:set var="messagecharlimit" value="${10000}"></c:set>

                <form:input type="text" name="id" id="id" style="display: none;" path="id"/>
                <form:input type="text" name="type" id="type" style="display: none;" path="type"/>

            <div class="container-fluid">

                <c:choose>
                <c:when test="${not empty username && empty type}">
                <div class="row col-sm-12 col-md-10 col-lg-8" style="padding-left: 0px">
                    </c:when>
                    <c:otherwise>
                    <div class="row col-sm-12 col-md-10 col-md-offset-1 col-lg-8 col-lg-offset-2"
                         style="padding-left: 0px">
                        </c:otherwise>
                        </c:choose>
                        <h3>Contact Us</h3>
                    </div>

                    <c:choose>

                    <c:when test="${not empty username && empty type}">
                    <div class="row col-sm-12 col-md-10 col-lg-8" style="padding-left: 0px">
                        </c:when>
                        <c:otherwise>
                        <div class="row col-sm-12 col-md-10 col-md-offset-1 col-lg-8 col-lg-offset-2"
                             style="padding-left: 0px">
                            </c:otherwise>
                            </c:choose>

                            <small>Your comments, feedback, suggestions, feature requests are highly appreciated.
                                Please don't hesitate to contact us.
                            </small>
                        </div>

                            <%--<c:choose>--%>
                            <%--<c:when test="${not empty username && empty type}">--%>
                            <%--<div class="row col-sm-12 col-md-10 col-lg-8" style="padding-left: 0px">--%>
                            <%--</c:when>--%>
                            <%--<c:otherwise>--%>
                            <%--<div class="row col-sm-12 col-md-10 col-md-offset-1 col-lg-8 col-lg-offset-2"--%>
                            <%--style="padding-left: 0px">--%>
                            <%--</c:otherwise>--%>
                            <%--</c:choose>--%>

                            <%--<!-- the result of the form submission will be rendered inside this div -->--%>
                            <%--<div id="result">--%>
                            <%--</div>--%>

                            <%--</div>--%>

                        <c:choose>
                        <c:when test="${not empty username && empty type}">
                        <div class="row col-sm-12 col-md-10 col-lg-8" style="padding-left: 0px">
                            </c:when>
                            <c:otherwise>
                            <div class="row col-sm-12 col-md-10 col-md-offset-1 col-lg-8 col-lg-offset-2"
                                 style="padding-left: 0px">
                                </c:otherwise>
                                </c:choose>

                                <div id="error">
                                    <strong>
                                        <form:errors path="*" id="error"/>
                                    </strong>
                                </div>
                            </div>
                        </div>

                        <div id="formdiv" class="container-fluid">

                            <c:choose>
                            <c:when test="${not empty username && empty type}">
                            <div class="row col-sm-12 col-md-10 col-lg-8 well">
                                </c:when>
                                <c:otherwise>
                                <div class="row col-sm-12 col-md-10 col-md-offset-1 col-lg-8 col-lg-offset-2 well">
                                    </c:otherwise>
                                    </c:choose>

                                    <div class="form-group">
                                        <form:label class="col-sm-2 control-label" path="name">Name:</form:label>

                                        <div class="col-sm-10">
                                            <form:input type="text" name="name" id="name" path="name"
                                                        onKeyDown="limitTextField(this.form.name,${namelimit});"
                                                        onKeyUp="limitTextField(this.form.name,${namelimit});"
                                                        maxlength="${namelimit}"
                                                        class="form-control"/>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <form:label class="col-sm-2 control-label" path="email">E-mail:<span
                                            class="mandatory">*</span></form:label>

                                        <div class="col-sm-10">
                                            <form:input type="text" name="email" id="email"
                                                        onKeyDown="limitTextField(this.form.email,${emaillimit});"
                                                        onKeyUp="limitTextField(this.form.email,${emaillimit});"
                                                        maxlength="${namelimit}" class="form-control" path="email"/>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <form:label class="col-sm-2 control-label"
                                                    path="message">Message:</form:label>

                                        <div class="col-sm-10">
                                            <form:textarea rows="10" name="message" id="message" path="message"
                                                           onKeyDown="limitTextArea(this.form.message,this.form.countdown,${messagecharlimit});"
                                                           onKeyUp="limitTextArea(this.form.message,this.form.countdown,${messagecharlimit});"
                                                           class="form-control"/>
                                                <%--</form:textarea>--%>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-2 control-label"></label>
                                        <div class="col-sm-10">
                                            <input type="submit" class="btn btn-info" value="Send"></div>
                                    </div>
                                </div>

                            </div>
                            </form:form>
                            <%--</div>--%>

                        </div>


                        <!-- Footer -->
                        <!--<footer class="footer">
                        <div class="row">
                        <div class="col-lg-12">
                        <p>Copyright &copy; SpendBook 2016-2017</p>
                        </div>
                        </div>
                        </footer>-->

                    </div>

                    <script type="text/javascript">

                        //                         $(document).ready(function () {
                        //
                        //                             /* attach a submit handler to the form */
                        //                             $("#form").submit(function (event) {
                        //
                        // //                alert("jquery form submit.");
                        //
                        //                                 /* stop form from submitting normally */
                        //                                 event.preventDefault();
                        //
                        //                                 /* get the action attribute from the <form action=""> element */
                        //                                 var $form = $(this),
                        //                                     url = $form.attr('action');
                        //
                        // //                alert(url);
                        //
                        //                                 /* Send the data using post*/
                        //                                 var posting = $.post(url, {
                        //                                     name: $('#name').val(),
                        //                                     email: $('#email').val(),
                        //                                     message: $('#message').val(),
                        //                                     id: $('#id').val()
                        //                                 });
                        //
                        //                                 /* Alerts the results */
                        //                                 posting.done(function (data) {
                        //
                        //                                     console.log(data);
                        //
                        //                                     var json = JSON.parse(data);
                        //
                        //                                     var result = json.result;
                        //                                     var message = json.message;
                        //
                        //                                     console.log("result == " + result);
                        //                                     console.log("message == " + message);
                        //
                        //                                     if (result.toString() == "false") {
                        //
                        //                                         document.getElementById("result").innerHTML = "<p><strong><span class=\"mandatory\">" + message + "</span></strong></p>";
                        //
                        //                                     } else {
                        //
                        //                                         document.getElementById("result").innerHTML = "<p><strong>" + message + "</strong></p>";
                        //
                        //                                         document.getElementById("formdiv").innerHTML = "";
                        //                                     }
                        //
                        // //                    alert('success');
                        //                                 });
                        //                             });
                        //                         });

                    </script>

                    <style>

                        span.mandatory {
                            color: red;
                        }

                        span.error {
                            color: red;
                        }

                        #error {
                            color: red;
                        }

                    </style>
<jsp:include page="footer.jsp"/>