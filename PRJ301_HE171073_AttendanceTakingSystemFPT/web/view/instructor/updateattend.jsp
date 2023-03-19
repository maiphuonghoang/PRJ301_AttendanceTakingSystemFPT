<%-- 
    Document   : updateattend
    Created on : Mar 14, 2023, 9:46:47 AM
    Author     : ADMIN
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
                <div class="grid wide">
                    <form action="updateattend" method="POST">
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
                                        <input name ="studentId${index.index}" value="${a.studentId.studentId}" hidden/>
                                        <span id="" style="color:#0000EE;">${a.studentId.studentId}</span>
                                    </td>
                                    <td>


                                        <span id="">${a.studentId.studentName}</span>
                                    </td>
                                    <td>
                                        <input id="${a.studentId.studentId}" type="radio" name="status${index.index}" value="absent" ${a.status ? "checked" : ""} />
                                        <label for="${a.studentId.studentId}">Absent</label>
                                    </td>
                                    <td>
                                        <input id="${a.studentId.studentName}" type="radio" name="status${index.index}" value="present" ${!a.status ? "checked" : ""} /><label
                                            for="${a.studentId.studentName}">Present</label>
                                    </td>

                                    <td>
                                        <input name="comment${index.index}" value="${a.comment}" type="text" />
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
                        </center>
                    </form>


                </div>
            </div>
        </div>

    </body>
</html>
