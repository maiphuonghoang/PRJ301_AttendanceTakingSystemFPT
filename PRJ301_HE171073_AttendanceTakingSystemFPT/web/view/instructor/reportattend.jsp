<%-- 
    Document   : reportattend
    Created on : Mar 14, 2023, 1:00:44 PM
    Author     : maiphuonghoang
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        </style>

    </head>
    <body>

        <div class="app">
            <header class="header">
                <div class="grid">
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
                                <a class="header__link panel" href="reportattend">Report group attendance</a>
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
                <div class="">
                    <form action="reportattend" action="POST">
                        <center>
                            Lecture: ${lecturerId}                       
                            <select name="groupId">
                                <option value="" disabled selected>Choose a course</option>
                                <c:forEach items="${groups}" var="g">
                                    <option value="${g.groupId}" ${g.groupId eq groupId?"selected":""} >
                                        ${g.courseId.courseId} - ${g.groupName}
                                    </option>

                                </c:forEach>
                            </select>
                            <input class="" type="submit" value="View">

                        </center>                                       
                    </form>
                    <c:if test="${attends!=null && attends.size()>0}">
                        <table>
                            <thead>
                                <tr>
                                    <th>RollNumber</th>
                                    <th colspan='5'>Student Name</th>
                                    <th>ABSENT (%) SO FAR</th>
                                        <c:forEach items="${sessions}" var="s" varStatus="index">

                                        <th><fmt:formatDate value="${s.date}" pattern="dd-MM" /><br /><br />${index.index + 1}</th>
                                        </c:forEach>

                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${percents}" var="entry">
                                    <tr>
                                        <td>${entry.key.studentId}</td>
                                        <td colspan='5'>${entry.key.studentName}</td>
                                        <td>${entry.value}%</td>
                                        <c:forEach items="${attends}" var="a">
                                            <c:if test="${entry.key.studentId.equals(a.studentId.studentId)}">
                                                <td>
                                                    <c:if test="${a.status && a.sessionId.sessionStatus}">
                                                        <font color=green>P</font>
                                                    </c:if>
                                                    <c:if test="${!a.status && a.sessionId.sessionStatus}">
                                                        <font color=red>A</font>
                                                    </c:if>
                                                    <c:if test="${!a.status && !a.sessionId.sessionStatus}">
                                                        -
                                                    </c:if>
                                                </td>
                                            </c:if>

                                        </c:forEach>

                                    </tr>
                                </c:forEach>

                            </tbody>
                        </table>
                    </c:if>
                </div>
                <div style="margin-left: 80px;">
                    <br>
                    <b>Note</b>:</p>
                    <ul>
                        <li>
                            <font color="green">P</font> = Present
                        </li>
                        <li>- = No information given for the whole group or Future activity</li>
                        <li>
                            <font color="red">A</font> = Absent
                        </li>
                    </ul>
                </div>
            </div>
        </div>


    </body>
</html>