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
        <h3>Close My Account</h3>
        <%--<br>--%>
        <!-- the result of the form submission will be rendered inside this div -->
        <div id="result"></div>
        <div id="formdiv">

            <form id="form" method="POST" name="form"
                  action="<c:url value="/closeaccount" />" class="form-horizontal">

                <c:set var="passwordcharacterslimit" value="${30}"></c:set>

                <div class="container-fluid">

                    <div class="form-group">
                        <label class="col-sm-3 control-label ">Your Password:<span class="mandatory">*</span></label>
                        <div class="col-sm-6 col-md-6 col-lg-4">
                            <input type="password" name="currentpassword" id="currentpassword" class="form-control"
                                   maxlength="${passwordcharacterslimit}"
                                   class="form-control">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label"></label>
                        <div class="col-sm-6 col-md-6 col-lg-4">
                            <input type="submit" class="btn btn-danger" value="Close My Account">
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</div>

<style>
    .col-sm-3.control-label {

        width: 220px;
    }
</style>

<script type="text/javascript">

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
                currentpassword: $('#currentpassword').val()
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


                    document.location.href = "/closeaccount?result=true";  // if result, "set" the document location
                    // document.getElementById("result").innerHTML = "<p><strong>" + message + "</strong></p>";
                    //
                    // document.getElementById("formdiv").innerHTML = "";
                }

//                    alert('success');
            });
        });
    });

</script>

<jsp:include page="footer.jsp"/>