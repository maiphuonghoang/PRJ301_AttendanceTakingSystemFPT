<%-- 
    Document   : timetable
    Created on : Mar 12, 2023, 1:41:52 AM
    Author     : maiphuonghoang
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en_US" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="../css/base.css" rel="stylesheet" type="text/css"/>
        <link href="../css/timetable.css" rel="stylesheet" type="text/css"/>
        <script>
            function submitForm()
            {
                document.getElementById("searchForm").submit();
            }
        </script>
        <style>
            .hasSlot{
                min-height: 110px;
            }
        </style>
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

                    <form action="timetable" method="post" id="searchForm">      
                        <table border="1">
                            <tr>
                                <th rowspan="2"><input type="date" name="date"  value="${selectdate}" onchange="submitForm();" ></th>
                                    <c:forEach items="${sameWeekDays}" var="d">
                                    <th>
                                        <fmt:formatDate value="${d}" pattern="EEEE" /> 
                                    </th>
                                </c:forEach>
                            </tr>
                            <tr>
                                <c:forEach items="${sameWeekDays}" var="d">
                                    <td>
                                        <fmt:formatDate value="${d}" pattern="dd/MM/yyyy" />
                                    </td>

                                </c:forEach>
                            </tr>
                            <c:forEach begin="1" end="6" step="1" var="n">
                                <tr>
                                    <td>Slot ${n}</td>
                                    <c:forEach items="${requestScope.sameWeekDays}" var="d">
                                        <td>                                         
                                            <c:forEach items="${requestScope.attends}" var="a">
                                                <c:if test="${a.sessionId.date eq d and a.sessionId.slotId.slotNumber eq n}">
                                                    <div class="${a.sessionId.date eq d and a.sessionId.slotId.slotNumber eq n ?"hasSlot":""}">
                                                        ${a.sessionId.groupId.groupName}-${a.sessionId.groupId.courseId.courseId}<br/>
                                                        at ${a.sessionId.roomId.roomId}<br/>
                                                        <c:if test="${a.status and a.sessionId.sessionStatus}">
                                                            <span class="label label-hover">Attended</span>
                                                        </c:if>
                                                        <c:if test="${!a.status and a.sessionId.sessionStatus}">
                                                            <span class="label label-danger">Absent</span>
                                                        </c:if>
                                                        <c:if test="${!a.status and  !a.sessionId.sessionStatus}">
                                                            (<font color=red>
                                                            Not yet
                                                            </font>)

                                                        </c:if>
                                                        <br/> 
                                                        <span class="label label-success"><fmt:formatDate value="${a.sessionId.slotId.startTime}" pattern="HH:mm" /> - <fmt:formatDate value="${a.sessionId.slotId.endTime}" pattern="HH:mm" /></span>

                                                    </div>
                                                </c:if>
                                            </c:forEach>
                                        </td>
                                    </c:forEach>
                                </tr>
                            </c:forEach>
                        </table>
                    </form>

                </div>
            </div>
        </div>
    </body>
</html>
