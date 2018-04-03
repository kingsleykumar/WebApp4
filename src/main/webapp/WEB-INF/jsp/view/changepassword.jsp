<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Kingsley Kumar
  Date: 20/11/2016
  Time: 23:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="title" type="java.util.String"--%>
<jsp:include page="header.jsp"/>
<jsp:include page="navbar.jsp"/>

<div class="wrapper">
    <jsp:include page="sidebar.jsp"/>

    <div id="content">
        <h3>Change Password</h3>
        <%--<br>--%>
        <!-- the result of the form submission will be rendered inside this div -->
        <div id="result"></div>
        <div id="formdiv">

            <form id="form" method="POST" name="form"
                  action="<c:url value="/changepassword" />" class="form-horizontal">

                <c:set var="passwordcharacterslimit" value="${30}"></c:set>

                <div class="container-fluid">

                    <div class="form-group">
                        <label class="col-sm-3 control-label ">Current Password:<span class="mandatory">*</span></label>
                        <div class="col-sm-6 col-md-6 col-lg-4">
                            <input type="password" name="currentpassword" id="currentpassword" class="form-control"
                                   maxlength="${passwordcharacterslimit}"
                                   class="form-control">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label ">New Password:<span class="mandatory">*</span></label>
                        <div class="col-sm-6 col-md-6 col-lg-4">
                            <input type="password" name="password" id="password" maxlength="${passwordcharacterslimit}"
                                   class="form-control" class="form-control">
                            <small>(Password must be at least 8 characters long)</small>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label ">Confirm New Password:<span
                                class="mandatory">*</span></label>
                        <div class="col-sm-6 col-md-6 col-lg-4">
                            <input type="password" name="confirmpassword" id="confirmpassword"
                                   maxlength="${passwordcharacterslimit}" class="form-control" class="form-control">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label"></label>
                        <div class="col-sm-6 col-md-6 col-lg-4">
                            <input type="submit" class="btn btn-info" value="Save">
                        </div>
                    </div>
                </div>
            </form>
        </div>

        <%--<div >--%>
        <div class="form-horizontal" id="closeaccountdiv" style="padding-top: 50px;">
            <div class="container-fluid">

                <div class="form-group">
                    <%--<div class="col-sm-3" style="text-align: right;">--%>
                    <label class="col-sm-3 control-label">Do you want to leave?</label>

                    <div class="col-sm-6 col-md-6 col-lg-4">

                        <a class="btn btn-danger" role="button" id="closeaccount"
                           href="<c:url value="/closeaccount"></c:url>">Close My Account</a>

                        <%--<input type="submit" class="btn btn-danger" value="Delete" onclick="closeMyAccount()">--%>
                    </div>
                </div>

            </div>

        </div>
        <%--</div>--%>

    </div>
</div>
</div>

<style>
    .col-sm-3.control-label {

        width: 220px;
    }
</style>

<script type="text/javascript">

    $(document).on("click", "#closeaccount", function (e) {
        var link = $(this).attr("href"); // "get" the intended link in a var
        e.preventDefault();

        var dialog =  bootbox.confirm({
            message: "Your account and data will be deleted permanently. Are you sure you want to close your account?",
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

    //
    // function closeMyAccount() {
    //
    //     bootbox.confirm("Your account and data will be deleted permanently. Are you sure you close your account?", function (result) {
    //         if (result) {
    //
    //             console.log("result = " + result);
    //             // document.location.href = link;  // if result, "set" the document location
    //         }
    //     });
    // }

    $(document).ready(function () {

        /* attach a submit handler to the form */
        $("#form").submit(function (event) {

//                alert("jquery form submit.");

            /* stop form from submitting normally */
            event.preventDefault();

            /* get the action attribute from the <form action=""> element */
            var $form = $(this),
                url = $form.attr('action');

//                alert(url);

            /* Send the data using post*/
            var posting = $.post(url, {
                currentpassword: $('#currentpassword').val(),
                password: $('#password').val(),
                confirmpassword: $('#confirmpassword').val()
            });

            /* Alerts the results */
            posting.done(function (data) {

                console.log(data);

                var json = JSON.parse(data);

                var result = json.result;
                var message = json.message;

                console.log("result == " + result);
                console.log("message == " + message);

                if (result.toString() == "false") {

                    document.getElementById("result").innerHTML = "<p><strong><span class=\"mandatory\">" + message + "</span></strong></p>";

                } else {

                    document.getElementById("result").innerHTML = "<p><strong>" + message + "</strong></p>";

                    document.getElementById("formdiv").innerHTML = "";

                    document.getElementById("closeaccountdiv").innerHTML = "";
                }

//                    alert('success');
            });
        });
    });

</script>

<jsp:include page="footer.jsp"/>