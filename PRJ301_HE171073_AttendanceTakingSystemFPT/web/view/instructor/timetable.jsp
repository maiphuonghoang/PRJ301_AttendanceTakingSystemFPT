<%-- 
    Document   : timetable
    Created on : Mar 10, 2023, 2:54:13 PM
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

                    <form action="timetable" method="post" id="searchForm">      
                        <center>
                            Lecturer: ${requestScope.lecturerId}
                        </center>
                        <table border="1">
                            <tr>
                                <th rowspan="2"><input type="date" name="date"  value="${selectdate}" onchange="submitForm();" ></th>
                                <th>Mon</th>
                                <th>Tue</th>
                                <th>Wed</th>
                                <th>Thu</th>
                                <th>Fri</th>
                                <th>Sat</th>
                                <th>Sun</th>

                            </tr>
                            <tr>
                                <c:forEach items="${sameWeekDays}" var="d">
                                    <td>
                                        ${d} 
                                    </td>

                                </c:forEach>
                            </tr>

                            <c:forEach begin="1" end="6" step="1" var="n">
                                <tr>
                                    <td>Slot ${n}</td>
                                    <c:forEach items="${requestScope.sameWeekDays}" var="d">
                                        <td>
                                            <c:forEach items="${requestScope.sessions}" var="s">
                                                <c:if test="${s.date eq d and s.slotId.slotNumber eq n}">
                                                    <div class="${s.date eq d and s.slotId.slotNumber eq n ?"hasSlot":""}">
                                                        ${s.groupId.groupName}-${s.groupId.courseId.courseId}<br/>
                                                        at ${s.roomId.roomId}<br/>
                                                        <c:if test="${s.sessionStatus}">
                                                            (<font color=Green>Attended</font>)<br/>
                                                            <c:if test="${today eq s.date}">
                                                                <a href="updateattend?sessionId=${s.sessionId}">(<font color=blue>Update attend</font>)</a>
                                                                </c:if>
                                                                <c:if test="${!(today eq s.date)}">
                                                                <a href="viewattend?sessionId=${s.sessionId}">(<font color=orange>View attend</font>)</a>

                                                            </c:if>                                                                
                                                        </c:if>
                                                        <c:if test="${!s.sessionStatus}">
                                                            (<font color=red>Not yet</font>)<br/>
                                                            <c:if test="${today eq s.date}">
                                                                <a href="takeattend?sessionId=${s.sessionId}">(<font color=blue>Take attend</font>)</a>
                                                                </c:if>
                                                            </c:if>
                                                        <br/> <fmt:formatDate value="${s.slotId.startTime}" pattern="HH:mm" /> - <fmt:formatDate value="${s.slotId.endTime}" pattern="HH:mm" />

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
