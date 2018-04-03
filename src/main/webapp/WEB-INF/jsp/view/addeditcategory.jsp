<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Kingsley Kumar
  Date: 23/08/2016
  Time: 23:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="categoryname" type="String"--%>
<%--@elvariable id="subcategories" type="java.util.List"--%>
<%--@elvariable id="description" type="String"--%>
<%--@elvariable id="actiontype" type="String"--%>
<jsp:include page="header.jsp"/>
<jsp:include page="navbar.jsp"/>

<div class="wrapper">
    <jsp:include page="sidebar.jsp"/>

    <div id="content">
        <h3>${title}</h3>
        <%--<br>--%>
        <c:choose>
            <c:when test="${actiontype == 'Add'}">
                <c:set var="caturl" value="/category/add"/>
                <c:set var="actionName" value="Save"/>
            </c:when>
            <c:otherwise>
                <c:set var="caturl" value="/category/${categoryName}/edit"/>
                <c:set var="actionName" value="Update"/>
            </c:otherwise>
        </c:choose>
        <%--<p>${caturl}</p>--%>
        <form:form method="POST" class="form-horizontal" name="form" modelAttribute="categorycommand" action="${caturl}"
                   onsubmit="return validateForm()">

            <c:set var="categorycharlimit" value="${50}"></c:set>
            <c:set var="descchartlimit" value="${200}"></c:set>
            <c:set var="subcategorychartlimit" value="${5000}"></c:set>

            <div class="container-fluid">
                <div class="form-group">
                    <div class="row col-md-10 col-lg-7">

                        <div id="error">
                            <strong>
                                <form:errors path="*" id="error"/>
                            </strong>
                        </div>
                    </div>
                </div>
            </div>

            <div class="container-fluid">

                <div class="form-group">
                    <form:label class="col-md-3 col-lg-2 control-label" style="width: 160px"
                                path="category">Category Name:<span class="mandatory">*</span></form:label>
                    <div class="col-md-7 col-lg-5">
                        <%--<c:choose>--%>
                            <%--<c:when test="${actiontype == 'Update'}">--%>
                                <form:input type="text" name="category" value="${category}"
                                            onKeyDown="limitTextField(this.form.category,${categorycharlimit});"
                                            onKeyUp="limitTextField(this.form.category,${categorycharlimit});"
                                            maxlength="${categorycharlimit}" class="form-control" path="category" readonly="${actiontype == 'Update'}"/>
                            <%--</c:when>--%>
                            <%--<c:otherwise>--%>
                                <%--<form:input type="text" name="category" value="" path="category"--%>
                                            <%--onKeyDown="limitTextField(this.form.category,${categorycharlimit});"--%>
                                            <%--onKeyUp="limitTextField(this.form.category,${categorycharlimit});"--%>
                                            <%--maxlength="${categorycharlimit}" placeholder="(Example: Food)"--%>
                                            <%--class="form-control"/>--%>
                            <%--</c:otherwise>--%>
                        <%--</c:choose>--%>
                    </div>
                </div>

                <div class="form-group">
                    <form:label class="col-md-3 col-lg-2 control-label" style="width: 160px"
                                path="subcategories">Sub-Categories:</form:label>
                    <div class="col-md-7 col-lg-5">

                        <form:textarea rows="6" cols="50" name="subcategories" id="subcategorylist" path="subcategories"
                                       onKeyDown="limitTextField(this.form.subcategories,${subcategorychartlimit});"
                                       onKeyUp="limitTextField(this.form.subcategories,${subcategorychartlimit});"
                                       class="form-control"
                                       placeholder="(Example: Groceries, Lunch, Dinner)"/>
                    </div>
                </div>

                <div class="form-group">
                    <form:label class="col-md-3 col-lg-2 control-label" style="width: 160px" path="description">Description:</form:label>
                    <div class="col-md-7 col-lg-5">
                        <form:textarea rows="3" cols="50" name="description" path="description"
                                       onKeyDown="limitTextArea(this.form.description,this.form.countdown,${descchartlimit});"
                                       onKeyUp="limitTextArea(this.form.description,this.form.countdown,${descchartlimit});"
                                       class="form-control"/>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-3 col-lg-2 control-label" style="width: 160px"></label>
                    <div class="col-md-7 col-lg-5">
                        <input type="submit" class="btn btn-info" value="${actionName}" onclick="selectAll()"/>
                    </div>
                </div>
            </div>

            <%--<input type="submit" class="btn btn-info" value="${actionName}" onclick="selectAll()"/>--%>
        </form:form>
    </div>
</div>

<script language="javascript" type="text/javascript">

    function isEmpty(str) {
        return (!str || 0 === str.length || !str.trim());
    }

    function validateForm() {
        var x = document.forms["form"]["category"].value;
        if (isEmpty(x)) {
            bootbox.alert("Category Name is required.");
            return false;
        }

        var x = document.forms["form"]["subcategories"].value;
        var partsOfStr = x.split(',');

        for (var i = 0; i < partsOfStr.length; i++) {

            if (partsOfStr[i].length > 50) {

                bootbox.alert("Individual Sub-Category name can not exceed 50 characters in length.");

                return false;
            }
        }

    }

    function removeFromList() {

        var list = document.getElementById("subcategorylist");

        // Remember selected items.
        var is_selected = [];
        for (var i = 0; i < list.options.length; i++) {

            is_selected[i] = list.options[i].selected;
        }

        // Remove selected items.
        var i = list.options.length;
//            alert(i)
        while (i--) {
            if (is_selected[i]) {
                list.remove(i);
            }
        }

//            list.remove(list.selectedIndex);
    }


    function addToList() {
        //First things first, we need our text:
//        var text = document.getElementById("subcategory").value; //.value gets input values
//
//        if (text.trim() == '') {
//            return;
//        }

        bootbox.prompt({
            title: "Please enter sub-category name:",
//            inputType: 'text',
            callback: function (text) {

                console.log("text = " + text);

                if (text == null) {

                    return;
                }

                if (text.length > 30) {

                    bootbox.alert("Sub-category name can't exceed 30 characters in length.");
                    return;
                }

                console.log("test1");

                var list = document.getElementById("subcategorylist");

                var itemexist = false;

                for (var i = 0; i < list.options.length; ++i) {

                    if (text.trim() == list.options[i].value) {

//                    alert("item exists!");

                        itemexist = true;

                        break;
                    }
                }

                console.log("test2");


                if (!itemexist) {

                    //Now construct a quick list element
                    var li = document.createElement("option");
                    li.appendChild(document.createTextNode(text));

                    //Now use appendChild and add it to the list!
                    document.getElementById("subcategorylist").appendChild(li);
                }

                console.log("test3");

            }
        });
    }

    function selectAll() {
        var selectBox = document.getElementById("subcategorylist");

        for (var i = 0; i < selectBox.options.length; i++) {
            selectBox.options[i].selected = true;
        }
    }
</script>

<jsp:include page="footer.jsp"/>
