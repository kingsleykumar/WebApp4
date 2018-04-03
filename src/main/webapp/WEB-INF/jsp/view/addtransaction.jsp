<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Kingsley Kumar
  Date: 29/08/2016
  Time: 21:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="transactionTypes" type="java.util.List<String>"--%>
<%--@elvariable id="categories" type="java.util.List<String>"--%>
<%--@elvariable id="budgets" type="java.util.List<String>"--%>
<%--@elvariable id="subcategoriesJson" type="java.util.Map<String, List<String>"--%>
<%--@elvariable id="dateBudgetMapJson" type="java.util.Map<String, String>"--%>
<%--@elvariable id="dateformat" type="java.lang.string"--%>
<jsp:include page="header.jsp"/>
<jsp:include page="navbar.jsp"/>

<div class="wrapper">
    <jsp:include page="sidebar.jsp"/>

    <div id="content">
        <h3>${title}</h3>
        </br>

        <%--<button onclick="addAnotherTransaction()">Add Another Transaction</button>--%>
        <%--<br/><br/>--%>

        <c:set var="count" value="0" scope="page"/>

        <form:form method="POST" name="form" id="txform"
                   action="/transaction/add" class="form-horizontal" modelAttribute="transactionCommandWrapper">

            <input type="hidden" id="totalRows" name="totalRows" value="1"/>

            <%--<c:forEach items="${transactioncommands}" var="transactioncommand" varStatus="status">--%>

                <%--<p>${transactioncommands}</p>--%>
                <%--<p>${transactioncommand}</p>--%>
                <%--<p>${transactioncommand.category}</p>--%>
                <%--&lt;%&ndash;<%=transactioncommand%>&ndash;%&gt;--%>
            <%--</c:forEach>--%>


            <c:forEach items="${transactionCommandWrapper.transactionCommands}" var="transactionCommand" varStatus="status">

                <c:set var="count" value="${count + 1}" scope="page"/>

                <div class="container-fluid well" style="padding-bottom: 4px; padding-top: 12px">

                    <div class="form-group" style="margin-bottom: 7px">
                        <form:label class="col-sm-2 control-label txCol1"
                                    path="transactionCommands[${status.index}].date">Date:</form:label>
                        <div class="col-sm-4">
                            <form:input type="text" id="datepicker${status.index}" name="datepicker${status.index}"
                                        class="form-control" path="transactionCommands[${status.index}].date"/>
                        </div>

                        <form:label class="col-sm-2 control-label txCol2"
                                    path="transactionCommands[${status.index}].type">Type:</form:label>
                        <div class="col-sm-4">

                            <form:select id="transactiontype${status.index}" name="transactiontype${status.index}"
                                         class="form-control" path="transactionCommands[${status.index}].type">
                                <%--<form:option value="NONE" label=""/>--%>
                                <form:options items="${transactionTypes}"/>
                            </form:select>


                                <%--<select id="transactiontype1" name="transactiontype1" class="form-control">--%>
                                <%--<c:forEach items="${transactionTypes}" var="element">--%>
                                <%--<option value="${element}">${element}</option>--%>
                                <%--</c:forEach>--%>
                                <%--</select>--%>
                        </div>
                    </div>

                    <div class="form-group" style="margin-bottom: 7px">
                        <form:label class="col-sm-2 control-label txCol1"
                                    path="transactionCommands[${status.index}].category">Category:</form:label>
                        <div class="col-sm-4">

                            <form:select id="category${status.index}" name="category${status.index}"
                                         class="form-control" onchange="updateSubCategory(this, 0)"
                                         path="transactionCommands[${status.index}].category">
                                <%--<form:option value="NONE" label=""/>--%>
                                <form:options items="${categories}"/>
                            </form:select>

                                <%--<select id="category1" name="category1" onchange="updateSubCategory(this, 1)"--%>
                                <%--class="form-control">--%>
                                <%--<c:forEach items="${categories}" var="element">--%>
                                <%--<option value="${element}">${element}</option>--%>
                                <%--</c:forEach>--%>
                                <%--</select>--%>
                        </div>

                        <form:label class="col-sm-2 control-label txCol2"
                                    path="transactionCommands[${status.index}].subcategory">Sub-Category:</form:label>
                        <div class="col-sm-4">
                            <form:select id="subcategory${status.index}" name="subcategory${status.index}"
                                         class="form-control" path="transactionCommands[${status.index}].subcategory">
                            </form:select>
                        </div>
                    </div>

                    <div class="form-group" style="margin-bottom: 7px">
                        <form:label class="col-sm-2 control-label txCol1"
                                    path="transactionCommands[${status.index}].item">Item:</form:label>
                        <div class="col-sm-4">
                            <form:input type="text" id="item1" name="item1"
                                        onKeyDown="limitTextField(this.form.item1,75);"
                                        onKeyUp="limitTextField(this.form.item1,75);"
                                        class="form-control" path="transactionCommands[${status.index}].item"/>
                        </div>

                        <form:label class="col-sm-2 control-label txCol2"
                                    path="transactionCommands[${status.index}].value">Value:</form:label>
                        <div class="col-sm-4">
                            <form:input type="text" id="value1" name="value1"
                                        onKeyDown="validateNumericInput(event, this.form.value1, 25);"
                                        onKeyUp="validateNumericInput(event, this.form.value1, 25);"
                                        class="form-control" path="transactionCommands[${status.index}].value"/>
                        </div>
                    </div>

                    <div class="form-group" style="margin-bottom: 7px">
                        <form:label class="col-sm-2 control-label txCol1"
                                    path="transactionCommands[${status.index}].budget">Budget:</form:label>
                        <div class="col-sm-4">

                            <form:select id="budget${status.index}" name="budget${status.index}"
                                         class="form-control" path="transactionCommands[${status.index}].budget">
                                <form:options items="${budgets}"/>
                            </form:select>

                                <%--<select id="budget1" name="budget1" class="form-control">--%>
                                <%--<c:forEach items="${budgets}" var="element">--%>
                                <%--<option value="${element}">${element}</option>--%>
                                <%--</c:forEach>--%>
                                <%--</select>--%>
                        </div>
                    </div>

                    <form:input type="hidden" id="id${status.index}" name="id${status.index}"
                                path="transactionCommands[${status.index}].id"/>

                </div>


            </c:forEach>


            <button type="button" class="btn btn-success btn-sm" id="addAnotherTxBtn" onclick="addAnotherTransaction()">
                <span class="glyphicon glyphicon-plus"></span> Add Another Transaction
            </button>

            <br/><br/>

            <p><input class="btn btn-info" type="submit" value="Save"/></p>

        </form:form>
    </div>
</div>


<script type="text/javascript">

    var allCategories = [];
    <c:forEach items="${categories}" var="id">
    allCategories.push("${id}");
    </c:forEach>

    var allTransactionTypes = [];
    <c:forEach items="${transactionTypes}" var="id">
    allTransactionTypes.push("${id}");
    </c:forEach>

    var allBudgetNames = [];
    <c:forEach items="${budgets}" var="id">
    allBudgetNames.push("${id}");
    </c:forEach>

    var jsonStr = ${subcategoriesJson};
    var dateBudgetMap = ${dateBudgetMapJson};

    //        console.log(dateBudgetMap);

    var format = "${dateformat}";
    var datePickerFormat = format.replace(/yyyy/g, "yy");

    $(function () {
        $("#datepicker0").datepicker({
            dateFormat: datePickerFormat, onSelect: function (dateText) {
                <%--console.log("Selected date: " + dateText + "; input's current value: " + this.value);--%>

                <%--var dateBudgetMap = ${dateBudgetMapJson};--%>
                <%--var budgetName;--%>

                <%--for (var key in dateBudgetMap) {--%>
                <%--if (key == this.value) {--%>
                <%--budgetName = dateBudgetMap[key];--%>
                <%--}--%>
                <%--//                console.log(key + ': ' + dateBudgetMap[key]);--%>
                <%--}--%>

                <%--console.log(budgetName);--%>
            }
        });
    });


    function addAnotherTransaction() {

        var form = document.getElementById("txform");
        var totalRows = document.getElementById("totalRows").value;
        var x = parseInt(totalRows) - 1; //prev row index
        var txId = parseInt(totalRows);
        // var x = document.getElementById("totalRows").value //= currentRow + 1;
        // var txId = parseInt(x) + 1;

        console.log("x ==== " + x);
        console.log("txId ==== " + txId);

//        alert(txId);

        var newTxDiv = document.createElement('div');
        newTxDiv.setAttribute('class', "container-fluid well");
        newTxDiv.setAttribute('style', "padding-bottom: 4px; padding-top: 12px");

        // FIRST ROW

        var firstRowDiv = document.createElement('div');
        firstRowDiv.setAttribute('class', "form-group");
        firstRowDiv.setAttribute('style', "margin-bottom: 7px");

        var dateLabel = document.createElement('label');
        dateLabel.setAttribute('class', "col-sm-2 control-label txCol1");
        dateLabel.innerText = "Date:";

        var datePickerDiv = document.createElement('div');
        datePickerDiv.setAttribute('class', "col-sm-4");

        var prevDatePickerId = "datepicker" + x;

        var prevDateValue = document.getElementById(prevDatePickerId).value;

        var dateElement = document.createElement("input");
        dateElement.type = "text";
        var newIdName = "datepicker" + txId;
        dateElement.id = newIdName;
        // dateElement.name = newIdName;
        dateElement.name = "transactionCommands[" + txId + "].date";

        dateElement.setAttribute('class', "form-control");
        dateElement.value = prevDateValue;
        var newDatePickerId = "#" + newIdName;

        datePickerDiv.appendChild(dateElement);

        var typeLabel = document.createElement('label');
        typeLabel.setAttribute('class', "col-sm-2 control-label txCol2");
        typeLabel.innerText = "Type:";

        var typeDiv = document.createElement('div');
        typeDiv.setAttribute('class', "col-sm-4");

        var typeElement = document.createElement("select");
        typeElement.id = "transactiontype" + txId;
        // typeElement.name = "transactiontype" + txId;
        typeElement.name = "transactionCommands[" + txId + "].type";

//        typeElement.setAttribute('style', "width: 85px; height: 21px");
        typeElement.setAttribute('class', "form-control");

        for (var i = 0; i < allTransactionTypes.length; i++) {
            var option = document.createElement("option");
            option.value = allTransactionTypes[i];
            option.text = allTransactionTypes[i];
            typeElement.appendChild(option);
        }

        var prevTypeElementId = "transactiontype" + x;

        var prevTypeValue = document.getElementById(prevTypeElementId).value;

        typeElement.value = prevTypeValue;

        typeDiv.appendChild(typeElement);

        firstRowDiv.appendChild(dateLabel);
        firstRowDiv.appendChild(datePickerDiv);

        firstRowDiv.appendChild(typeLabel);
        firstRowDiv.appendChild(typeDiv);

        newTxDiv.appendChild(firstRowDiv);

        // SECOND ROW

        var secondRowDiv = document.createElement('div');
        secondRowDiv.setAttribute('class', "form-group");
        secondRowDiv.setAttribute('style', "margin-bottom: 7px");

        var categoryLabel = document.createElement('label');
        categoryLabel.setAttribute('class', "col-sm-2 control-label txCol1");
        categoryLabel.innerText = "Category:";

        var categoryDiv = document.createElement('div');
        categoryDiv.setAttribute('class', "col-sm-4");

        var prevCategoryId = "category" + x;

        var prevCategoryValue = document.getElementById(prevCategoryId).value;

        var categoryElement = document.createElement("select");
        categoryElement.id = "category" + txId;
        // categoryElement.name = "category" + txId;
        categoryElement.name = "transactionCommands[" + txId + "].category";
        categoryElement.setAttribute('class', "form-control");

        categoryElement.onchange = function () {
            updateSubCategory(this, txId);
        };

        //Create and append the options
        for (var i = 0; i < allCategories.length; i++) {
            var option = document.createElement("option");
            option.value = allCategories[i];
            option.text = allCategories[i];
            categoryElement.appendChild(option);
        }

        categoryElement.value = prevCategoryValue;

        categoryDiv.appendChild(categoryElement);

        var subCategoryLabel = document.createElement('label');
        subCategoryLabel.setAttribute('class', "col-sm-2 control-label txCol2");
        subCategoryLabel.innerText = "Sub-Category:";

        var subCategoryDiv = document.createElement('div');
        subCategoryDiv.setAttribute('class', "col-sm-4");

        var subCategoryElement = document.createElement("select");
        subCategoryElement.id = "subcategory" + txId;
        // subCategoryElement.name = "subcategory" + txId;
        subCategoryElement.name = "transactionCommands[" + txId + "].subcategory";
        subCategoryElement.setAttribute('class', "form-control");

        var subCategoryArray = jsonStr;

        var allSubCategories = subCategoryArray[categoryElement.value];

        subCategoryElement.innerHTML = "";

        for (var i = 0; i < allSubCategories.length; i++) {
            var option = document.createElement("option");
            option.value = allSubCategories[i];
            option.text = allSubCategories[i];
            subCategoryElement.appendChild(option);
        }

        var prevSubCategoryElementId = "subcategory" + x;

        var prevSubCategoryValue = document.getElementById(prevSubCategoryElementId).value;

        subCategoryElement.value = prevSubCategoryValue;

        subCategoryDiv.appendChild(subCategoryElement);

        secondRowDiv.appendChild(categoryLabel);
        secondRowDiv.appendChild(categoryDiv);

        secondRowDiv.appendChild(subCategoryLabel);
        secondRowDiv.appendChild(subCategoryDiv);

        newTxDiv.appendChild(secondRowDiv);

        //THIRD ROW

        var thirdRowDiv = document.createElement('div');
        thirdRowDiv.setAttribute('class', "form-group");
        thirdRowDiv.setAttribute('style', "margin-bottom: 7px");

        var itemLabel = document.createElement('label');
        itemLabel.setAttribute('class', "col-sm-2 control-label txCol1");
        itemLabel.innerText = "Item:";

        var itemDiv = document.createElement('div');
        itemDiv.setAttribute('class', "col-sm-4");

        var itemInputElement = document.createElement("input");
        itemInputElement.type = "text";
        itemInputElement.id = "item" + txId;
        // itemInputElement.name = "item" + txId;
        itemInputElement.name = "transactionCommands[" + txId + "].item";

        itemInputElement.setAttribute('class', "form-control");
        itemInputElement.maxLength = 75;
        itemInputElement.onkeydown = function () {
            limitTextField(itemInputElement, 75);
        };
        itemInputElement.onkeyup = function () {
            limitTextField(itemInputElement, 75);
        };

        itemDiv.appendChild(itemInputElement);

        var valueLabel = document.createElement('label');
        valueLabel.setAttribute('class', "col-sm-2 control-label txCol2");
        valueLabel.innerText = "Value:";

        var valueDiv = document.createElement('div');
        valueDiv.setAttribute('class', "col-sm-4");

        var valueInputElement = document.createElement("input");
        valueInputElement.type = "text";
        valueInputElement.id = "value" + txId;
        // valueInputElement.name = "value" + txId;
        valueInputElement.name = "transactionCommands[" + txId + "].value";

        valueInputElement.setAttribute('class', "form-control");

        valueInputElement.maxLength = 25;

        valueInputElement.onkeydown = function (event) {
            validateNumericInput(event, valueInputElement, 25);
        };
        valueInputElement.onkeyup = function (event) {
            validateNumericInput(event, valueInputElement, 25);
        };

        valueDiv.appendChild(valueInputElement);

        thirdRowDiv.appendChild(itemLabel);
        thirdRowDiv.appendChild(itemDiv);

        thirdRowDiv.appendChild(valueLabel);
        thirdRowDiv.appendChild(valueDiv);

        newTxDiv.appendChild(thirdRowDiv);

        // FOURTH ROW

        var fourthRowDiv = document.createElement('div');
        fourthRowDiv.setAttribute('class', "form-group");
        fourthRowDiv.setAttribute('style', "margin-bottom: 7px");

        var budgeLabel = document.createElement('label');
        budgeLabel.setAttribute('class', "col-sm-2 control-label txCol1");
        budgeLabel.innerText = "Budget:";

        var budgetDiv = document.createElement('div');
        budgetDiv.setAttribute('class', "col-sm-4");

        var prevBudgetId = "budget" + x;

        var prevBudgetValue = document.getElementById(prevBudgetId).value;

        var budgetElement = document.createElement("select");
        budgetElement.id = "budget" + txId;
        // budgetElement.name = "budget" + txId;
        budgetElement.name = "transactionCommands[" + txId + "].budget";
        budgetElement.setAttribute('class', "form-control");

        budgetElement.innerHTML = "";

        for (var i = 0; i < allBudgetNames.length; i++) {
            var option = document.createElement("option");
            option.value = allBudgetNames[i];
            option.text = allBudgetNames[i];
            budgetElement.appendChild(option);
        }

        budgetElement.value = prevBudgetValue;

        budgetDiv.appendChild(budgetElement);

        fourthRowDiv.appendChild(budgeLabel);
        fourthRowDiv.appendChild(budgetDiv);

        newTxDiv.appendChild(fourthRowDiv);

        var element = document.createElement("input");
        element.type = "hidden";
        element.id = "id" + txId;
        // element.name = "id" + x;
        element.name = "transactionCommands[" + txId + "].id";
        element.size = 30;
        element.value = txId;

//            col.style = "display:none;";

        newTxDiv.appendChild(element);

        form.insertBefore(newTxDiv, document.getElementById("addAnotherTxBtn"));

        $(newDatePickerId).datepicker({dateFormat: datePickerFormat});

        var totalRows = txId + 1;

        document.getElementById("totalRows").value = totalRows;
    }

    function updateSubCategory(obj, index) {

        var selectedValue = obj.value;

//            alert(jsonStr);

        console.log("index = " + index);

//        var subCategoryElementId = "subcategory";

//        if (index > 1) {

        var subCategoryElementId = "subcategory" + index;
//        }

        console.log(subCategoryElementId);
//            console.log(jsonStr);

        var subCategoryElement = document.getElementById(subCategoryElementId);

        // console.log(subCategoryElement);

        var subCategoryArray = jsonStr;

        var allSubCategories = subCategoryArray[selectedValue];

        subCategoryElement.innerHTML = "";

        for (var i = 0; i < allSubCategories.length; i++) {
            var option = document.createElement("option");
            option.value = allSubCategories[i];
            option.text = allSubCategories[i];
            subCategoryElement.appendChild(option);
        }
    }


</script>

<script type="text/javascript">
    document.getElementById("datepicker0").value = getTodayDate(format);
    document.getElementById("transactiontype0").value = allTransactionTypes[1];

    var dateBudgetMap = ${dateBudgetMapJson};
    var budgetName;

    for (var key in dateBudgetMap) {
        if (key == document.getElementById("datepicker0").value) {
//            if(dateBudgetMap.hasOwnProperty(document.getElementById("datepicker").value)) {
            budgetName = dateBudgetMap[key];
        }
    }
    document.getElementById("budget0").value = budgetName;

    updateSubCategory(document.getElementById("category0"), 0);
</script>

<jsp:include page="footer.jsp"/>