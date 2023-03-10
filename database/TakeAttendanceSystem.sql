--CREATE DATABASE PRJ301_TakeAttendanceSystem
USE PRJ301_TakeAttendanceSystem

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
	CONSTRAINT PK_Instructor PRIMARY KEY (instructorId)

)

CREATE TABLE Student
(
	studentId varchar(8) NOT NULL,
	studentName nvarchar(100),
	studentImage varchar(max),
	CONSTRAINT PK_Student PRIMARY KEY (studentId)
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

--	2. TAKE ATTENDANCE Chưa có dữ liệu 
--	Attendance for sonnt5 at slot 3 on Friday 21/03/2023.
--	This is the session number 19 of the PRJ301 - SE1723
SELECT c.groupName [Group], c.studentId AS [Roll Number], c.studentName AS [Full Name],
	   att.[status] Present, att.comment Comment, c.studentImage [Image], ses.roomId
FROM (SELECT s.studentId, s.studentName, s.studentImage, g.groupId, g.groupName
			FROM Student s JOIN Participate p ON s.studentId = p.studentId
			JOIN [Group] g ON p.groupId = g.groupId WHERE g.groupId = 15 ) as c 
JOIN [Session] ses ON ses.groupId = c.groupId 
JOIN Attend att ON att.studentId = c.studentId  AND ses.sessionId = att.sessionId  
WHERE ses.date = '21/03/2023'

--	3. TIMETABLE 
--	Week 20/02/2023 to 26/02/2023 of lecturer SonNT5
SELECT i.instructorId, ses.date, t.slotNumber, t.startTime, t.endTime, g.courseId, g.groupName, ses.roomId, ses.sessionStatus
FROM Instructor i  JOIN [Session] ses ON i.instructorId = ses.lecturerId
				   JOIN [Group] g ON g.groupId = ses.groupId 
				   JOIN TimeSlot t ON ses.slotId = t.slotId
WHERE i.instructorId = 'sonnt5'
AND ses.date BETWEEN '20/02/2023' AND '26/02/2023' ORDER BY ses.date

--	4. REPORT ATTENDANCE 
-- tạo procedure 
SELECT s.sessionId, roomId, lecturerId, s.slotId, s.groupId, s.date from [Session] s WHERE s.lecturerId = 'sonnt5'


				   
select *  from [Session] ses JOIN TimeSlot t ON ses.slotId = t.slotId 
select ses.sessionId, ses.date, t.slotNumber, ses.lecturerId, ses.groupId, ses.roomId, ses.sessionStatus from [Session] ses JOIN TimeSlot t ON ses.slotId = t.slotId 
