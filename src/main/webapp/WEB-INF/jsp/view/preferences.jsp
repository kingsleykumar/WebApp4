<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Kingsley Kumar
  Date: 24/11/2016
  Time: 00:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="username" type="java.lang.String"--%>
<%--@elvariable id="supportedFormats" type="java.util.List<com.sb.utils.SelectionItem>"--%>
<%--@elvariable id="breakDownByList" type="java.util.List<com.sb.utils.SelectionItem>"--%>
<%--@elvariable id="summaryViewsList" type="java.util.List<com.sb.utils.SelectionItem>"--%>
<%--@elvariable id="chartTypeList" type="java.util.List<com.sb.utils.SelectionItem>"--%>
<%--@elvariable id="tableExpandStateList" type="java.util.List<com.sb.utils.SelectionItem>"--%>
<%--@elvariable id="pagesList" type="java.util.List<com.sb.utils.SelectionItem>"--%>
<%--@elvariable id="timeRangeList" type="java.util.List<com.sb.utils.SelectionItem>"--%>
<%--@elvariable id="selectedFormat" type="java.util.String"--%>
<%--@elvariable id="defaultBreakDownBy" type="java.util.String"--%>
<%--@elvariable id="defaultView" type="java.util.String"--%>
<%--@elvariable id="defaultChartType" type="java.util.String"--%>
<%--@elvariable id="defaultTableExpandState" type="java.util.String"--%>
<jsp:include page="header.jsp"/>
<jsp:include page="navbar.jsp"/>

<div class="wrapper">
    <jsp:include page="sidebar.jsp"/>

    <div id="content">
        <h3>Preferences</h3>
        <br>
        <div id="result"></div>
        <div id="formdiv">
            <form:form id="form" method="POST" name="form"
                  action="/preferences"  modelAttribute="preferences" class="form-horizontal">

                <div class="container-fluid">

                    <div class="form-group">
                        <form:label class="col-sm-4 control-label" path="dateformat">Date Chooser Format:</form:label>
                        <div class="col-sm-6 col-md-6 col-lg-4">
                            <form:select id="dateformat" name="dateformat" class="form-control" path="dateformat">
                                <%--<form:option value="NONE" label=""/>--%>
                                <form:options items="${supportedFormats}" itemValue="id" itemLabel="displayName"/>
                            </form:select>
                        </div>
                    </div>

                    <div class="form-group">
                        <form:label class="col-sm-4 control-label" path="defaultPageAfterLogin">Default Page After Login:</form:label>
                        <div class="col-sm-6 col-md-6 col-lg-4">
                            <form:select id="defaultPageAfterLogin" name="defaultPageAfterLogin" class="form-control" path="defaultPageAfterLogin">
                                <%--<form:option value="NONE" label=""/>--%>
                                <form:options items="${pagesList}" itemValue="id" itemLabel="displayName"/>
                            </form:select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-4 control-label text-primary">View Summary</label>
                    </div>

                    <div class="form-group">
                        <form:label class="col-sm-4 control-label" path="defaultBreakDownBy">Default Breakdown By:</form:label>
                        <div class="col-sm-6 col-md-6 col-lg-4">
                            <form:select id="defaultBreakDownBy" name="defaultBreakDownBy" class="form-control" path="defaultBreakDownBy">
                                <%--<form:option value="NONE" label=""/>--%>
                                <form:options items="${breakDownByList}" itemValue="id" itemLabel="displayName"/>
                            </form:select>
                        </div>
                    </div>


                    <div class="form-group">
                        <form:label class="col-sm-4 control-label" path="defaultView">Default View:</form:label>
                        <div class="col-sm-6 col-md-6 col-lg-4">
                            <form:select id="defaultView" name="defaultView" class="form-control" path="defaultView">
                                <form:options items="${summaryViewsList}" itemValue="id" itemLabel="displayName"/>
                            </form:select>
                        </div>
                    </div>

                    <div class="form-group">
                        <form:label class="col-sm-4 control-label" path="defaultTableExpandState">Default Table Expand State:</form:label>
                        <div class="col-sm-6 col-md-6 col-lg-4">
                            <form:select id="defaultTableExpandState" name="defaultTableExpandState" class="form-control" path="defaultTableExpandState">
                                <form:options items="${tableExpandStateList}" itemValue="id" itemLabel="displayName"/>
                            </form:select>
                        </div>
                    </div>

                    <div class="form-group">
                        <form:label class="col-sm-4 control-label" path="defaultChartType">Default Chart Type:</form:label>
                        <div class="col-sm-6 col-md-6 col-lg-4">
                            <form:select id="defaultChartType" name="defaultChartType" class="form-control" path="defaultChartType">
                                <form:options items="${chartTypeList}" itemValue="id" itemLabel="displayName"/>
                            </form:select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-4 control-label text-primary">View/Edit Transactions</label>
                    </div>

                    <div class="form-group">
                        <form:label class="col-sm-4 control-label" path="defaultTimeRangeForTxView">Default Time Range:</form:label>
                        <div class="col-sm-6 col-md-6 col-lg-4">
                            <form:select id="defaultTimeRangeForTxView" name="defaultTimeRangeForTxView" class="form-control" path="defaultTimeRangeForTxView">
                                <%--<form:option value="NONE" label=""/>--%>
                                <form:options items="${timeRangeList}" itemValue="id" itemLabel="displayName"/>
                            </form:select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-4 control-label"></label>
                        <div class="col-sm-6 col-md-6 col-lg-4">
                            <input type="submit" class="btn btn-info" value="Save"></div>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
</div>

<style>
    .col-sm-4.control-label {

        width: 230px;
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
            // var posting = $.post(url, {
            //     dateformat: $('#dateformat').val(),
            //     defaultBreakDownBy: $('#defaultBreakDownBy').val(),
            //     defaultView: $('#defaultView').val(),
            //     defaultChartType: $('#defaultChartType').val(),
            //     defaultTableExpandState: $('#defaultTableExpandState').val()
            // });

            var posting = $.post(url, $("#form").serialize());

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
                }

//                    alert('success');
            });
        });
    });

</script>

<jsp:include page="footer.jsp"/>