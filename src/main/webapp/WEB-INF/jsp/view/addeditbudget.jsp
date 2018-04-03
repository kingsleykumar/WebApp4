<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Kingsley Kumar
  Date: 24/10/2016
  Time: 23:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="transactionTypes" type="java.util.List<String>"--%>
<%--@elvariable id="categories" type="java.util.List<String>"--%>
<%--@elvariable id="actiontype" type="String"--%>
<%--@elvariable id="budget" type="com.sb.db.Budget"--%>
<%--@elvariable id="dateformat" type="java.lang.string"--%>
<jsp:include page="header.jsp"/>
<jsp:include page="navbar.jsp"/>

<div class="wrapper">
    <jsp:include page="sidebar.jsp"/>

    <div id="content">
        <h3>${title}</h3>
        </br>

        <c:choose>
            <c:when test="${actiontype == 'Add'}">
                <c:set var="caturl" value="/budget/add"/>
                <c:set var="actionName" value="Save"/>
            </c:when>
            <c:otherwise>
                <c:set var="caturl" value="/budget/${budgetName}/edit"/>
                <c:set var="actionName" value="Update"/>
            </c:otherwise>
        </c:choose>


        <form:form method="POST" name="form"
                   action="${caturl}" modelAttribute="budgetcommand" class="form-horizontal"
                   onsubmit="return validateForm()">
        <c:set var="budgetnamelimit" value="${30}"></c:set>
        <c:set var="amountlimit" value="${25}"></c:set>

        <c:set var="budgetname" value=""></c:set>
        <c:set var="budgetamount" value=""></c:set>
        <c:set var="budgetfrom" value=""></c:set>
        <c:set var="budgetto" value=""></c:set>
        <c:set var="readonly" value=""></c:set>

        <c:if test="${not empty budget}">
            <c:set var="budgetname" value="${budget.name}"></c:set>
            <c:set var="budgetamount" value="${budget.amount}"></c:set>
            <c:set var="budgetfrom" value="${budget.dateFrom}"></c:set>
            <c:set var="budgetto" value="${budget.dateTo}"></c:set>
            <%--<c:set var="readonly" value="readonly"></c:set>--%>
        </c:if>


        <div class="container-fluid">

            <div class="form-group">
                <form:label class="col-sm-4 col-md-3 col-lg-2 control-label"
                            path="name">Budget Name:<span class="mandatory">*</span></form:label>
                <div class="col-sm-7 col-md-6 col-lg-4">
                    <form:input type="text" name="budgetname" id="budgetname" value="${budgetname}" path="name"
                                onKeyDown="limitTextField(this.form.budgetname,${budgetnamelimit});"
                                onKeyUp="limitTextField(this.form.budgetname,${budgetnamelimit});"
                                maxlength="${budgetnamelimit}" class="form-control"/>
                </div>
            </div>

            <div class="form-group">
                <form:label class="col-sm-4 col-md-3 col-lg-2 control-label" path="name">Allocated Amount:<span
                    class="mandatory">*</span></form:label>
                <div class="col-sm-7 col-md-6 col-lg-4">
                    <form:input type="text" name="budgetamount" id="budgetamount" value="${budgetamount}" path="amount"
                                onKeyDown="validateNumericInput(event, this.form.budgetamount,${amountlimit});"
                                onKeyUp="validateNumericInput(event, this.form.budgetamount,${amountlimit});"
                                maxlength="${amountlimit}" class="form-control"/>
                </div>
            </div>

                <%--<div class="form-group">--%>
                <%--<label class="col-sm-2">Duration:</label>--%>
                <%--</div>--%>

            <div class="form-group">
                <form:label class="col-sm-4 col-md-3 col-lg-2 control-label" path="from">From:<span
                    class="mandatory">*</span></form:label>
                <div class="col-sm-7 col-md-6 col-lg-4">
                    <form:input type="text" id="datepickerFrom" name="datepickerFrom"
                                value="${budgetfrom}" class="form-control" path="from"/>
                </div>
            </div>

            <div class="form-group">
                <form:label class="col-sm-4 col-md-3 col-lg-2 control-label"
                            path="to">To:<span class="mandatory">*</span></form:label>
                <div class="col-sm-7 col-md-6 col-lg-4">
                    <form:input type="text" id="datepickerTo" name="datepickerTo"
                                value="${budgetto}" class="form-control" path="to"/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 col-lg-2">Budget Per Category</label>
            </div>

            <input type="hidden" id="totalRows" name="totalRows" value="1"/>

            <div class="container-fluid well well-sm" id="budget-div" style="padding-bottom: 0px;">
                    <%--<c:choose>--%>
                    <%--<c:when test="${not empty budget.categoryBudgets}">--%>
                <c:set var="count" value="0" scope="page"/>

                    <%--<c:set var="state" scope="page"/>--%>


                <c:forEach items="${budgetcommand.categoryBudgets}" var="categorybudget" varStatus="status">
                    <c:set var="count" value="${count + 1}" scope="page"/>
                    <%--<c:set var="state" value="${status}" scope="page"/>--%>

                    <div class="container-fluid" style="padding-top:6px; padding-bottom: 0px;">
                        <div class="form-group">
                            <form:label class="col-lg-1 control-label"
                                        path="categoryBudgets[${status.index}].type">Type:</form:label>
                            <div class="col-lg-2">

                                <form:select id="transactiontype${status.index}" name="transactiontype${status.index}"
                                             class="form-control" path="categoryBudgets[${status.index}].type">
                                    <%--<form:option value="NONE" label=""/>--%>
                                    <form:options items="${transactionTypes}"/>
                                </form:select>

                                    <%--<select class="form-control" id="transactiontype${count}"--%>
                                    <%--name="transactiontype${count}">--%>
                                    <%--<c:forEach items="${transactionTypes}" var="element">--%>
                                    <%--<c:choose>--%>
                                    <%--<c:when test="${element == categorybudget.type}">--%>
                                    <%--<option value="${element}" selected>${element}</option>--%>
                                    <%--</c:when>--%>
                                    <%--<c:otherwise>--%>
                                    <%--<option value="${element}">${element}</option>--%>
                                    <%--</c:otherwise>--%>
                                    <%--</c:choose>--%>
                                    <%--&lt;%&ndash;<option value="${element}">${element}</option>&ndash;%&gt;--%>
                                    <%--</c:forEach>--%>
                                    <%--</select>--%>
                            </div>

                            <form:label class="col-lg-1 control-label"
                                        path="categoryBudgets[${status.index}].name">Category:</form:label>
                            <div class="col-lg-3">

                                <form:select id="category" name="category${status.index}" class="form-control"
                                             path="categoryBudgets[${status.index}].name">
                                    <%--<form:option value="NONE" label=""/>--%>
                                    <form:options items="${categories}"/>
                                </form:select>

                                    <%--<select class="form-control" id="category" name="category${count}">--%>
                                    <%--<c:forEach items="${categories}" var="element">--%>
                                    <%--<c:choose>--%>
                                    <%--<c:when test="${element == categorybudget.name}">--%>
                                    <%--<option value="${element}" selected>${element}</option>--%>
                                    <%--</c:when>--%>
                                    <%--<c:otherwise>--%>
                                    <%--<option value="${element}">${element}</option>--%>
                                    <%--</c:otherwise>--%>
                                    <%--</c:choose>--%>
                                    <%--</c:forEach>--%>
                                    <%--</select>--%>
                            </div>

                            <form:label class="col-lg-2 control-label"
                                        path="categoryBudgets[${status.index}].amount">Allocated Amount:</form:label>
                            <div class="col-lg-3">
                                <form:input class="form-control" type="text" name="value${status.index}"
                                            value="${categorybudget.amount}"
                                            path="categoryBudgets[${status.index}].amount"
                                            onKeyDown="validateInputAndUpdateBudgetAmount(event, this,${amountlimit});"
                                            onKeyUp="validateInputAndUpdateBudgetAmount(event, this,${amountlimit});"
                                            maxlength="${amountlimit}"/>
                            </div>
                        </div>

                    </div>

                </c:forEach>
            </div>


            <button type="button" class="btn btn-success btn-sm" id="addAnotherBudgetPerCategoryBtn"
                    onclick="addBudgetPerCategory()">
                <span class="glyphicon glyphicon-plus"></span> Add Another Budget Per Category
            </button>

            <br/><br/>

            <p><input type="submit" class="btn btn-info" value="${actionName}"/></p>
                <%--<p><input type="submit" class="btn btn-info" value="${actiontype}"/></p>--%>
        </div>


    </div>

        <%--<b>Budget Per Category:</b></br></br>--%>


        <%--<button type="button" class="btn btn-basic" onclick="addBudgetPerCategory()">Add Budget Per Category</button>--%>

    </form:form>
</div>
</div>
<style>
    .col-sm-4 {
        width: 180px;
    }
</style>

<script type="text/javascript">

    var allCategories = [];
    <c:forEach items="${categories}" var="id">
    allCategories.push("${id}");
    </c:forEach>

    var allTransactionTypes = [];
    <c:forEach items="${transactionTypes}" var="id">
    allTransactionTypes.push("${id}");
    </c:forEach>

    var format = "${dateformat}";
    var datePickerFormat = format.replace(/yyyy/g, "yy");

    $(function () {
        $("#datepickerFrom").datepicker({dateFormat: datePickerFormat});
        $("#datepickerTo").datepicker({dateFormat: datePickerFormat});
    });

    function validateInputAndUpdateBudgetAmount(event, limitField, limitNum) {

        validateNumericInput(event, limitField, limitNum);

        var x = document.getElementById("totalRows").value;

        var numberOfRows = parseInt(x);

        var totalAmount = 0;

        for (var i = 0; i < numberOfRows; i++) {

            var name = "categoryBudgets[" + i + "].amount";
            // var name = "value" + i;

            // console.log("name = " + name);

            var amountPerCategory = document.getElementsByName(name)[0].value;

            if (amountPerCategory) {

                totalAmount = totalAmount + parseFloat(amountPerCategory);
            }
        }

        if (totalAmount > 0) {

            var budgetAmountElement = document.getElementById("budgetamount");

            budgetAmountElement.value = totalAmount;
        }
    }

    function addBudgetPerCategory() {

        var div = document.getElementById("budget-div");
        <%--var x = ${count};--%>
        var x = document.getElementById("totalRows").value;
        var currentRow = parseInt(x) + 1;

        // console.log("currentRow == " + currentRow);

//        alert(x);

        var newBudgetPerCategoryDiv = document.createElement('div');
        newBudgetPerCategoryDiv.setAttribute('class', "container-fluid");
        newBudgetPerCategoryDiv.setAttribute('style', "padding-bottom: 0px;");

        var firstRowDiv = document.createElement('div');
        firstRowDiv.setAttribute('class', "form-group");
//        firstRowDiv.setAttribute('style', "margin-bottom: 7px");

        var typeLabel = document.createElement('label');
        typeLabel.setAttribute('class', "col-lg-1 control-label");
        <%--typeLabel.setAttribute('path', "categoryBudgets[${currentRow}].type");--%>
        typeLabel.innerText = "Type:";

        var typeDiv = document.createElement('div');
        typeDiv.setAttribute('class', "col-lg-2");

        var typeElement = document.createElement("select");
        typeElement.id = "transactiontype" + x;
        typeElement.name = "categoryBudgets[" + x + "].type";
        // typeElement.name = "transactiontype" + currentRow;
//        typeElement.style.cssText = 'padding: 1em 1em;';
        typeElement.setAttribute('class', "form-control");
        <%--typeElement.setAttribute('path', "categoryBudgets[${currentRow}].type");--%>
//        typeElement.setAttribute('style', "padding: 0.3em 0.3em;");
//        typeElement.style.padding = "2px";

        //Create and append the options
        for (var i = 0; i < allTransactionTypes.length; i++) {
            var option = document.createElement("option");
            option.value = allTransactionTypes[i];
            option.text = allTransactionTypes[i];
            typeElement.appendChild(option);
        }

        var prevTypeElementId = "transactiontype" + (x-1);

        var prevTypeValue = document.getElementById(prevTypeElementId).value;

        typeElement.value = prevTypeValue;

        typeDiv.appendChild(typeElement);

        var categoryLabel = document.createElement('label');
        categoryLabel.setAttribute('class', "col-lg-1 control-label");
        <%--categoryLabel.setAttribute('path', "categoryBudgets[${currentRow}].name");--%>
        categoryLabel.innerText = "Category:";

        var categoryDiv = document.createElement('div');
        categoryDiv.setAttribute('class', "col-lg-3");

        var categoryElement = document.createElement("select");
        categoryElement.id = "category" + x;
        categoryElement.name = "categoryBudgets[" + x + "].name";
        // categoryElement.name = "category" + currentRow;
//        categoryElement.style = "padding: 0.3em";
        categoryElement.setAttribute('class', "form-control");
        <%--categoryElement.setAttribute('path', "categoryBudgets[${currentRow}].name");--%>
//        categoryElement.setAttribute('style', "padding: 0.3em 0.3em;");

//            categoryElement.onchange = function () {
//                updateSubCategory(this, x);
//            };

        //Create and append the options
        for (var i = 0; i < allCategories.length; i++) {
            var option = document.createElement("option");
            option.value = allCategories[i];
            option.text = allCategories[i];
            categoryElement.appendChild(option);
        }

        categoryDiv.appendChild(categoryElement);

        var amtLabel = document.createElement('label');
        amtLabel.setAttribute('class', "col-lg-2 control-label");
        <%--amtLabel.setAttribute('path', "categoryBudgets[${currentRow}].amount");--%>
        amtLabel.innerText = "Allocated Amount:";

        var amtDiv = document.createElement('div');
        amtDiv.setAttribute('class', "col-lg-3");
        amtDiv.appendChild(getTextNumericInputElement("value", x, "", 25));

        firstRowDiv.appendChild(typeLabel);
        firstRowDiv.appendChild(typeDiv);
        firstRowDiv.appendChild(categoryLabel);
        firstRowDiv.appendChild(categoryDiv);
        firstRowDiv.appendChild(amtLabel);
        firstRowDiv.appendChild(amtDiv);

        newBudgetPerCategoryDiv.appendChild(firstRowDiv);

        div.appendChild(newBudgetPerCategoryDiv);

        document.getElementById("totalRows").value = currentRow;
    }

    function getTextNumericInputElement(idNamePrefix, rowIndex, value, inputcharlimit) {

//        var col = document.createElement('td'); // create second column node
//        col.setAttribute('class', "form-control");
//        col.setAttribute('style', "padding:0.3em 0.3em");


        var element = document.createElement("input");
        element.type = "text";
        element.id = idNamePrefix + rowIndex;
        // element.name = idNamePrefix + rowIndex;
        // var rowIndexNew = rowIndex - 1;
        element.name = "categoryBudgets[" + rowIndex + "].amount";

        // console.log("rowIndexNew = " + rowIndexNew);
        // console.log("element.name = " + element.name);

//            element.size = inputcharlimit;
        element.maxLength = inputcharlimit;
        element.value = value;
//        element.style = "padding:2px";
        element.setAttribute('class', "form-control");
        <%--element.setAttribute('path', "categoryBudgets[${rowIndex}].amount");--%>

//        element.setAttribute('style', "padding: 0.3em 0.3em;");

//        element.style = style;
        element.onkeydown = function (event) {
            validateInputAndUpdateBudgetAmount(event, element, inputcharlimit);
        };
        element.onkeyup = function (event) {
            validateInputAndUpdateBudgetAmount(event, element, inputcharlimit);
        };

//        col.appendChild(element);

        return element;
    }


    function getTextInputElement(idNamePrefix, rowIndex, inputcharlimit, style) {

        var col = document.createElement('td'); // create second column node

        var element = document.createElement("input");
        element.type = "text";
        element.id = idNamePrefix + rowIndex;
        element.name = idNamePrefix + rowIndex;
        element.size = inputcharlimit;
        element.style = style;

        col.appendChild(element);

        return col;
    }

    function isEmpty(str) {
        return (!str || 0 === str.length || !str.trim());
    }


    function validateForm() {
        var x = document.getElementById("budgetname").value;
        // console.log("x === " + x);
        // var x = document.forms["form"]["budgetname"].value;
        if (isEmpty(x)) {
            bootbox.alert("Budget Name is required.");
            return false;
        }

        var x = document.getElementById("budgetamount").value;
        // var x = document.forms["form"]["budgetamount"].value;
        if (isEmpty(x)) {
            bootbox.alert("Allocated Amount is required.");
            return false;
        }

        if (isNaN(x)) {
            bootbox.alert("Allocated Amount has to be a numeric value.");
            return false;
        }

        var x = document.getElementById("datepickerFrom").value;
        // var x = document.forms["form"]["datepickerFrom"].value;
        if (isEmpty(x)) {
            bootbox.alert("From Date is required.");
            return false;
        }

        var x = document.getElementById("datepickerTo").value;
        // var x = document.forms["form"]["datepickerTo"].value;
        if (isEmpty(x)) {
            bootbox.alert("To Date is required.");
            return false;
        }
    }

    <%--<c:choose>--%>
    <%--<c:when test="${empty budget}">--%>
    document.getElementById("datepickerFrom").value = getTodayDate(format);
    document.getElementById("datepickerTo").value = getTodayDate(format);
    <%--</c:when>--%>
    <%--<c:otherwise>--%>
    document.getElementById("totalRows").value = ${count};
    <%--console.log(${count});--%>
    <%--</c:otherwise>--%>
    <%--</c:choose>--%>

    document.getElementById("transactiontype0").value = allTransactionTypes[1];
    //    updateSubCategory(document.getElementById("category"), 1);
</script>

<jsp:include page="footer.jsp"/>