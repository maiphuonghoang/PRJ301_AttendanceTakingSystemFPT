<%-- 
    Document   : login
    Created on : Mar 5, 2023, 3:42:47 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1 style="color: red">${err}</h1>
        <form action="login" method="POST">
            Username:<input type="text" name="username" value="${username}" /> <br>
            Password: <input type="password" name="password" value="${password}" /><br>
            Remember your account <input type="checkbox" name="remember" value="true" /><br>
            <input type="submit" value="Login" />
        </form>
    </body>
</html>
