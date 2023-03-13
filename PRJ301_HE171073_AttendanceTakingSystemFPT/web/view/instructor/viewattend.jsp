<%-- 
    Document   : updateattend
    Created on : Mar 14, 2023, 12:54:17 AM
    Author     : ADMIN
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
                    <form action="updateattend" method="POST">
                        <input name ="sessionId" value="${sessionId}" hidden/>
                        <table>
                            <thead>
                                <tr>
                                    <th rowspan="1">
                                        No
                                    </th>
                                    <th rowspan="1">
                                        Group
                                    </th>
                                    <th rowspan="1">
                                        Code
                                    </th>
                                    <th rowspan="1">
                                        Name
                                    </th>
                                    <th rowspan="1">
                                        Image
                                    </th>
                                    <th rowspan="1">
                                        Status
                                    </th>
                                    <th rowspan="1">
                                        Comment
                                    </th>
                                    <th rowspan="1">
                                        Taker
                                    </th>
                                    <th rowspan="1">
                                        Record time
                                    </th>

                                </tr>
                            </thead>
                            <div>
                                <tbody>
                                    <c:forEach items="${requestScope.attends}" var="a" varStatus="index">
                                        <tr>
                                            <td> ${index.index + 1}</td>
                                            <td>
                                                <font color="#0000ee">${a.sessionId.groupId.groupName}<font>
                                            </td>
                                            <td>
                                                <font color="#0000ee">${a.studentId.studentId}</font>
                                            </td>
                                            <td style="text-align: start; padding-left: 15px ">
                                                ${a.studentId.studentName}
                                            </td>
                                            <td>
                                                <div class="student-image">
                                                    <img src="${a.studentId.studentImage}" alt="Image" />
                                                </div>
                                            </td>
                                            <td>
                                                <c:if test="${!a.status}">
                                                    <font color="red">Absent </font>
                                                </c:if>
                                                <c:if test="${a.status}">
                                                    <font color="green">Present </font>
                                                </c:if>
                                            </td>
                                            <td>${a.comment}</td>
                                            <td>
                                                <font color="#0000ee">${a.sessionId.lecturerId.instructorId}</font>
                                            <td>


                                                    <c:set var="dateStr" value="${a.recordTime}" />

                                                    <fmt:parseDate var="date" value="${dateStr}" pattern="yyyy-MM-dd HH:mm:ss" />

                                                    <fmt:formatDate var="formattedDate" value="${date}" pattern="yyyy-MM-dd hh:mm a" timeZone="GMT-5" />

                                                    ${formattedDate}
                                                </td>

                                            </tr>
                                    </c:forEach>


                                </tbody>
                            </div>
                        </table>
                    </form>


                </div>
            </div>
        </div>

    </body>
</html>
