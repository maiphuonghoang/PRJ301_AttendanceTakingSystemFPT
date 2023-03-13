<%-- 
    Document   : takeattend
    Created on : Mar 13, 2023, 3:07:21 AM
    Author     : maiphuonghoang
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="../css/base.css" rel="stylesheet" type="text/css"/>
        <link href="../css/takeattend.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>

        <div class="app">
            <header class="header">
                <div class="grid wide">
                    <div class="header__navbar">

                        <div class="header__title">FPT University Academic Portal</div>
                        <ul class="header__list">
                            <li class="header__item">
                                <a class="header__link" href="#">Timetable</a>
                            </li>
                            <li class="header__item">
                                <a class="header__link" href="">Take Attendance</a>
                            </li>
                            <li class="header__item">
                                <a class="header__link" href="">View Attendance</a>
                            </li>
                            <li class="header__item">
                                <a class="header__link" href="">Report</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </header>
            <div class="app__container">
                <div class="grid wide">
                    <form action="takeattend" method="POST">
                        <input name ="sessionId" value="${sessionId}" hidden/>
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

                            <c:forEach items="${requestScope.attends}" var="a" varStatus="index">
                                <tr>

                                    <td>
                                        ${index.index + 1}
                                    </td>
                                    <td>
                                        <span id="" style="color:#0000EE;">${a.sessionId.groupId.groupName}</span>
                                    </td>
                                    <td>

                                        <span id="" style="color:#0000EE;">${a.studentId.studentId}</span>
                                    </td>
                                    <td>
                                        <input name ="studentId${index.index}" value="${a.studentId.studentId}" hidden/>

                                        <span id="">${a.studentId.studentName}</span>
                                    </td>
                                    <td>
                                        <input id="" type="radio" name="status${index.index}" value="absent" ${a.status ? "checked" : ""} /><label for="">Absent</label>
                                    </td>
                                    <td>
                                        <input id="" type="radio" name="status${index.index}" value="present" ${!a.status ? "checked" : ""} /><label
                                            for="">Present</label>
                                    </td>

                                    <td>
                                        <input name="comment${index.index}" type="text" />
                                    </td>
                                    <td>
                                        <div class="student-image">
                                            <img src="${a.studentId.studentImage}" alt="Image" />
                                        </div>
                                    </td>
                                    <td>

                                    </td>
                                </tr>

                            </c:forEach>



                        </table>
                        <center>
                            <input type="submit" name="" value="Save" class="btn" />
                            <input name="number" value="${number}" hidden="" />
                        </center>
                    </form>


                </div>
            </div>
        </div>

    </body>
</html>
