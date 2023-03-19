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
        <link href="../css/takeattend.css" rel="stylesheet" type="text/css"/>
        <style >
            .app__container{
                font-size: 0.75rem;
            }
            .row {
                display: flex;
            }
            .left{
                flex: 2;
            }
            .right{
                flex: 3;
            }
            .min-withTd{
                min-width: 130px;
            }
            .term{
                width: 90px;
            }
            td{
                padding: 15px 0;
            }
            ul{
                list-style: none;

            }
            li{
                padding: 15px 0;

            }
            a{
                text-decoration: none;
                font-weight: 600;
            }

        </style>
    </head>
    <body>
        <div class="app">
            <header class="header">
                <div class="grid wide">
                    <div class="header__navbar">

                        <div class="header__title">FPT University Academic Portal</div>
                        <ul class="header__list">
                            <li class="header__item">
                                <a class="header__link" href="timetable">Timetable</a>
                            </li>
                            <li class="header__item">
                                <a class="header__link" href="reportattend">Report attendance</a>
                            </li>
                        </ul>
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
                                                     <b style="color: blue;">FU HL</b>

                                                </div>
                                            </td>
                                            <td valign='center'>
                                                <div >
                                                    <b style="color: blue;">Spring2023</b>
                                                </div>
                                            </td>
                                            <td valign='top'>
                                                <div >
                                                    <ul>
                                                        <input type="hidden" name="groupId" value="${s.groupId.groupId}"/>

                                                        <c:forEach items="${sessions}" var="s">

                                                            <li>
                                                                <a href="reportattend?groupId=${s.groupId.groupId}&studentId="${studentId}"">
                                                                    ${s.groupId.courseId.courseName}(${s.groupId.courseId.courseId}) (${s.groupId.groupName})
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
                                    <td>
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
                </div>
            </div>

        </div>
    </div>


</body>
</html>
