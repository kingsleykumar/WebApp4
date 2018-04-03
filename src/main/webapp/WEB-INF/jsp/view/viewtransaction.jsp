<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Kingsley Kumar
  Date: 04/09/2016
  Time: 23:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="transactionTypes" type="java.util.List<String>"--%>
<%--@elvariable id="categories" type="java.util.List<String>"--%>
<%--@elvariable id="subcategoriesJson" type="java.util.Map<String, List<String>"--%>
<%--@elvariable id="budgets" type="java.util.List<String>"--%>
<%--@elvariable id="dateformat" type="java.lang.string"--%>
<%--@elvariable id="defaultTimeRange" type="java.lang.string"--%>
<jsp:include page="header.jsp"/>
<jsp:include page="navbar.jsp"/>

<div class="wrapper">
    <jsp:include page="sidebar.jsp"/>

    <div id="content">
        <h3>${title}</h3>
        </br>

        <div>

            <div class="container-fluid form-horizontal well" style="padding-bottom: 4px; padding-top: 12px">

                <div class="form-group" style="margin-bottom: 7px">
                    <label class="col-lg-1 control-label" style="width: 110px;">From Date:</label>
                    <div class="col-lg-2"><input type="text" id="datepickerFrom" name="datepicker"
                                                 class="form-control"/>
                    </div>

                    <label class="col-lg-1 control-label">To Date:</label>
                    <div class="col-lg-2"><input type="text" id="datepickerTo" name="datepicker" class="form-control"/>
                    </div>

                    <label class="col-lg-1 control-label">Category:</label>
                    <div class="col-lg-3">
                        <select id="selectcategory" name="selectcategory" class="form-control">
                            <option value=""></option>
                            <c:forEach items="${categories}" var="element">
                                <option value="${element}">${element}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <%--<label class="col-md-1 visible-sm"></label>--%>
                    <div class="col-lg-1">
                        <button type="button" class="btn btn-info" id="tx-view-button" class="form-control">View
                        </button>
                    </div>
                </div>
            </div>

            </br>
            <%--</br>--%>
            <div class="loader" id="loader" align="center">
                <img id="loaderImg" src="/images/ajax-loader.gif"/>
            </div>

            <form:form method="POST" name="form"
                       action="/transaction/update"
                       class="form-horizontal"
                       modelAttribute="transactionCommandWrapper"> <%--onsubmit="return validateForm()"--%>
                <input type="hidden" id="totalRows" name="totalRows" value="1"/>

                <div id="txlist">
                </div>

                <p><input class="btn btn-info" id="save" type="submit" value="Save"/></p>

            </form:form>

        </div>

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

    var jsonStr = ${subcategoriesJson};

    var format = "${dateformat}";
    var datePickerFormat = format.replace(/yyyy/g, "yy");

    var allBudgetNames = [];
    <c:forEach items="${budgets}" var="id">
    allBudgetNames.push("${id}");
    </c:forEach>

    $(function () {
        $("#datepickerFrom").datepicker({dateFormat: datePickerFormat});
        $("#datepickerTo").datepicker({dateFormat: datePickerFormat});
    });

    function getDate(element) {
        var date;
        try {
            date = $.datepicker.parseDate(datePickerFormat, element.value);
        } catch (error) {
            date = null;
        }

        return date;
    }

    function getFormattedDate(date, format) {

        var dd = date.getDate();
        var mm = date.getMonth() + 1; //January is 0!
        var yyyy = date.getFullYear();

        if (dd < 10) {
            dd = '0' + dd;
        }

        if (mm < 10) {
            mm = '0' + mm;
        }

        if (format == "dd/mm/yyyy") {

            return dd + '/' + mm + '/' + yyyy;

        } else if (format == "dd.mm.yyyy") {

            return dd + '.' + mm + '.' + yyyy;

        } else if (format == "dd-mm-yyyy") {

            return dd + '-' + mm + '-' + yyyy;

        } else if (format == "yyyy-mm-dd") {

            return yyyy + '-' + mm + '-' + dd;

        } else if (format == "yyyy.mm.dd") {

            return yyyy + '.' + mm + '.' + dd;

        } else if (format == "mm/dd/yyyy") {

            return mm + '/' + dd + '/' + yyyy;
        }

        return dd + '/' + mm + '/' + yyyy;
    }

    function getFirstDayOfMonth() {

        var today = new Date();

        var firstDay = new Date(today.getFullYear(), today.getMonth(), 1);

        return getFormattedDate(firstDay, format);
    }

    function getLastDayOfMonth() {

        var today = new Date();

        var lastDay = new Date(today.getFullYear(), today.getMonth() + 1, 0);

        return getFormattedDate(lastDay, format);
    }

    function getPrevious() {

        var element = document.getElementById("datepicker");
        var selectedDate = getDate(element);
        selectedDate.setDate(selectedDate.getDate() - 1);
        element.value = getFormattedDate(selectedDate, format);
    }

    function getNext() {

        var element = document.getElementById("datepicker");
        var selectedDate = getDate(element);
        selectedDate.setDate(selectedDate.getDate() + 1);
        element.value = getFormattedDate(selectedDate, format);
    }

    //        $(document).ready(function() {
    //            $('#button').onclick(function(event) {
    //                $.get('viewtransaction', {
    //                    "action" : "view",
    //                    "date" : getFormatteDate(selectedDate)
    //                }, function(responseText) {
    //                    $('#transactions').text(responseText);
    //                });
    //            });
    //        });
    //        function getOutgoings() {
    //
    //            var element = document.getElementById("datepicker");
    //            var selectedDate = getDate(element);
    //
    ////            alert(getFormattedDate(selectedDate));
    //            $.get('viewtransaction', {
    //                "action": "view",
    //                "date": getFormatteDate(selectedDate)
    //            }, function (responseText) {
    //                $('#transactions').text(responseText);
    //            });
    //        }


    //        var myList = [{"name": "abc", "age": 50},
    //            {"age": "25", "hobby": "swimming"},
    //            {"name": "xyz", "hobby": "programming"}];

    function addAllColumnHeaders(myList, selector) {

        var columnSet = [];

        var headerTr$ = $('<tr/>');

        for (var i = 0; i < myList.length; i++) {
            var rowHash = myList[i];
            for (var key in rowHash) {
                if ($.inArray(key, columnSet) == -1) {
                    columnSet.push(key);
                    headerTr$.append($('<th/>').html(key));
                }
            }
        }
        $(selector).append(headerTr$);

        return columnSet;
    }

    // Builds the HTML Table out of myList.
    function buildHtmlTable(selector, myList) {
//            var columns = addAllColumnHeaders(myList, selector);

        var columns = [];

        if (myList.length > 0) {

            var rowHash = myList[0];

            for (var key in rowHash) {

                columns.push(key);
            }

            console.log("output :" + myList.length);

            for (var i = 0; i < myList.length; i++) {

                var index = i;
                // var index = i + 1;
                addRow(selector, columns, myList[i], index);
//                    var row$ = $('<tr/>');
//                    for (var colIndex = 0; colIndex < columns.length; colIndex++) {
//                        var cellValue = myList[i][columns[colIndex]];
//
//                        if (cellValue == null) {
//                            cellValue = "";
//                        }
//
//                        row$.append($('<td/>').html(cellValue));
//                    }
//                    $(selector).append(row$);
            }
        }
        //
    }


    function addRow(selector, columns, hashMap, x) {

//        var form = document.getElementById("txform");

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

//        var prevDatePickerId = "datepicker" + x;

        var dateElement = document.createElement("input");
        dateElement.type = "text";
        var newIdName = "datepicker" + x;
        dateElement.id = newIdName;
        dateElement.name = newIdName;
        dateElement.value = hashMap[columns[0]];
        dateElement.setAttribute('class', "form-control");
        dateElement.readOnly = "readonly";
        dateElement.name = "transactionCommands[" + x + "].date";

        datePickerDiv.appendChild(dateElement);

        var typeLabel = document.createElement('label');
        typeLabel.setAttribute('class', "col-sm-2 control-label txCol2");
        typeLabel.innerText = "Type:";

        var typeDiv = document.createElement('div');
        typeDiv.setAttribute('class', "col-sm-4");

        var typeElement = document.createElement("select");
        typeElement.id = "transactiontype" + x;
        typeElement.name = "transactiontype" + x;
        typeElement.setAttribute('class', "form-control");


        //Create and append the options
        for (var i = 0; i < allTransactionTypes.length; i++) {
            var option = document.createElement("option");
            option.value = allTransactionTypes[i];
            option.text = allTransactionTypes[i];
            typeElement.appendChild(option);
        }

        typeElement.value = hashMap[columns[1]];
        typeElement.name = "transactionCommands[" + x + "].type";

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

        var categoryElement = document.createElement("select");
        categoryElement.id = "category" + x;
        categoryElement.name = "category" + x;
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

        categoryElement.value = hashMap[columns[2]];
        categoryElement.name = "transactionCommands[" + x + "].category";

        categoryDiv.appendChild(categoryElement);

        var subCategoryLabel = document.createElement('label');
        subCategoryLabel.setAttribute('class', "col-sm-2 control-label txCol2");
        subCategoryLabel.innerText = "Sub-Category:";

        var subCategoryDiv = document.createElement('div');
        subCategoryDiv.setAttribute('class', "col-sm-4");

        var subCategoryElement = document.createElement("select");
        subCategoryElement.id = "subcategory" + x;
        subCategoryElement.name = "subcategory" + x;
        subCategoryElement.setAttribute('class', "form-control");

        var subCategoryArray = jsonStr;

        var allSubCategories = subCategoryArray[hashMap[columns[2]]];

        subCategoryElement.innerHTML = "";

        var subCategoryExists = false;

        for (var i = 0; i < allSubCategories.length; i++) {
            var option = document.createElement("option");
            option.value = allSubCategories[i];
            option.text = allSubCategories[i];
            subCategoryElement.appendChild(option);

            if (hashMap[columns[3]] != null && hashMap[columns[3]] != '' && allSubCategories[i] == hashMap[columns[3]]) {

                subCategoryExists = true;
            }
        }

//        console.log("hashMap[columns[3] == " +hashMap[columns[3]]);
//        console.log("subCategoryExists == " +subCategoryExists);

        if (subCategoryExists)

            subCategoryElement.value = hashMap[columns[3]];
        subCategoryElement.name = "transactionCommands[" + x + "].subcategory";

        subCategoryDiv.appendChild(subCategoryElement);

        secondRowDiv.appendChild(categoryLabel);
        secondRowDiv.appendChild(categoryDiv);

        secondRowDiv.appendChild(subCategoryLabel);
        secondRowDiv.appendChild(subCategoryDiv);

        newTxDiv.appendChild(secondRowDiv);

        // THIRD ROW

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
        itemInputElement.id = "item" + x;
        itemInputElement.name = "item" + x;
        itemInputElement.setAttribute('class', "form-control");
        itemInputElement.maxLength = 75;
        itemInputElement.onkeydown = function () {
            limitTextField(itemInputElement, 75);
        };
        itemInputElement.onkeyup = function () {
            limitTextField(itemInputElement, 75);
        };

        itemInputElement.value = hashMap[columns[4]];
        itemInputElement.name = "transactionCommands[" + x + "].item";

        itemDiv.appendChild(itemInputElement);

        var valueLabel = document.createElement('label');
        valueLabel.setAttribute('class', "col-sm-2 control-label txCol2");
        valueLabel.innerText = "Value:";

        var valueDiv = document.createElement('div');
        valueDiv.setAttribute('class', "col-sm-4");

        var valueInputElement = document.createElement("input");
        valueInputElement.type = "text";
        valueInputElement.id = "value" + x;
        valueInputElement.name = "value" + x;
        valueInputElement.setAttribute('class', "form-control");

        valueInputElement.maxLength = 25;

        valueInputElement.onkeydown = function () {
            validateNumericInput(event, valueInputElement, 25);
        };
        valueInputElement.onkeyup = function () {
            validateNumericInput(event, valueInputElement, 25);
        };

        valueInputElement.value = hashMap[columns[5]];
        valueInputElement.name = "transactionCommands[" + x + "].value";

        valueDiv.appendChild(valueInputElement);

        thirdRowDiv.appendChild(itemLabel);
        thirdRowDiv.appendChild(itemDiv);

        thirdRowDiv.appendChild(valueLabel);
        thirdRowDiv.appendChild(valueDiv);

        newTxDiv.appendChild(thirdRowDiv);

        //FOURTH ROW

        var fourthRowDiv = document.createElement('div');
        fourthRowDiv.setAttribute('class', "form-group");
        fourthRowDiv.setAttribute('style', "margin-bottom: 7px");

        var budgeLabel = document.createElement('label');
        budgeLabel.setAttribute('class', "col-sm-2 control-label txCol1");
        budgeLabel.innerText = "Budget:";

        var budgetDiv = document.createElement('div');
        budgetDiv.setAttribute('class', "col-sm-4");

        var budgetElement = document.createElement("select");
        budgetElement.id = "budget" + x;
        budgetElement.name = "budget" + x;
        budgetElement.setAttribute('class', "form-control");

        // budgetElement.innerHTML = "";

        for (var i = 0; i < allBudgetNames.length; i++) {
            var option = document.createElement("option");
            option.value = allBudgetNames[i];
            option.text = allBudgetNames[i];
            budgetElement.appendChild(option);
        }

        budgetElement.value = hashMap[columns[7]];
        budgetElement.name = "transactionCommands[" + x + "].budget";

        budgetDiv.appendChild(budgetElement);

        var deleteLabel = document.createElement('label');
        deleteLabel.setAttribute('class', "col-sm-2 control-label");
        deleteLabel.innerText = "Delete?";

        var deleteDiv = document.createElement('div');
        deleteDiv.setAttribute('class', "col-sm-2");
//        deleteDiv.setAttribute('class', "checkbox");
//        deleteDiv.setAttribute('class', "checkbox-danger");
//        deleteDiv.setAttribute('class', "checkbox checkbox-danger");

//        var checkBoxLabel = document.createElement('label');
//        checkBoxLabel.setAttribute('class', "col-sm-2 control-label");

        var deleteElement = document.createElement("input");
        deleteElement.type = "checkbox";
        deleteElement.id = "delete" + x;
        deleteElement.name = "delete" + x;
        deleteElement.setAttribute('style', "width: 25px; height: 25px;");
        deleteElement.name = "transactionCommands[" + x + "].delete";
        if (String(hashMap[columns[8]]) == 'true')
            deleteElement.checked = true;
//            col.style = "display:none;";

//        deleteElement.setAttribute('style', "height: 30px");
//    <label class="btn btn-warning">
//                <input type="checkbox" autocomplete="off">
//                <span class="glyphicon glyphicon-ok"></span>
//                </label>

//        var spanElement = document.createElement("span");
//        spanElement.setAttribute('class', "checkbox-placeholder");
//        checkBoxLabel.appendChild(deleteElement);
//        checkBoxLabel.appendChild(spanElement);


        deleteDiv.appendChild(deleteElement);

        fourthRowDiv.appendChild(budgeLabel);
        fourthRowDiv.appendChild(budgetDiv);

        fourthRowDiv.appendChild(deleteLabel);
        fourthRowDiv.appendChild(deleteDiv);

        newTxDiv.appendChild(fourthRowDiv);

        var element = document.createElement("input");
        element.type = "hidden";
        element.id = "id" + x;
        element.name = "id" + x;
        element.size = 30;
        element.value = hashMap[columns[6]];
//            col.style = "display:none;";
        element.name = "transactionCommands[" + x + "].id";

        newTxDiv.appendChild(element);

        selector.appendChild(newTxDiv);


//            $(newId).datepicker({dateFormat: 'dd/mm/yy'});
    }

    //        function getTextInputElement(idNamePrefix, rowIndex, value, inputcharlimit, style) {
    //
    //            var col = document.createElement('td'); // create second column node
    //
    //            var element = document.createElement("input");
    //            element.type = "text";
    //            element.id = idNamePrefix + rowIndex;
    //            element.name = idNamePrefix + rowIndex;
    //            element.size = inputcharlimit;
    //            element.value = value;
    //            element.style = style;
    //
    //            col.appendChild(element);
    //
    //            return col;
    //        }


    function updateSubCategory(obj, index) {

        var selectedValue = obj.value;

//            alert(jsonStr);
//            console.log("index = " + index);

        var subCategoryElementId = "subcategory";

        if (index >= 1) {

            subCategoryElementId = "subcategory" + index;
        }

//            console.log(subCategoryElementId);
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

    $(document).ready(function () {

        $(document).on("click", "#tx-view-button", function () {  // When HTML DOM "click" event is invoked on element with ID "somebutton", execute the following function...

            var elementFrom = document.getElementById("datepickerFrom");
            var elementTo = document.getElementById("datepickerTo");

            var selectCategoryElement = document.getElementById("selectcategory");
            var selectedCategory = selectCategoryElement.options[selectCategoryElement.selectedIndex].value;

//            alert(getDate(element));

            var fromDate = getFormattedDate(getDate(elementFrom), format);
            var toDate = getFormattedDate(getDate(elementTo), format);

            var view = "view";

            var element = document.getElementById("txlist");

            element.innerHTML = "";
            element.border = 0;
//
//            alert(selectedDateFormatted);
            $('.loader').show();
            $('#button').prop('disabled', true);
            $('#save').prop('disabled', true);

            $.get("/transaction/retrieve", {
                "dateFrom": fromDate,
                "dateTo": toDate,
                "selectedCategory": selectedCategory,
                "action": view
//                "date": selectedDateFormatted
//                "date": getFormatteDate(selectedDate)
            }, function (json_data) {    // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response JSON...

                $('.loader').hide();

                console.log(json_data);

                $('#button').prop('disabled', false);
                // $('#save').prop('disabled', false);

                var element = document.getElementById("txlist");

//                    element.innerHTML = "";

                var array = JSON.parse(json_data);

                document.getElementById("totalRows").value = array.length;

                if (array.length > 0) {

                    $('#save').prop('disabled', false);

//                    element.border = 2;

//                    var header = ["Date", "Type", "Category", "Sub-Category", "Item", "Value", "Budget", "Id", "Delete?"];

//                    var row = document.createElement('tr'); // create row node
//
//                    for (var i = 0; i < header.length; i++) {
//
//                        var col = document.createElement('th'); // create column node
////                    if (header[i] == "Id") {
////                        col.style = "display:none;";
////                    }
//                        col.appendChild(document.createTextNode(header[i]));
//                        row.appendChild(col);
//                    }
//
//                    element.appendChild(row);

                    buildHtmlTable(element, array);

                    $('td:nth-child(8),th:nth-child(8)').hide();
                } else {

                    $('#save').prop('disabled', true);
                }
//                $('#outgoings').text(responseText);

//                var $ul = $("<ul>").appendTo($("#outgoings")); // Create HTML <ul> element and append it to HTML DOM element with ID "somediv".
//                $.each(responseJson, function(index, item) { // Iterate over the JSON array.
//                    $("<li>").text(item).appendTo($ul);      // Create HTML <li> element, set its text content with currently iterated item and append it to the <ul>.
//                });
            });
        });

        var viewButton = document.getElementById("tx-view-button");
        viewButton.click(); // this will trigger the click event
    });
</script>


<script type="text/javascript">

    $('.loader').hide();

    var defaultStartDate;
    var defaultEndDate;

    var timeRange = "${defaultTimeRange}";

    switch (timeRange) {

        case "weekly":

            defaultStartDate = getFormattedDate(moment().startOf('isoweek').toDate(), format);
            defaultEndDate = getFormattedDate(moment().endOf('isoweek').toDate(), format);
            break;

        case "monthly":

            defaultStartDate = getFirstDayOfMonth();
            defaultEndDate = getLastDayOfMonth();
            break;

        case "wtd":

            defaultStartDate = getFormattedDate(moment().startOf('isoweek').toDate(), format);
            defaultEndDate = getTodayDate(format);
            break;

        case "mtd":

            defaultStartDate = getFirstDayOfMonth();
            defaultEndDate = getTodayDate(format);
            break;

        default:
            defaultStartDate = getTodayDate(format);
            defaultEndDate = getTodayDate(format);
    }


    document.getElementById("datepickerFrom").value = defaultStartDate;
    // document.getElementById("datepickerFrom").value = getTodayDate(format);
    document.getElementById("datepickerTo").value = defaultEndDate;


    //    var viewButton = document.getElementById("button");
    //    viewButton.click(); // this will trigger the click event
    $('td:nth-child(8),th:nth-child(8)').hide();

</script>
<jsp:include page="footer.jsp"/>