<%-- 
    Document   : takeattend
    Created on : Mar 13, 2023, 3:07:21 AM
    Author     : ADMIN
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="../css/style.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <form action="takeattend" method="POST">
            <table>
                <tr>
                    <th scope="col">&nbsp;</th>
                    <th scope="col">Group</th>
                    <th scope="col">Roll Number</th>
                    <th scope="col">Full Name</th>
                    <th scope="col">ABSENT</th>
                    <th scope="col">PRESENT</th>
                    <th scope="col">COMMENT</th>
                    <th scope="col"> IMAGE</th>
                    <th scope="col">&nbsp;</th>
                </tr>

                <c:forEach items="${requestScope.attends}" var="a">
                    <tr>

                        <td>
                        </td>
                        <td>
                            <span id="" style="color:#0000EE;">${a.sessionId.groupId.groupName}</span>
                        </td>
                        <td>
                            <span id="" style="color:#0000EE;">${a.studentId.studentId}</span>
                        </td>
                        <td>
                            <span id="">${a.studentId.studentName}</span>
                        </td>
                        <td>
                            <input id="" type="radio" name="${a.studentId.studentId}" value="absent" ${a.status ? "checked" : ""} /><label for="">Absent</label>
                        </td>
                        <td>
                            <input id="" type="radio" name="${a.studentId.studentId}" value="present" ${!a.status ? "checked" : ""} /><label
                                for="">Present</label>
                        </td>

                        <td>
                            <input type="text" />
                        </td>
                        <td>
                            <div>
                                <img src="" alt="Image" />
                            </div>
                        </td>
                        <td>

                        </td>
                    </tr>

                </c:forEach>



            </table>
            <center>
                <input type="submit" name="" value="Save" class="btn--save" />
            </center>
        </form>
    </body>
</html>
