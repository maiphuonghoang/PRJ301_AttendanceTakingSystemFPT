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
        <link href="css/login.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div class="wrapper">
            <!--<img src="./image/FPT2.jpg"/>-->
            <!--<img src="./image/fpt-mb.png"/>-->
                        <img src="./image/fpt3.png"/>


        </div>

        <div class="main">
            <form action="login" method="POST" class="form">
                <h2 class="heading">Login FAP</h3>

                    <div class="spacer"></div>

                    <div class="form-group">
                        <label for="username" class="form-label">Username</label>
                        <input value="${username}" id ="username" name="username" type="text" placeholder="VD: email@fpt.edu.vn" class="form-control">
                        <span class="form-message"></span>
                    </div>

                    <div class="form-group">
                        <label for="password" class="form-label">Password</label>
                        <input value="${password}" id="password" name="password" type="password"  class="form-control">
                        <span class="form-message"></span>
                    </div>
                    <h3 style="color: red">${err}</h3>

                    <h3>Remember your account <input type="checkbox" name="remember" value="true" /></h3><br>


                    <input class="form-submit" type="submit" value="Login" />
            </form>

        </div> 

    </body>
</html>
