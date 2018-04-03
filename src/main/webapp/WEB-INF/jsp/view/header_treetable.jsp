<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Kingsley Kumar
  Date: 03/11/2017
  Time: 22:29
  To change this template use File | Settings | File Templates.
--%>
<%--@elvariable id="title" type="java.util.String"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>${title}</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="icon" href="/images/logo.png" type="image/png">
    <link rel="shortcut icon" href="/images/logo.png" type="image/png">

    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.0/themes/base/jquery-ui.css">

    <!-- Bootstrap CSS CDN -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

    <!-- JQuery TreeTable CSS -->
    <link rel="stylesheet" type="text/css" href="/css/jquery.treetable.theme.default.css">
    <link rel="stylesheet" type="text/css" href="/css/jquery.treetable.css"/>

    <!-- Custom CSS -->
    <link rel="stylesheet" type="text/css" href="/css/main.css"/>
    <link rel="stylesheet" type="text/css" href="/css/jquery.mCustomScrollbar.css"/>

    <!-- jQuery CDN -->
    <script src="https://code.jquery.com/jquery-1.12.0.min.js"></script>

    <!-- Chart.js CDN -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.1/Chart.bundle.min.js"></script>
    <%--<script src="https://cdn.jsdelivr.net/npm/chartjs-plugin-piechart-outlabels"></script>--%>
    <%--<link rel="stylesheet" href="/resources/demos/style.css">--%>
    <%--<script src="https://code.jquery.com/jquery-1.12.4.js"></script>--%>
    <script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>

    <script src="/js/jquery.treetable.js"></script>
    <script src="/js/common.js"></script>
    <script src="/js/bootbox.min.js"></script>
    <script src="/js/Chart.PieceLabel.min.js"></script>
    <script src="/js/chartjs-plugin-datalabels.min.js"></script>

    <jsp:include page="timeoutchecker.jsp"/>

    <style>
        canvas {
            -moz-user-select: none;
            -webkit-user-select: none;
            -ms-user-select: none;
        }
    </style>
</head>
<body>
<div class="overlay"></div>
