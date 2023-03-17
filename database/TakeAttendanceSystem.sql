--CREATE DATABASE PRJ301_TakeAttendanceSystem
USE PRJ301_TakeAttendanceSystem

CREATE TABLE Account (
    username VARCHAR(150) NOT NULL PRIMARY KEY,
    [password] VARCHAR(150) NOT NULL,
);
CREATE TABLE [Role](
	roleId int NOT NULL,
	roleName varchar(150),
	CONSTRAINT PK_Role PRIMARY KEY (roleId)
)
CREATE TABLE Account_Role(
	username varchar(150) NOT NULL,
	roleId int NOT NULL,
	CONSTRAINT PK_Account_Role PRIMARY KEY (username, roleId)
)
ALTER TABLE Account_Role ADD CONSTRAINT FK_Account_Role_Account FOREIGN KEY(username)
REFERENCES Account (username)
ALTER TABLE Account_Role ADD CONSTRAINT FK_Account_Role_Role FOREIGN KEY(roleId)
REFERENCES [Role] (roleId)

CREATE TABLE Feature(
	featureId int NOT NULL, 
	featureName varchar(150),
	[url] varchar(150),
	CONSTRAINT PK_Feature PRIMARY KEY (featureId)
)
CREATE TABLE Role_Feature 
(
	roleId int NOT NULL,
	featureId int NOT NULL,
	CONSTRAINT PK_Role_Feature PRIMARY KEY (roleId, featureId)
)
ALTER TABLE Role_Feature ADD CONSTRAINT FK_Role_Feature_Role FOREIGN KEY(roleId)
REFERENCES [Role](roleId)
ALTER TABLE Role_Feature ADD CONSTRAINT FK_Role_Feature_Feature FOREIGN KEY(featureId)
REFERENCES Feature(featureId)

CREATE TABLE Course
(
	courseId varchar(10) NOT NULL,
	courseName nvarchar(100),
	CONSTRAINT PK_Course PRIMARY KEY (courseId)
 )

 CREATE TABLE Instructor
 (
	instructorId varchar(20) NOT NULL,
	instructorName nvarchar(100),
	instructorImage varchar(max),
	CONSTRAINT PK_Instructor PRIMARY KEY (instructorId),

	accountId varchar(150) NOT NULL UNIQUE,
    CONSTRAINT FK_Instructor_Account FOREIGN KEY (accountId) REFERENCES Account(username)
)

CREATE TABLE Student
(
	studentId varchar(8) NOT NULL,
	studentName nvarchar(100),
	studentImage varchar(max),
	CONSTRAINT PK_Student PRIMARY KEY (studentId),

	accountId varchar(150) NOT NULL UNIQUE,
    CONSTRAINT FK_Student_Account FOREIGN KEY (accountId) REFERENCES Account(username)
 )

 CREATE TABLE TimeSlot
 (
	slotId int NOT NULL,
	slotNumber int,
	startTime time(0),
	endTime time(0),
	CONSTRAINT PK_TimeSlot PRIMARY KEY (slotId)
)

CREATE TABLE Room
(
	roomId varchar(10) NOT NULL,
	CONSTRAINT PK_Room PRIMARY KEY (roomId)
)

CREATE TABLE [Group]
(
	groupId int NOT NULL,
	groupName varchar(20) NULL,
	courseId varchar(10) NULL,
	instructorId varchar(20) NULL,
	CONSTRAINT PK_Group PRIMARY KEY (groupId)
)
ALTER TABLE [Group] ADD CONSTRAINT FK_Group_Course FOREIGN KEY(courseId)
REFERENCES Course (courseId)
ALTER TABLE [Group] ADD CONSTRAINT FK_Group_Instructor FOREIGN KEY(instructorId)
REFERENCES Instructor (instructorId)

 CREATE TABLE Participate
 (
	groupId int NOT NULL,
	studentId varchar(8) NOT NULL,
	CONSTRAINT PK_Participate PRIMARY KEY (groupId, studentId)
)

ALTER TABLE Participate ADD CONSTRAINT FK_Participate_Group FOREIGN KEY(groupId)
REFERENCES [Group] (groupId)
ALTER TABLE Participate ADD CONSTRAINT FK_Participate_Student FOREIGN KEY(studentId)
REFERENCES Student (studentId)

 CREATE TABLE [Session]
 (
	sessionId int NOT NULL,
	sessionName nvarchar(200) NULL,
	[date] date NULL, 
	slotId int NULL,
	lecturerId varchar(20) NULL,
	groupId int NULL,
	roomId varchar(10) NULL,
	[sessionStatus] bit NULL,
	CONSTRAINT PK_Session PRIMARY KEY (sessionId)
)
ALTER TABLE [Session] ADD CONSTRAINT FK_Session_Group FOREIGN KEY(groupId)
REFERENCES [Group] (groupId)
ALTER TABLE [Session] ADD CONSTRAINT FK_Session_Room FOREIGN KEY(roomId)
REFERENCES Room (roomId)
ALTER TABLE [Session] ADD CONSTRAINT FK_Session_TimeSlot FOREIGN KEY(slotId)
REFERENCES TimeSlot (slotId)
ALTER TABLE [Session] ADD CONSTRAINT FK_Session_Instructor FOREIGN KEY(lecturerId)
REFERENCES Instructor(instructorId)

CREATE TABLE Attend
(
	studentId varchar(8) NOT NULL,
	sessionId int NOT NULL,
	[status] bit NULL,
	recordTime datetime NULL,
	comment nvarchar(max),
	CONSTRAINT PK_Attend PRIMARY KEY (studentId, sessionId)
)
ALTER TABLE Attend ADD CONSTRAINT FK_Attend_Session FOREIGN KEY(sessionId)
REFERENCES [Session] (sessionId)
ALTER TABLE Attend ADD CONSTRAINT FK_Attend_Student FOREIGN KEY(studentId)
REFERENCES Student (studentId)

/*
DROP TABLE Attend
DROP TABLE Participate
DROP TABLE [Session]
DROP TABLE [Group]
DROP TABLE Student
DROP TABLE Room
DROP TABLE Instructor
DROP TABLE TimeSlot
DROP TABLE Course

DROP TABLE Account_Role
DROP TABLE Account
DROP TABLE Role_Feature 
DROP TABLE [Role]
DROP TABLE Feature
*/

/*
SELECT * FROM Student
SELECT * FROM Participate
SELECT * FROM [Session]
SELECT * FROM [Group]
SELECT * FROM Room
SELECT * FROM Instructor
SELECT * FROM TimeSlot
SELECT * FROM Attend
SELECT * FROM Course

SELECT * FROM Account_Role
SELECT * FROM Account
SELECT * FROM Role_Feature 
SELECT * FROM [Role]
SELECT * FROM Feature

--	Thứ tự xóa bảng 
DELETE FROM Attend
DELETE FROM Participate
DELETE FROM [Session]
DELETE FROM [Group]
DELETE FROM Student
DELETE FROM Room
DELETE FROM Instructor
DELETE FROM TimeSlot
DELETE FROM Course

DELETE FROM Account_Role
DELETE FROM Account
DELETE FROM Role_Feature 
DELETE FROM [Role]
DELETE FROM Feature
*/
SET DATEFORMAT dmy 
--	1. VIEW ATTENDANCE 
--	Attendance for PRJ301 SE1723 with lecturer SonNT5 at slot 3 on Friday 02/03/2023.
--	This is the session number 6 in room BE-303 
SELECT c.groupName [Group], c.studentId Code, c.studentName [Name], c.studentImage [Image],
	   att.[status] [Status], att.comment Comment, ses.lecturerId Taker, 
	   att.recordTime AS [Record Time], ses.roomId, c.courseId
FROM (SELECT s.studentId, s.studentName, s.studentImage, g.groupId, g.groupName, g.courseId
			FROM Student s JOIN Participate p ON s.studentId = p.studentId
			JOIN [Group] g ON p.groupId = g.groupId WHERE g.groupId = 15 ) as c 
JOIN [Session] ses ON ses.groupId = c.groupId 
JOIN Attend att ON att.studentId = c.studentId  AND ses.sessionId = att.sessionId  
WHERE ses.[date] = '03/02/2023'


--	3. TIMETABLE 
--	Week 20/02/2023 to 26/02/2023 of lecturer SonNT5
SELECT i.instructorId, ses.date, t.slotNumber, t.startTime, t.endTime, g.courseId, g.groupName, ses.roomId, ses.sessionStatus
FROM Instructor i  JOIN [Session] ses ON i.instructorId = ses.lecturerId
				   JOIN [Group] g ON g.groupId = ses.groupId 
				   JOIN TimeSlot t ON ses.slotId = t.slotId
WHERE i.instructorId = 'sonnt5'
AND ses.date BETWEEN '20/02/2023' AND '26/02/2023' ORDER BY ses.date

SELECT s.sessionId, roomId, lecturerId, s.slotId, s.groupId, s.date from [Session] s WHERE s.lecturerId = 'sonnt5'

SELECT COUNT(*) AS Total FROM Account a 
INNER JOIN Account_Role ar on a.username = ar.username 
INNER JOIN [Role] g on g.roleId = ar.roleId
INNER JOIN [Role_Feature] rf on rf.roleId = g.roleId
INNER JOIN [Feature] f on rf.featureId = f.featureId 
where a.username = 'sonnt5@fpt.edu.vn' and f.url = '/instructor/timetable'

--take attendance
SELECT s.studentId, s.studentName, s.studentImage, 
ses.sessionId, ses.slotId, ses.groupId, g.groupName, ses.date,  ses.[sessionStatus],
a.status, a.recordTime, ISNULL(a.comment, '') AS comment
FROM Student s LEFT JOIN  Participate p On s.studentId =p.studentId
LEFT JOIN [Group] g On g.groupId = p.groupId 
LEFT JOIN [Session] ses ON ses.groupId = g.groupId
LEFT JOIN Attend a ON a.sessionId = ses.sessionId
WHERE ses.sessionId = 17

--update attendance
SELECT s.studentId, s.studentName, s.studentImage,
ses.sessionId, ses.groupId, g.groupName, ses.date, ses.lecturerId,
a.status, a.recordTime, ISNULL(a.comment, '') AS comment
FROM Student s JOIN  Participate p On s.studentId =p.studentId
JOIN [Group] g On g.groupId = p.groupId 
JOIN [Session] ses ON ses.groupId = g.groupId
JOIN Instructor i ON ses.lecturerId = i.instructorId
JOIN Attend a ON a.studentId = s.studentId
AND a.sessionId = ses.sessionId 
WHERE ses.sessionId = 2

SELECT a.studentId, a.sessionId, a.status, a.recordTime, a.comment FROM Attend a

SELECT a.studentId, a.sessionId, a.status, a.recordTime, a.comment,
ses.date, ses.groupId, g.groupName, ses.sessionStatus, s.studentName
from Attend a JOIN Session ses on a.sessionId = ses.sessionId 
JOIN [Group] g On g.groupId = ses.groupId
JOIN Student s On a.studentId = s.studentId
where ses.sessionId = 130
--
--report attend 

SELECT s.studentId, s.studentName,ses.groupId, g.groupName, 
ses.date,  ses.[sessionStatus], a.status
FROM Student s LEFT JOIN  Participate p On s.studentId =p.studentId
LEFT JOIN [Group] g On g.groupId = p.groupId 
LEFT JOIN [Session] ses ON ses.groupId = g.groupId
LEFT JOIN Attend a ON a.sessionId = ses.sessionId AND a.studentId = s.studentId
WHERE g.groupName = 'SE1723' AND g.courseId = 'PRJ301' order by ses.date
select * from [Group]
SELECT s.studentId, s.studentName,COUNT(*) AS numPresent 
FROM Student s JOIN Attend a ON a.studentId = s.studentId
JOIN  [Session] ses ON a.sessionId = ses.sessionId
JOIN [Group] g On g.groupId = ses.groupId 
WHERE  g.groupName = 'SE1723' AND g.courseId = 'PRJ301' AND a.status = 1
group by  s.studentId, s.studentName, a.status

SELECT g.courseId, g.groupName, s.studentId, s.studentName,COUNT(*) AS numPresent 
FROM Student s JOIN Attend a ON a.studentId = s.studentId
JOIN  [Session] ses ON a.sessionId = ses.sessionId
JOIN [Group] g On g.groupId = ses.groupId 
WHERE  g.groupName = 'SE1638-NET' AND g.courseId = 'PRN221' AND a.status = 1
group by  s.studentId, s.studentName, a.status,g.courseId, g.groupName

select count(*) as numberSlot from Session ses join [group] g on ses.groupId = g.groupId where courseId = 'IOT102' AND ses.sessionStatus = 1
select * from Session ses join [group] g on ses.groupId = g.groupId where courseId = 'IOT102' AND g.groupName = 'SE1722' AND ses.sessionStatus = 1
select * from Session ses join [group] g on ses.groupId = g.groupId where g.courseId = 'PRN221' AND g.groupName = 'SE1638-NET' order by ses.date
select * from Session ses join [group] g on ses.groupId = g.groupId where g.courseId = 'PRJ301' AND g.groupName = 'SE1723' 

select * from participate p  join student s on p.studentId = s.studentId join [group] g on g.groupId = p.groupId WHERE courseId='PRN221' AND groupName='SE1638-NET'

go
CREATE PROC Percent_Absent @groupId int 
AS
BEGIN 
	DECLARE @total int, @current int
	select @total = count(*) from Session ses join [group] g on ses.groupId = g.groupId 
					where g.groupId = @groupId
	select @current = count(*) from Session ses join [group] g on ses.groupId = g.groupId 
					where g.groupId = @groupId AND ses.sessionStatus = 1
	SELECT s.studentId, s.studentName, COUNT(*) AS  numPresent, 
	ROUND((CAST(@current - COUNT(*) AS float) / @total*100),2) as percentAbsent
	FROM Student s JOIN Attend a ON a.studentId = s.studentId
	JOIN  [Session] ses ON a.sessionId = ses.sessionId
	JOIN [Group] g On g.groupId = ses.groupId 
	WHERE  g.groupId = @groupId AND a.status = 1
	GROUP BY  s.studentId, s.studentName
END 
go
EXEC Percent_Absent 4

--DROP PROC Percent_Absent
select count(*) from Session ses join [group] g on ses.groupId = g.groupId 
					where g.groupId =4
select count(*) from Session ses join [group] g on ses.groupId = g.groupId 
					where g.groupId = 4 AND ses.sessionStatus = 1
go
CREATE PROC Report_Attend @groupId int
AS
BEGIN 
SELECT s.studentId, s.studentName,ses.groupId, g.groupName, 
ses.date,  ses.[sessionStatus], ISNULL(a.status, 0) AS status
FROM Student s LEFT JOIN  Participate p On s.studentId =p.studentId
LEFT JOIN [Group] g On g.groupId = p.groupId 
LEFT JOIN [Session] ses ON ses.groupId = g.groupId
LEFT JOIN Attend a ON a.sessionId = ses.sessionId AND a.studentId = s.studentId
WHERE g.groupId = @groupId order by studentId, date
END 
go 
EXEC Report_Attend 8
--DROP PROC Report_Attend

select s.studentId from account a join student s on a.username = s.accountId where a.username = 'he171073@fpt.edu.vn'
select i.instructorId from account a join Instructor i on a.username = i.accountId where a.username = 'sonnt5@fpt.edu.vn'

--timetable student 
SELECT  stu.studentId, ses.groupId, g.groupName, g.courseId,
ses.date,  ses.[sessionStatus], ses.roomId, ses.sessionId,
t.slotNumber, t.startTime, t.endTime, a.status 
FROM Student stu 
JOIN Participate p on stu.studentId = p.studentId
JOIN [Group] g on g.groupId = p.groupId
JOIN [Session] ses on ses.groupId = g.groupId
LEFT JOIN TimeSlot t ON ses.slotId = t.slotId 
LEFT JOIN Room r ON r.roomId = ses.roomId
LEFT JOIN Attend a on a.studentId = stu.studentId AND a.sessionId = ses.sessionId
WHERE stu.studentId = 'HE171073' 
AND ses.date BETWEEN '03/06/2023' AND '03/12/2023'

-- report attend student 
SELECT s.studentId, s.studentName,ses.groupId, g.groupName,
ses.date,  ses.[sessionStatus], ses.roomId, ses.lecturerId, a.status, ses.sessionId,
t.slotNumber, t.startTime, t.endTime, ISNULL(a.comment,'') AS comment
FROM Student s JOIN  Participate p On s.studentId =p.studentId
JOIN [Group] g On g.groupId = p.groupId 
JOIN [Session] ses ON ses.groupId = g.groupId
JOIN TimeSlot t ON t.slotId = ses.slotId
JOIN Room r ON r.roomId = ses.roomId
LEFT JOIN Attend a ON a.sessionId = ses.sessionId AND a.studentId = s.studentId
WHERE g.groupId = 15 AND s.studentId = 'HE171073' order by ses.date

select * from course
select * from [Session] ses join [Group] g on ses.groupId = g.groupId JOIN Course c ON c.courseId = g.courseId WHERE g.groupId = 15

SELECT  stu.studentId, G.groupId, g.groupName, g.courseId, c.courseName
FROM Student stu 
JOIN Participate p on stu.studentId = p.studentId
JOIN [Group] g on g.groupId = p.groupId
JOIN Course c on c.courseId = g.courseId WHERE stu.studentId = 'HE171073'

