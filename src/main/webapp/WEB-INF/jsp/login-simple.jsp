<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head></head>
<body>
<h1>Login</h1>
<form name='f' action="${contextPath}/login" method='POST'>

    <div class="form-group ${error != null ? 'has-error' : ''}">
        <span>${msg}</span>

        <span>${errorMsg}</span>
    </div>

    <table>
        <tr>
            <td>User1:</td>
            <td><input type='text' name='username' value=''></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input type='password' name='password' /></td>
        </tr>


        <tr>
            <td><input name="submit" type="submit" value="submit" /></td>
        </tr>
    </table>
</form>
</body>
</html>