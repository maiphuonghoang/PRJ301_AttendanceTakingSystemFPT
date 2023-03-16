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
                    ${requestScope.studentId}
                    <form action="timetable" method="post" id="searchForm">      
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
                                            <c:forEach items="${requestScope.attends}" var="a">
                                                <c:if test="${a.sessionId.date eq d and a.sessionId.slotId.slotNumber eq n}">
                                                    <div class="${a.sessionId.date eq d and a.sessionId.slotId.slotNumber eq n ?"hasSlot":""}">
                                                        ${a.sessionId.groupId.groupName}-${a.sessionId.groupId.courseId.courseId}<br/>
                                                        at ${a.sessionId.roomId.roomId}<br/>
                                                        <c:if test="${a.status and a.sessionId.sessionStatus}">
                                                            (<font color=Green>Attended</font>)
                                                        </c:if>
                                                        <c:if test="${!a.status and a.sessionId.sessionStatus}">
                                                            (<font color=red>Absent</font>)
                                                        </c:if>
                                                        <c:if test="${!a.status and  !a.sessionId.sessionStatus}">
                                                            (<font color=blue>Not yet</font>)
                                                            
                                                        </c:if>
                                                        <br/> <fmt:formatDate value="${a.sessionId.slotId.startTime}" pattern="HH:mm" /> - <fmt:formatDate value="${a.sessionId.slotId.endTime}" pattern="HH:mm" />
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
