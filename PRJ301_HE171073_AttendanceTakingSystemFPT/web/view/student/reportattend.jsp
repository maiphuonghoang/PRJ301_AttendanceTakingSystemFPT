<%-- 
    Document   : reportattend
    Created on : Mar 17, 2023, 10:28:00 AM
    Author     : ADMIN
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en_US"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="../css/base.css" rel="stylesheet" type="text/css"/>
        <link href="../css/reportattendS.css" rel="stylesheet" type="text/css"/>
        <script src="https://kit.fontawesome.com/a54d2cbf95.js"></script>


    </head>
    <body>
        <div class="app">
            <header class="header">
                <div class="grid wide">
                    <div class="logo">
                        <img class="logo-img" src="../image/FPT_logo_2010.svg.png" alt="alt"/>
                        <h1 style="display: inline-block; margin-left: 4px; font-weight: 300">University Academic Portal</h1>                       
                    </div>

                    <div class="header__navbar">


                        <ul class="header__list">
                            <li class="header__item">
                                <a class="header__link panel" href="timetable">Timetable</a>
                            </li>
                            <li class="header__item">
                                <a class="header__link panel" href="reportattend">Report attendance</a>
                            </li>

                        </ul>
                        <div>
                            <a href="" class="label label-hover">${sessionScope.account.username}</a>&nbsp|
                            <a href="../logout" class="label label-danger">Logout</a>&nbsp|       
                            <span class="label label-hover">CAMPUS: FPTU-Hòa Lạc</span>
                        </div>

                    </div>
                </div>
            </header>
            <div class="app__container">
                <div class="grid wide">
                    <form action="reportattend" action="POST">

                        <h2 style="padding: 20px;">Report attendance for ${studentName} (${studentId})</h2>

                        <div class="row">
                            <div class="left">
                                <table >
                                    <thead>
                                        <tr>
                                            <th scope='col'>Campus</th>
                                            <th scope='col' class="term">Term</th>
                                            <th scope='col'>Course</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td valign='center'>
                                                <div >
                                                    <b>FU HL</b>

                                                </div>
                                            </td>
                                            <td valign='center'>
                                                <div >
                                                    <b>Spring2023</b>
                                                </div>
                                            </td>
                                            <td valign='top'>
                                                <div >
                                                    <ul>
                                                        <input type="hidden" name="groupId" value="${s.groupId.groupId}"/>

                                                        <c:forEach items="${sessions}" var="s">
                                                            <li style="text-align: start; padding-left: 15px"  class="student-course">
                                                                <a class="${groupId==s.groupId.groupId?"active":""}" href="reportattend?groupId=${s.groupId.groupId}&studentId="${studentId}"">
                                                                    ${s.groupId.courseId.courseName} (${s.groupId.courseId.courseId}) (${s.groupId.groupName})
                                                                </a>
                                                            </li>
                                                        </c:forEach>
                                                    </ul>
                                                </div>
                                            </td>
                                    </tbody>
                                </table>

                            </div>
                    </form>
                    <c:if test="${groupId>0}">
                        <table class='table table-bordered right' >
                            <tr>
                            <thead>
                            <th>No.</th>
                            <th class="min-withTd">Date</th>
                            <th class="min-withTd">Time Slot</th>
                            <th>Room</th>
                            <th>Lecturer</th>
                            <th>Group</th>
                            <th>Attedance status</th>
                            <th>Lecturer's comment</th>
                            </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${attends}" begin="0" end="${attends.size()}" var="a" step="1" varStatus="index">

                                    <tr>
                                        <td>${index.index + 1}</td>
                                        <td >
                                            <span class='label label-primary'>
                                                <fmt:formatDate value="${a.sessionId.date}" pattern="EEEE" /> 
                                                <fmt:formatDate value="${a.sessionId.date}" pattern="dd/MM/yyyy" />
                                            </span>
                                        </td>
                                        <td>
                                            <span class='label label-danger'>${a.sessionId.slotId.slotNumber} _
                                                <fmt:formatDate value="${a.sessionId.slotId.startTime}" pattern="HH:mm" />-<fmt:formatDate value="${a.sessionId.slotId.endTime}" pattern="HH:mm" />

                                            </span>
                                        </td>
                                        <td>${a.sessionId.roomId.roomId}</td>
                                        <td>${a.sessionId.lecturerId.instructorId}</td>
                                        <td>${a.sessionId.groupId.groupName}</td>
                                        <td>
                                            <c:if test="${a.status && a.sessionId.sessionStatus}">
                                                <font color=green>Present</font>
                                            </c:if>
                                            <c:if test="${!a.status && a.sessionId.sessionStatus}">
                                                <font color=red>Absent</font>
                                            </c:if>
                                            <c:if test="${!a.status && !a.sessionId.sessionStatus}">
                                                Future
                                            </c:if>
                                        </td>
                                        <td></td>           
                                    </tr>

                                </c:forEach>
                            </tbody>
                            <tfoot>
                                <tr>
                                    <td colspan='8'><b>Absent</b>: ${percentage}% absent so far (${numAbsent} absent on ${attends.size()} total).</td>
                                </tr>
                            </tfoot>
                        </table>
                    </c:if>
                </div>
            </div>

        </div>
    </div>
    <a href="#" class="to-top">
        <i class="fas fa-chevron-up"></i>
    </a>

    <script src="../js/returntop.js" ></script>


</body>
</html>
