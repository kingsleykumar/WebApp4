<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Kingsley Kumar
  Date: 24/09/2016
  Time: 23:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="dateformat" type="java.lang.string"--%>
<%--@elvariable id="breakDownByList" type="java.util.List<com.sb.utils.SelectionItem>"--%>
<%--@elvariable id="chartTypeList" type="java.util.List<com.sb.utils.SelectionItem>"--%>
<%--@elvariable id="defaultBreakDownBy" type="java.util.String"--%>
<%--@elvariable id="defaultView" type="java.util.String"--%>
<%--@elvariable id="defaultChartType" type="java.util.String"--%>
<%--@elvariable id="defaultTableExpandState" type="java.util.String"--%>
<%--@elvariable id="fromDefault" type="java.lang.String"--%>
<%--@elvariable id="toDefault" type="java.lang.String"--%>
<c:url value="/" var="root"/>
<jsp:include page="header_treetable.jsp"/>
<jsp:include page="navbar.jsp"/>

<div class="wrapper">
    <jsp:include page="sidebar.jsp"/>

    <div id="content">
        <h3>${title}</h3>
        </br>

        <div class="form-horizontal container-fluid well" style="padding-bottom: 4px; padding-top: 12px">

            <div class="form-group" style="margin-bottom: 7px">
                <label class="col-lg-1 control-label" style="width: 110px;">From Date:</label>
                <div class="col-lg-2"><input type="text" id="datepickerFrom" name="datepicker"
                                             class="form-control"/>
                </div>

                <label class="col-lg-1 control-label" style="width: 90px">To Date:</label>
                <div class="col-lg-2"><input type="text" id="datepickerTo" name="datepicker" class="form-control"/>
                </div>

                <label class="col-lg-1 control-label" style="width: 140px">Breakdown By:</label>
                <div class="col-lg-2">

                    <%--<select id="breakdownBy" name="breakdownBy" class="form-control" onchange="loadSummary()">--%>
                    <select id="breakdownBy" name="breakdownBy" class="form-control">
                        <c:forEach items="${breakDownByList}" var="element">
                            <c:choose>
                                <c:when test="${element.id == defaultBreakDownBy}">
                                    <option value="${element.id}"
                                            selected>${element.displayName}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${element.id}">${element.displayName}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>

                    <%----%>
                    <%--<select id="breakdownBy" class="form-control">--%>
                    <%--<option value="nobreakdown"></option>--%>
                    <%--<option value="monthly" selected>Monthly</option>--%>
                    <%--<option value="weekly">Weekly</option>--%>
                    <%--<option value="daily">Daily</option>--%>
                    <%--<option value="yearly">Yearly</option>--%>
                    <%--<option value="budget">Budget</option>--%>
                    <%--</select>--%>
                </div>
                <%--<label class="col-sm-1 visible-xs"></label>--%>
                <div class="col-lg-1">
                    <button type="button" class="btn btn-info" id="summary-view-button" class="form-control">View
                    </button>
                </div>
            </div>
        </div>

        <div id="summary">

            <%--<h4 style="color: #575757">Table View</h4>--%>
            <c:if test="${defaultView == 'tableView' || defaultView == 'bothViews'}">
                <div class="row container-fluid">
                    <button class="btn btn-info btn-sm" id="expandbtn"
                            onclick="$('#summarytreetable').treetable('expandAll');">
                        <span class="glyphicon glyphicon-plus"></span> Expand All
                    </button>
                    <button class="btn btn-info btn-sm" id="expandallbtn" onclick="expandOneLevel()">
                        <span class="glyphicon glyphicon-plus"></span> Expand One Level
                    </button>
                        <%--<button class="btn btn-info" id="collapsebtn"--%>
                        <%--onclick="$('#summarytreetable').treetable('collapseAll');">--%>
                        <%--<span class="glyphicon glyphicon-minus"></span> Collapse All--%>
                        <%--</button>--%>
                    </button>
                </div>
                </br>
            </c:if>

            <div class="loader" id="loader" align="center">
                <img id="loaderImg" src="/images/ajax-loader.gif"/>
            </div>

            <c:if test="${defaultView == 'tableView' || defaultView == 'bothViews'}">
                <div>
                        <%--<div class="table-responsive">--%>
                    <table class="table table-bordered table-condensed" id="summarytreetable" style="width: 100%">
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </c:if>

            <c:if test="${defaultView == 'chartView' || defaultView == 'bothViews'}">

                <div class="form-horizontal container-fluid well" style="padding-bottom: 4px; padding-top: 12px">

                    <div class="form-group" style="margin-bottom: 7px">

                        <label class="col-sm-2 control-label" style="width: 105px;text-align: left;
                        padding-right: 0px; margin-right:0px; ">Chart Type:</label>

                        <div class="col-sm-3">
                            <select id="chartTypeSelection" name="chartTypeSelection" style="margin-left: 0px;"
                                    onchange="changeChartType()"
                                    class="form-control">
                                <c:forEach items="${chartTypeList}" var="element">
                                    <c:choose>
                                        <c:when test="${element.id == defaultChartType}">
                                            <option value="${element.id}"
                                                    selected>${element.displayName}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${element.id}">${element.displayName}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>

                <%--<div class="container-fluid">--%>

                <div class="container-fluid" id="path-container"
                     style="margin-left: 2px; margin-top: 0px; padding-left: 0px; padding-right: 0px; margin-right: 0px">
                        <%--<button type="button" name="chart-root-btn" id="chart-root-btn" onclick="loadChartRoot()"--%>
                        <%--style="margin-right:0px; margin-left:0px;border: #ff6661 solid 2px">Home</button>--%>
                </div>

                <div class="container-fluid" id="chart-container"
                     style="margin-top: 0px; padding-top: 0px; margin-left: 0px; padding-left: 0px;">
                    <canvas id="chart" style="width: 100%;"></canvas>
                </div>
                <%--</div>--%>
            </c:if>

        </div>

    </div>
</div>

<script type="text/javascript">

    var bgColor = [
        'rgba(255, 99, 132, 1)',
        'rgba(54, 162, 235, 1)',
        'rgba(255, 206, 86, 1)',
        'rgba(75, 192, 192, 1)',
        'rgba(153, 102, 255, 1)',
        'rgba(255, 159, 64, 1)',
        'rgba(158,218,229,1)',
        'rgba(23,190,207,1)',
        'rgba(219,219,141,1)',
        'rgba(188,189,34,1)',
        'rgba(199,199,199,1)',
        'rgba(127,127,127,1)',
        'rgba(247,182,210,1)',
        'rgba(227,119,194,1)',
        'rgba(196,156,148,1)',
        'rgba(140,86,75,1)',
        'rgba(197,176,213,1)',
        'rgba(148,103,189,1)',
        'rgba(255,152,150,1)',
        'rgba(214,39,40,1)',
        'rgba(152,223,138,1)',
        'rgba(44,160,44,1)',
        'rgba(255,187,120,1)',
        'rgba(255,127,14,1)',
        'rgba(174,199,232,1)',
        'rgba(31,119,180,1)',
        'rgba(28,159,119,1)',
        'rgba(217,93,3,1)',
        'rgba(113,108,177,1)',
        'rgba(230,42,139,1)',
        'rgba(101,166,29,1)',
        'rgba(230,170,4,1)',
        'rgba(168,120,33,1)',
        'rgba(98,98,98,1)',
        'rgba(69,90,100,1)',
        'rgba(100,181,246,1)',
        'rgba(239,108,0,1)',
        'rgba(255,213,79,1)',
        'rgba(183,184,63,1)',
        'rgba(132,183,97,1)',
        'rgba(47,64,116,1)',
        'rgba(159,142,196,1)',
        'rgba(205,130,173,1)',
        'rgba(68,142,77,1)',
        'rgba(167,209,99,1)',
        'rgba(204,71,72,1)',
        'rgba(253,212,0,1)',
        'rgba(101,52,143,1)',
        'rgba(89,90,92,1)',
        'rgba(154,198,132,1)',
        'rgba(59,55,139,1)',
        'rgba(210,30,117,1)',
        'rgba(33,115,120,1)',
        'rgba(107,196,152,1)',
        'rgba(243,105,102,1)',
        'rgba(251,173,47,1)',
        'rgba(254,214,152,1)'
    ];

    var prevArrayLength = 0;

    var base = "${pageContext.request.contextPath}";
    var totalLevelOneNodes = 0;

    console.log("base == " + base);

    var format = "${dateformat}";
    var fd = "${fromDefault}";
    var defaultExpandState = "${defaultTableExpandState}";
    var defaultView = "${defaultView}";
    var datePickerFormat = format.replace(/yyyy/g, "yy");

    console.log("fd == " + fd);

    var currentDataList;
    var currentDDMap;
    var noOfRootElements = 0;

    $(function () {
        $("#datepickerFrom").datepicker({dateFormat: datePickerFormat});
        $("#datepickerTo").datepicker({dateFormat: datePickerFormat});
        $("#summarytreetable").treetable({expandable: true});
    });

    function clearTable() {

        // Clear the table data.
        for (var i = 0; i < prevArrayLength; i++) {
            var node = $("#summarytreetable").treetable("node", i + 1);
            if (undefined != node) {
                $("#summarytreetable").treetable("removeNode", (i + 1)); // Remove the node and it's children.
            }
        }

        var element = document.getElementById("summarytreetable");

        element.innerHTML = "";
    }

    function getFirstDayOfMonth() {

        var today = new Date();

        var firstDay = new Date(today.getFullYear(), today.getMonth(), 1);

        return getFormattedDate(firstDay, format);
    }

    function getDate(element) {
        var date;
        try {
            date = $.datepicker.parseDate(datePickerFormat, element.value);
        } catch (error) {
            date = null;
        }

        return date;
    }

    function numberWithCommas(x) {
        var parts = x.toString().split(".");
        parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        return parts.join(".");
    }

    function getUpdatedNumber(amount) {

        var amount = parseFloat(amount);

        if (amount == 0) {
            amount = "";
        } else {
            amount = amount.toFixed(2);
        }

        return numberWithCommas(amount);
    }


    function getUpdatedNumberWithoutReplacingZeroWithSpace(amount) {

        var amount = parseFloat(amount);

        amount = amount.toFixed(2);

        return numberWithCommas(amount);
    }

    function loadSummary() {


        var elementFrom = document.getElementById("datepickerFrom");
        var elementTo = document.getElementById("datepickerTo");

        var fromDate = getFormattedDate(getDate(elementFrom), format);
        var toDate = getFormattedDate(getDate(elementTo), format);

        var retrieve = "retrieve";
        var breakdownBy = document.getElementById("breakdownBy");
        var selectedBreakDown = breakdownBy.options[breakdownBy.selectedIndex].value;

        if (defaultView == 'tableView' || defaultView == 'bothViews') {
            clearTable();

            $('#expandbtn').prop('disabled', true);
            $('#expandallbtn').prop('disabled', true);
            // $('#collapsebtn').prop('disabled', true);
        }

        if (defaultView == 'chartView') {
            clearCanvas();
        }

        $('.loader').show();
        $('#button').prop('disabled', true);

//            document.getElementById('loader').innerHTML = "<img id=\"loaderImg\" src=\"images/ajax-loader.gif\" />";

        $.get("/summary/retrieve", {
            "dateFrom": fromDate,
            "dateTo": toDate,
            // "action": retrieve,
            "selectedBreakDown": selectedBreakDown
        }, function (list) {    // Execute Ajax GET request on URL of "summary" and execute the following function with Ajax response JSON...

            var array = JSON.parse(list);

            // console.log(list);

//                document.getElementById('loader').visible = false;
//                document.getElementById('loader').innerHTML = "";
            $('.loader').hide();
            $('#button').prop('disabled', false);

            var drillDownMap = {};

            if (defaultView == 'tableView' || defaultView == 'bothViews') {

                $('#expandbtn').prop('disabled', false);
                $('#expandallbtn').prop('disabled', false);
                // $('#collapsebtn').prop('disabled', false);
                // }

                console.log("prevArrayLength === " + prevArrayLength);

                // Clear the table data.
                for (var i = 0; i < prevArrayLength; i++) {
                    var node = $("#summarytreetable").treetable("node", i + 1);
                    if (undefined != node) {
                        $("#summarytreetable").treetable("removeNode", (i + 1)); // Remove the node and it's children.
                    }
                }

                var element = document.getElementById("summarytreetable");

                element.innerHTML = "";

                var headerRow = document.createElement('thead'); // create row node
                var row = document.createElement('tr'); // create row node


                if (selectedBreakDown == "budget") {
                    var header = ["Budget", "Allocated Amount", "Actual Expense", "Remaining Amount", "Expense In Percentage"];

                    for (var i = 0; i < header.length; i++) {

                        var col = document.createElement('th'); // create column node
                        if (header[i] == "Budget") {
                            col.style = "width: 48%;";
                        } else if (header[i] == "Allocated Amount") {
                            col.style = "width: 12%;";
                        } else if (header[i] == "Actual Expense") {
                            col.style = "width: 12%;";
                        } else if (header[i] == "Remaining Amount") {
                            col.style = "width: 13%;";
                        }
                        col.appendChild(document.createTextNode(header[i]));
                        row.appendChild(col);
                    }

                } else {
                    var header = ["Month", "Amount", "In Percentage"];

                    if (selectedBreakDown == "weekly") {
                        header = ["Week", "Amount", "In Percentage"];
                    } else if (selectedBreakDown == "daily") {
                        header = ["Day", "Amount", "In Percentage"];
                    } else if (selectedBreakDown == "yearly") {
                        header = ["Year", "Amount", "In Percentage"];
                    } else if (selectedBreakDown == "nobreakdown") {
                        header = ["Time Period", "Amount", "In Percentage"];
                    }

                    for (var i = 0; i < header.length; i++) {

                        var col = document.createElement('th'); // create column node
                        if (header[i] == "Month" || header[i] == "Week" || header[i] == "Day" || header[i] == "Year" || header[i] == "Time Period") {
                            col.style = "width: 60%;";
                        } else if (header[i] == "Amount") {
                            col.style = "width: 23%;";
                        }
                        col.appendChild(document.createTextNode(header[i]));
                        row.appendChild(col);
                    }
                }

                headerRow.appendChild(row);

                element.appendChild(headerRow);

                // levelOneIds = [];

                var negativeColor = "#ff6055";
                var positiveColor = "#478b3e";

                noOfRootElements = array.length;
                var len = array.length;

                prevArrayLength = len;

                totalLevelOneNodes = len;

                for (var i = 0; i < len; i++) {
                    var node = $("#summarytreetable").treetable("node", i + 1);

                    var amount = getUpdatedNumber(array[i].amount);

                    if (selectedBreakDown == "budget") {

                        $("#summarytreetable").treetable("loadBranch", node,
                            "<tr data-tt-id=" + (i + 1) + ">" +
                            "<td><b style='color: #ff4906'>" + array[i].name + "</b></td><td></td><td></td><td></td><td></td></tr>");

                    } else {

                        $("#summarytreetable").treetable("loadBranch", node,
                            "<tr data-tt-id=" + (i + 1) + ">" +
                            "<td><b style='color: #ff4906'>" + array[i].name + "</b></td><td><b>" + amount + "</b></td><td></td></tr>");
                    }


                    var ddChildrenObj = {};

                    if (array[i].children.length > 0)
                        drillDownMap[array[i].name] = ddChildrenObj;

                    for (var j = 0; j < array[i].children.length; j++) {

                        var node = $("#summarytreetable").treetable("node", i + 1);

                        var childAt = array[i].children[j];

                        var amount = getUpdatedNumber(childAt.amount);
                        var amountInPercentage = getUpdatedNumber(childAt.amountInPercentage);

                        var nodeName = "";

                        if (selectedBreakDown == "budget") {

                            var allocatedAmount = getUpdatedNumber(childAt.allocatedAmount);
                            var remainingAmount = getUpdatedNumber(childAt.remainingAmount);

                            if (childAt.remainingAmount >= 0) {

                                var td = "<td>";

                                if (remainingAmount)
                                    td = "<td style='background-color: " + positiveColor + ";'>";

                                $("#summarytreetable").treetable("loadBranch", node,
                                    "<tr data-tt-id=" + (i + 1) + "." + (j + 1) + " data-tt-parent-id=" + (i + 1) + ">" +
                                    "<td><b>" + childAt.name + "</b></td><td><b>" + allocatedAmount + "</b></td>" +
                                    "<td><b>" + amount + "</b></td>" + td + "<b style='color: #f5f5f5'>" + remainingAmount + "</b></td><td><b>" + amountInPercentage + "</b></td></tr>");
                            } else {

                                var td = "<td>";

                                if (remainingAmount)
                                    td = "<td style='background-color: " + negativeColor + ";'>";

                                $("#summarytreetable").treetable("loadBranch", node,
                                    "<tr data-tt-id=" + (i + 1) + "." + (j + 1) + " data-tt-parent-id=" + (i + 1) + ">" +
                                    "<td><b>" + childAt.name + "</b></td><td><b>" + allocatedAmount + "</b></td>" +
                                    "<td><b>" + amount + "</b></td>" + td + "<b style='color: #f5f5f5'>" + remainingAmount + "</b></td><td><b>" + amountInPercentage + "</b></td></tr>");
                            }

                            nodeName = childAt.name + " [" + allocatedAmount + "]";

                        } else {

                            $("#summarytreetable").treetable("loadBranch", node,
                                "<tr data-tt-id=" + (i + 1) + "." + (j + 1) + " data-tt-parent-id=" + (i + 1) + ">" +
                                "<td><b>" + childAt.name + "</b></td><td><b>" + amount + "</b></td><td><b>" + amountInPercentage + "</b></td></tr>");

                            nodeName = childAt.name;
                        }

                        ddChildrenObj[nodeName] = childAt.amount;

                        if (childAt.children != null && childAt.children.length > 0) {

                            var ddPathSecondLevel = array[i].name + " >> " + nodeName;

                            var ddChildrenSecondLevelObj = {};

                            drillDownMap[ddPathSecondLevel] = ddChildrenSecondLevelObj;

                            for (var k = 0; k < childAt.children.length; k++) {

                                var parentNodeNumber = (i + 1) + "." + (j + 1);

                                var newNodeNumber = (i + 1) + "." + (j + 1) + "." + (k + 1);
//
                                var parentNode = $("#summarytreetable").treetable("node", parentNodeNumber);

                                var node = $("#summarytreetable").treetable("node", parentNode);

                                var categoryChild = childAt.children[k];

                                var amount = getUpdatedNumber(categoryChild.amount);
                                var amountInPercentage = getUpdatedNumber(categoryChild.amountInPercentage);

                                var nodeName = "";

                                if (selectedBreakDown == "budget") {

                                    var allocatedAmount = getUpdatedNumber(categoryChild.allocatedAmount);
                                    var remainingAmount = getUpdatedNumber(categoryChild.remainingAmount);

                                    if (categoryChild.remainingAmount >= 0) {

                                        var td = "<td>";

                                        if (remainingAmount)
                                            td = "<td style='background-color: " + positiveColor + ";'>";


                                        $("#summarytreetable").treetable("loadBranch", parentNode,
                                            "<tr data-tt-id=" + newNodeNumber + " data-tt-parent-id=" + parentNodeNumber + ">" +
                                            "<td>" + categoryChild.name + "</td><td><b>" + allocatedAmount + "</b></td><td><b>" + amount + "</b></td>" +
                                            td + "<b style='color: #f5f5f5'>" + remainingAmount + "</b></td><td>" + amountInPercentage + "</td></tr>");
                                    }
                                    else {

                                        var td = "<td>";

                                        if (remainingAmount)
                                            td = "<td style='background-color: " + negativeColor + ";'>";

                                        $("#summarytreetable").treetable("loadBranch", parentNode,
                                            "<tr data-tt-id=" + newNodeNumber + " data-tt-parent-id=" + parentNodeNumber + ">" +
                                            "<td>" + categoryChild.name + "</td><td><b>" + allocatedAmount + "</b></td><td><b>" + amount + "</b></td>" +
                                            td + "<b style='color: #f5f5f5'>" + remainingAmount + "</b></td><td>" + amountInPercentage + "</td></tr>");
                                    }

                                    if (allocatedAmount) {

                                        nodeName = categoryChild.name + " [" + allocatedAmount + "]";
                                    } else {

                                        nodeName = categoryChild.name;
                                    }

                                } else {

                                    $("#summarytreetable").treetable("loadBranch", parentNode,
                                        "<tr data-tt-id=" + newNodeNumber + " data-tt-parent-id=" + parentNodeNumber + ">" +
                                        "<td>" + categoryChild.name + "</td><td>" + amount + "</td><td>" + amountInPercentage + "</td></tr>");

                                    nodeName = categoryChild.name;
                                }

                                ddChildrenSecondLevelObj[nodeName] = categoryChild.amount;

                                if (categoryChild.children != null && categoryChild.children.length > 0) {

                                    var ddPathThirdLevel = ddPathSecondLevel + " >> " + nodeName;

                                    var ddChildrenThirdLevelObj = {};

                                    drillDownMap[ddPathThirdLevel] = ddChildrenThirdLevelObj;

                                    for (var l = 0; l < categoryChild.children.length; l++) {

                                        parentNodeNumber = (i + 1) + "." + (j + 1) + "." + (k + 1);

                                        newNodeNumber = (i + 1) + "." + (j + 1) + "." + (k + 1) + "." + (l + 1);
//
                                        var parentNode = $("#summarytreetable").treetable("node", parentNodeNumber);

                                        var node = $("#summarytreetable").treetable("node", parentNode)

                                        var subcategoryChild = categoryChild.children[l];

                                        var amount = getUpdatedNumber(subcategoryChild.amount);
                                        var amountInPercentage = getUpdatedNumber(subcategoryChild.amountInPercentage);

                                        var nodeName = subcategoryChild.name;

                                        if (selectedBreakDown == "budget") {

                                            $("#summarytreetable").treetable("loadBranch", parentNode,
                                                "<tr data-tt-id=" + newNodeNumber + " data-tt-parent-id=" + parentNodeNumber + ">" +
                                                "<td>" + subcategoryChild.name + "</td><td></td><td>" + amount + "</td><td></td><td>" + amountInPercentage + "</td></tr>");

                                        } else {

                                            $("#summarytreetable").treetable("loadBranch", parentNode,
                                                "<tr data-tt-id=" + newNodeNumber + " data-tt-parent-id=" + parentNodeNumber + ">" +
                                                "<td>" + subcategoryChild.name + "</td><td>" + amount + "</td><td>" + amountInPercentage + "</td></tr>");

                                        }

                                        ddChildrenThirdLevelObj[nodeName] = subcategoryChild.amount;

                                        if (subcategoryChild.children != null && subcategoryChild.children.length > 0) {

                                            var ddPathFourthLevel = ddPathThirdLevel + " >> " + nodeName;

                                            var ddChildrenFourthLevelObj = {};

                                            drillDownMap[ddPathFourthLevel] = ddChildrenFourthLevelObj;

                                            for (var m = 0; m < subcategoryChild.children.length; m++) {

                                                parentNodeNumber = (i + 1) + "." + (j + 1) + "." + (k + 1) + "." + (l + 1);

                                                newNodeNumber = (i + 1) + "." + (j + 1) + "." + (k + 1) + "." + (l + 1) + "." + (m + 1);
//
                                                var parentNode = $("#summarytreetable").treetable("node", parentNodeNumber);

                                                var node = $("#summarytreetable").treetable("node", parentNode);

                                                var leafChild = subcategoryChild.children[m];

                                                // console.log("leafChild.name === " + leafChild.name);

                                                var amount = getUpdatedNumber(leafChild.amount);
                                                var amountInPercentage = getUpdatedNumber(leafChild.amountInPercentage);

                                                var nodeName = leafChild.name;

                                                if (selectedBreakDown == "budget") {

                                                    $("#summarytreetable").treetable("loadBranch", parentNode,
                                                        "<tr data-tt-id=" + newNodeNumber + " data-tt-parent-id=" + parentNodeNumber + ">" +
                                                        "<td>" + leafChild.name + "</td><td></td><td>" + amount + "</td><td></td><td>" + amountInPercentage + "</td></tr>");
                                                } else {

                                                    $("#summarytreetable").treetable("loadBranch", parentNode,
                                                        "<tr data-tt-id=" + newNodeNumber + " data-tt-parent-id=" + parentNodeNumber + ">" +
                                                        "<td>" + leafChild.name + "</td><td>" + amount + "</td><td>" + amountInPercentage + "</td></tr>");
                                                }

                                                ddChildrenFourthLevelObj[nodeName] = leafChild.amount;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    $("#summarytreetable").treetable("collapseAll");
                }

                // console.log("drillDownMap === " + JSON.stringify(drillDownMap));//JSON.stringify(obj)
//                $("#summarytreetable").treetable("expandNode", 1);
//                $("#summarytreetable").treetable("expandNode", 2);
//                $("#summarytreetable").treetable("expandNode", $("#example-basic").data("1.1.1"));
//                $("#summarytreetable").treetable("expandNode", 3);

                if (defaultExpandState == 'expandAll') {

                    $('#summarytreetable').treetable('expandAll');
                } else {
                    for (var i = 0; i < len; i++) {
                        var node = $("#summarytreetable").treetable("node", i + 1);

                        $("#summarytreetable").treetable("expandNode", (i + 1));

                        if (defaultExpandState == 'expandToTwoLevels')
                            for (var j = 0; j < array[i].children.length; j++) {

                                $("#summarytreetable").treetable("expandNode", (i + 1) + "." + (j + 1));
                            }
                    }
                }

                if (defaultView == 'bothViews') {

                    loadChart();
                }

            } else if (defaultView == 'chartView') {

                noOfRootElements = array.length;

                var len = array.length;

                prevArrayLength = len;

                totalLevelOneNodes = len;

                for (var i = 0; i < len; i++) {

                    var ddChildrenObj = {};

                    if (array[i].children.length > 0)
                        drillDownMap[array[i].name] = ddChildrenObj;

                    for (var j = 0; j < array[i].children.length; j++) {

                        var childAt = array[i].children[j];

                        var nodeName = "";

                        if (selectedBreakDown == "budget") {

                            var allocatedAmount = getUpdatedNumber(childAt.allocatedAmount);

                            nodeName = childAt.name + " [" + allocatedAmount + "]";

                        } else {

                            nodeName = childAt.name;
                        }

                        ddChildrenObj[nodeName] = childAt.amount;

                        if (childAt.children != null && childAt.children.length > 0) {

                            var ddPathSecondLevel = array[i].name + " >> " + nodeName;

                            var ddChildrenSecondLevelObj = {};

                            drillDownMap[ddPathSecondLevel] = ddChildrenSecondLevelObj;

                            for (var k = 0; k < childAt.children.length; k++) {

                                var categoryChild = childAt.children[k];

                                var nodeName = "";

                                if (selectedBreakDown == "budget") {

                                    var allocatedAmount = getUpdatedNumber(categoryChild.allocatedAmount);

                                    nodeName = categoryChild.name + " [" + allocatedAmount + "]";

                                } else {

                                    nodeName = categoryChild.name;
                                }

                                ddChildrenSecondLevelObj[nodeName] = categoryChild.amount;

                                if (categoryChild.children != null && categoryChild.children.length > 0) {

                                    var ddPathThirdLevel = ddPathSecondLevel + " >> " + nodeName;

                                    var ddChildrenThirdLevelObj = {};

                                    drillDownMap[ddPathThirdLevel] = ddChildrenThirdLevelObj;

                                    for (var l = 0; l < categoryChild.children.length; l++) {

                                        var subcategoryChild = categoryChild.children[l];

                                        var nodeName = subcategoryChild.name;

                                        ddChildrenThirdLevelObj[nodeName] = subcategoryChild.amount;

                                        if (subcategoryChild.children != null && subcategoryChild.children.length > 0) {

                                            var ddPathFourthLevel = ddPathThirdLevel + " >> " + nodeName;

                                            var ddChildrenFourthLevelObj = {};

                                            drillDownMap[ddPathFourthLevel] = ddChildrenFourthLevelObj;

                                            for (var m = 0; m < subcategoryChild.children.length; m++) {

                                                var leafChild = subcategoryChild.children[m];

                                                // console.log("leafChild.name === " + leafChild.name);

                                                var nodeName = leafChild.name;

                                                ddChildrenFourthLevelObj[nodeName] = leafChild.amount;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                loadChart();
            }

            function loadChart() {

                currentDataList = list;
                currentDDMap = drillDownMap;

                var drillOneLevelDownByDefault = (len == 1);

                // create home button
                var pathDiv = document.getElementById("path-container");

                var pathBtn = document.createElement("button");
                pathBtn.type = "button";
                pathBtn.id = "chart-root-btn";
                pathBtn.name = "chart-root-btn";
                pathBtn.innerText = "Home";
                pathBtn.setAttribute('class', "btn btn-info btn-sm drilldownbutton");
                // pathBtn.setAttribute('style', "margin-left: 20px;");
                pathBtn.onclick = function (pathBtnClickEvent) {
                    loadChartRoot();
                }

                pathDiv.appendChild(pathBtn);

                updateChart(list, drillDownMap, drillOneLevelDownByDefault);
            }

        }).done(function () {
//                document.getElementById('loader').innerHTML = "";
        });

    }

    $(document).ready(function () {

        $(document).on("click", "#summary-view-button", function () {  // When HTML DOM "click" event is invoked on element with ID "somebutton", execute the following function...

            loadSummary();
        });

        var viewButton = document.getElementById("summary-view-button");

        viewButton.click(); // this will trigger the click event

        // $("#pie").addEventListener('mousedown', function (e) {
        //     e.preventDefault();
        // }, false);
    });

    var chartType = 'doughnut';

    function changeChartType() {

        chartType = document.getElementById("chartTypeSelection").value;

        // chartType = chartType.toLowerCase();

        updateChart(currentDataList, currentDDMap, (noOfRootElements == 1));
    }

    function loadChartRoot() {

        // $('#chart-root-btn').nextAll().remove();

        updateChart(currentDataList, currentDDMap, false);
    }

    function clearCanvas() {

        document.getElementById("chart-container").innerHTML = '&nbsp;';
        document.getElementById("chart-container").innerHTML = '<canvas id="chart" style="width: 100%;"></canvas>';
    }

    var dynamicColors = function () {
        var r = Math.floor(Math.random() * 255);
        var g = Math.floor(Math.random() * 255);
        var b = Math.floor(Math.random() * 255);
        var alpha = 1;
        return "rgba(" + r + "," + g + "," + b + "," + alpha + ")";
    };

    function adjustAxis(ddData, myChart) {

        if (chartType == 'bar' || chartType == 'horizontalBar') {

            var maxVal = 0;
            var minVal = 0;

            for (var i = 0; i < ddData.length; i++) {

                var currentVal = ddData[i];

                // console.log("currentVal type numer ? ===============  " + (typeof ddData[i] == 'number') + " , " + currentVal);

                if (typeof ddData[i] != 'number') {

                    currentVal = parseFloat(ddData[i]);
                }

                if (currentVal > maxVal) {

                    maxVal = currentVal;
                }

                if (currentVal < minVal) {

                    minVal = currentVal;
                }
            }

            if (minVal < 0) {
                // minVal = minVal + (minVal * 0.15);
            }

            maxVal = maxVal + (maxVal * 0.10);

            if (chartType == 'bar') {
                myChart.options.scales.yAxes[0].ticks.min = minVal;
                myChart.options.scales.yAxes[0].ticks.max = maxVal;
                myChart.options.scales.yAxes[0].afterTickToLabelConversion = function (scaleInstance) {

                    scaleInstance.ticks[0] = null;
                    scaleInstance.ticksAsNumbers[0] = null;

                };
            }
            else {
                myChart.options.scales.xAxes[0].ticks.min = minVal;
                myChart.options.scales.xAxes[0].ticks.max = maxVal;
                myChart.options.scales.xAxes[0].afterTickToLabelConversion = function (scaleInstance) {
                    scaleInstance.ticks[scaleInstance.ticks.length - 1] = null;
                    scaleInstance.ticksAsNumbers[scaleInstance.ticksAsNumbers.length - 1] = null;

                };
            }
        }
    }

    function updateBarWidthForVerticalBar(data, myChart) {

        if (data.length == 1) {

            myChart.options.scales.xAxes[0].barPercentage = 0.2;
            myChart.options.scales.xAxes[0].categoryPercentage = 1;
        } else {

            myChart.options.scales.xAxes[0].barPercentage = 0.9;
            myChart.options.scales.xAxes[0].categoryPercentage = 0.8;
        }
    }

    function updateBarWidthForHorizontalBar(data, myChart) {

        if (data.length == 1) {
            myChart.options.scales.yAxes[0].barPercentage = 0.2;
            myChart.options.scales.yAxes[0].categoryPercentage = 1;
        } else {
            myChart.options.scales.yAxes[0].barPercentage = 0.9;
            myChart.options.scales.yAxes[0].categoryPercentage = 0.8;
        }
    }

    function updateChart(list, drillDownMap, drillOneLevelDownByDefault) {

        if (list == null) {

            return;
        }

        chartType = document.getElementById("chartTypeSelection").value;

        // Clear path after home

        $('#chart-root-btn').nextAll().remove();

        // Clear canvas

        clearCanvas();

        var array = JSON.parse(list);

        var labels = [];
        var data = [];
        var len = array.length;

        if (len == 1) {

            labels.push(array[0].name);
            data.push(100);

        } else {

            var length = 0;

            for (var i = 0; i < len; i++) {

                if (drillDownMap.hasOwnProperty(array[i].name)) {
                    length++;
                }
            }
            var value = (100 / length).toFixed(0);

            for (var i = 0; i < len; i++) {

                if (drillDownMap.hasOwnProperty(array[i].name)) {

                    labels.push(array[i].name);
                    data.push(value);
                }
            }
        }


        var layoutPaddingBottom = 40;

        var width = window.innerWidth;

        if (width <= 768) {

            layoutPaddingBottom = 10;
        }

        Chart.defaults.global.defaultFontFamily = "'Poppins', 'sans-serif'";
        // Chart.defaults.global.barThickness = 120;
        // Chart.defaults.global.maxBarThickness = 120;
        // Chart.defaults.global.barPercentage = 0.1;
        // Chart.defaults.global.categoryPercentage = 0.1;

        var showLegend = false;

        Chart.defaults.global.plugins.datalabels.display = (chartType == 'bar' || chartType == 'horizontalBar');

        var ctx = document.getElementById("chart").getContext('2d');

        var myChart = new Chart(ctx, {
            type: chartType,
            data: {
                labels: labels,
                datasets: [{
                    // label: 'Amount',
                    data: data,
                    // fill: false,
                    backgroundColor: bgColor,
                    borderColor: bgColor,
                    borderWidth: 1
                }]
            },
            options: {
                // rotation: (-0.5 * Math.PI),
                // aspectRatio: 4/3,
                // tooltips: false,
                // elements: {
                //     rectangle: {
                //         borderWidth: 2,
                //     }
                // },
                layout: {
                    padding: {
                        top: 20,
                        right: 16,
                        bottom: layoutPaddingBottom,
                        left: 8
                    }
                },
                responsive: true,
                legend: {
                    display: showLegend,
                    // position: 'bottom',
                },
                hover: {
                    onHover: function (e) {
                        var point = this.getElementAtEvent(e);
                        if (point.length) e.target.style.cursor = 'pointer';
                        else e.target.style.cursor = 'default';
                    }
                },
//                title: {
//                    display: true,
//                    text: 'Chart View'
//                },
                animation: {
                    animateScale: true,
                    animateRotate: true
                },
                tooltips: {
                    titleFontFamily: "'Poppins', 'sans-serif'"
                },
//                legend: {
//                    display: false
//                },
//                 zoomOutPercentage: 55,
//                 plugins: {
//                     legend: false,
//                     outlabels: {
//                         text: '%l %v',
//                         color: 'white',
//                         stretch: 45,
//                         font: {
//                             resizable: true,
//                             minSize: 12,
//                             maxSize: 18
//                         }
//                     }
//                 },

                pieceLabel: {
                    render: function (args) {
                        // args will be something like:
                        // { label: 'Label', value: 123, percentage: 50, index: 0, dataset: {...} }
                        return args.label + ' (' + args.value + ')   ';
                        // return object if it is image
                        // return { src: 'image.png', width: 16, height: 16 };
                    },
                    fontSize: 14,
//                    fontStyle: 'bold',
                    fontColor: '#000',
                    fontFamily: 'Poppins, sans-serif',
//                    arc: true
//                     position: 'outside'
//                    overlap: true
                },
                // plugins: [{
                //     beforeInit: function(chart) {
                //         chart.data.labels.forEach(function(e, i, a) {
                //             if (/\n/.test(e)) {
                //                 a[i] = e.split(/\n/);
                //             }
                //         });
                //     }
                // }],
                plugins: {
                    datalabels: {
                        anchor: 'end',
                        align: 'end',
                        // offset: 4,
                        color: 'black',
                        // display: true,
                        font: {
                            family: 'Poppins, sans-serif'
                            // weight: 'bold'
                        }
                        // formatter: Math.round
                    },
                    // beforeInit: function (chart) {
                    //     myChart.data.labels.forEach(function (e, i, a) {
                    //         if (/\n/.test(e)) {
                    //             a[i] = e.split(/\n/);
                    //         }
                    //     });
                    // }
                },
                // scales: {
                //     yAxes: [{
                //         ticks: {
                //             beginAtZero: false
                //         }
                //     }],
                //     xAxes: [{
                //         ticks: {
                //             // autoSkip: false,
                //             maxRotation: 0,
                //             minRotation: 0
                //         }
                //     }]
                // }
            }
        });

        adjustAxis(data, myChart);

        // apply other axes settings
        if (chartType == 'bar' || chartType == 'horizontalBar') {
            // myChart.options.scales.yAxes.barPercentage = 0.2;
            // myChart.options.scales.yAxes.categoryPercentage = 0.2;
            // myChart.options.scales.yAxes.barThickness = 40;
            // myChart.options.scales.yAxes.maxBarThickness = 20;

            if (chartType == 'bar') {
                myChart.options.scales.yAxes[0].ticks.beginAtZero = true;
                myChart.options.scales.xAxes[0].ticks.maxRotation = 0;
                myChart.options.scales.xAxes[0].ticks.minRotation = 0;

                updateBarWidthForVerticalBar(data, myChart);
                // myChart.options.scales.xAxes[0].categoryPercentage = 0.2 / 10 * myChart.datasets[0].data.length
            }
            else {
                myChart.options.scales.xAxes[0].ticks.beginAtZero = true;
                // myChart.options.scales.yAxes[0].ticks.maxRotation = 0;
                // myChart.options.scales.yAxes[0].ticks.minRotation = 0;
                updateBarWidthForHorizontalBar(data, myChart);
            }
            // myChart.options.scales.xAxes[0].gridLines.offsetGridLines = true;
        } else if (chartType == 'doughnut') {

            myChart.options.pieceLabel.position = 'outside';
        }

        var currentDrillDownPath = [];

        var canvas = document.getElementById("chart");

        // console.log("len === " + len);
        // console.log("drillOneLevelDownByDefault === " + drillOneLevelDownByDefault);

        if (drillOneLevelDownByDefault) {

            applyDrillDown(array[0].name, 100);
        }

        function applyDrillDown(label, value) {

            currentDrillDownPath.push(label);

            var url = "label=" + label + "&value=" + value;
            console.log("url == " + url);
            console.log("currentDrillDownPath = " + currentDrillDownPath);

            var pathString = currentDrillDownPath.join(" >> ");

            var ddObj = drillDownMap[pathString];

            console.log("pathString == " + pathString);

            if (ddObj != null) {

                // alert(url);
                updateChart(ddObj);

                var pathDiv = document.getElementById("path-container");

                var pathBtn = document.createElement("button");
                pathBtn.type = "button";
                pathBtn.id = pathString;
                pathBtn.name = pathString;
                pathBtn.innerText = label;
                pathBtn.setAttribute('class', "btn btn-info btn-sm drilldownbutton");
                // pathBtn.setAttribute('style', "margin-left: 20px;");
                pathBtn.onclick = function (pathBtnClickEvent) {

                    // alert(pathBtnClickEvent.target.id);

                    var targetPathString = pathBtnClickEvent.target.id;

                    // console.log("targetPathString ===> " + targetPathString);

                    var objectAtTargetPath = drillDownMap[targetPathString];

                    if (objectAtTargetPath != null) {

                        currentDrillDownPath = targetPathString.split(" >> ");

                        // since ids with special characters are not liked by jquery,
                        // use regular JS call to get the element and pass it to jquery method
                        var nextElements = $(document.getElementById(targetPathString)).nextAll().remove();

                        // nextElements.each(function (index) {
                        //     //do something
                        //     // if (index >= 1) {
                        //     $(this).remove(); //do specific thing
                        //     // }
                        // });

                        updateChart(objectAtTargetPath);
                    }
                }
                //     dateElement.value = prevDateValue;
                //     var newDatePickerId = "#" + newIdName;
                // <button type="button" class="btn btn-success btn-sm" id="addAnotherTxBtn" onclick="addAnotherTransaction()">
                //
                var arrowText = document.createElement("span");
                arrowText.type = "span";
                arrowText.innerText = ">>";
                // arrowText.setAttribute('class', "drilldownarrow");
                arrowText.setAttribute('style',
                    "color:#575757; font-weight: bold; margin-left:0.40em; padding-left:0px; padding-right:0px; margin-right:0.40em; display:inline-block; ");
                // "color:#575757; font-weight: bold; margin-left:0.40em; padding-left:0px; padding-right:0px; margin-right:0.40em; display:inline-block; ");

                pathDiv.appendChild(arrowText);
                pathDiv.appendChild(pathBtn);
//
//                myChart.data.datasets[0].data.forEach(function (data) {
//                    console.log(data);
//                    myChart.data.datasets[0].data.pop();
//                });

            } else {

                currentDrillDownPath.pop(label);
            }
        }

        canvas.onclick = function (evt) {

            if (document.selection && document.selection.empty) {
                document.selection.empty();
            } else if (window.getSelection) {
                var sel = window.getSelection();
                sel.removeAllRanges();
            }

            var activePoints = myChart.getElementsAtEvent(evt);

            // console.log("activePoints = " + activePoints.className);
            // console.log("activePoints 11 = " + activePoints[0]['_chart'].config.data.labels);

            if (activePoints[0]) {
                var chartData = activePoints[0]['_chart'].config.data;
                var idx = activePoints[0]['_index'];

                console.log("idx === " + idx);

                var label = chartData.labels[idx];
                var value = chartData.datasets[0].data[idx];

                applyDrillDown(label, value);
            }
        }

        function updateChart(ddObj) {

            var ddLabels = [];
            var ddData = [];

            for (var property in ddObj) {
                if (ddObj.hasOwnProperty(property)) {
                    // console.log("ddObj[property] ---> " + ddObj[property]);
                    ddLabels.push(property);

                    var amount = parseFloat(ddObj[property]);

                    if (amount > 0) {
                        amount = amount.toFixed(2);
                    }

                    // console.log(" typeof amount === " + (typeof amount) + " , " + amount);
                    ddData.push(amount);
                }
            }

            myChart.data.labels = ddLabels;
            myChart.data.datasets[0].data = ddData;

            if (ddData.length > bgColor.length) {
                for (var i = bgColor.length; i < ddData.length; i++) {

                    var color = dynamicColors();
                    console.log(color);

                    bgColor.push(color);
                }
                myChart.data.datasets[0].backgroundColor = bgColor;
            }

            adjustAxis(ddData, myChart);

            if (chartType == 'bar' || chartType == 'horizontalBar') {

                if (chartType == 'bar') {

                    updateBarWidthForVerticalBar(ddData, myChart);
                }
                else {

                    updateBarWidthForHorizontalBar(ddData, myChart);
                }
            }

            myChart.update();
        }

        myChart.update();
    }

    function expandOneLevel() {

        $("#summarytreetable").treetable("collapseAll");

        for (var i = 0; i < totalLevelOneNodes; i++) {
            var node = $("#summarytreetable").treetable("node", i + 1);

            $("#summarytreetable").treetable("expandNode", (i + 1));
        }
    }

</script>

<script type="text/javascript">

    $('.loader').hide();

    //    document.getElementById('loader').innerHTML = "";

    var dateStringFrom = "${fromDefault}";
    var dateStringTo = "${toDefault}";

    if (dateStringFrom) {
        //
        // var dateArray = dateStringFrom.split('-')
        // var month = parseInt(dateArray[1]);
        // month = (month - 1);
        //
        // var date = new Date(parseInt(dateArray[2]), month, parseInt(dateArray[0]));

        // document.getElementById("datepickerFrom").value = getFormattedDate(date, format);
        document.getElementById("datepickerFrom").value = dateStringFrom;

    } else {

        document.getElementById("datepickerFrom").value = getFirstDayOfMonth();
    }

    if (dateStringTo) {

        // var dateArray = dateStringTo.split('-')
        // var month = parseInt(dateArray[1]);
        // month = (month - 1);
        //
        // var date = new Date(parseInt(dateArray[2]), month, parseInt(dateArray[0]));
        //
        // document.getElementById("datepickerTo").value = getFormattedDate(date, format);
        document.getElementById("datepickerTo").value = dateStringTo;

    } else {

        document.getElementById("datepickerTo").value = getTodayDate(format);
    }


    //    var viewButton = document.getElementById("button");
    //    viewButton.click(); // this will trigger the click event

    // Highlight selected row
    $("#summarytreetable").on("mousedown", "tr", function () {
        $(".selected").not(this).removeClass("selected");
        $(this).toggleClass("selected");
    });

    //    $('td:nth-child(3),th:nth-child(3)').hide();

</script>
<jsp:include page="footer.jsp"/>