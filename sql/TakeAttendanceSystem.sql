CREATE DATABASE PRJ301_TakeAttendanceSystem
USE PRJ301_TakeAttendanceSystem
CREATE TABLE Course(
	courseId varchar(10) NOT NULL PRIMARY KEY,
	courseName nchar(100)
 )
 CREATE TABLE Instructor(
	instructorId varchar(20) NOT NULL PRIMARY KEY,
	instructorName nvarchar(100) 
)
CREATE TABLE Student(
	studentId varchar(8) NOT NULL PRIMARY KEY,
	studentName nvarchar(100)
 )
 CREATE TABLE TimeSlot(
	slotId int NOT NULL PRIMARY KEY,
	slotNumber int,
	startTime time,
	endTime time
)
CREATE TABLE Room(
	roomId varchar(10) NOT NULL PRIMARY KEY ,
)
CREATE TABLE [Group](
	groupId int NOT NULL  PRIMARY KEY ,
	groupName varchar(20) NULL,
	courseId varchar(10) NULL,
	instructorId varchar(20) NULL,
)
ALTER TABLE [Group] ADD  CONSTRAINT FK_Group_Course FOREIGN KEY(courseId)
REFERENCES Course (courseId)
ALTER TABLE [Group]  ADD  CONSTRAINT FK_Group_Instructor FOREIGN KEY(instructorId)
REFERENCES Instructor (instructorId)

 CREATE TABLE Participate(
	groupId int NOT NULL,
	studentId varchar(8) NOT NULL,
	PRIMARY KEY(groupId, studentId)
)

ALTER TABLE Participate  ADD  CONSTRAINT FK_Participate_Group FOREIGN KEY(groupId)
REFERENCES [Group] (groupId)

ALTER TABLE Participate ADD  CONSTRAINT FK_Participate_Student FOREIGN KEY(studentId)
REFERENCES Student (studentId)

 CREATE TABLE Session(
	sessionId int NOT NULL PRIMARY KEY,
	sessionName nvarchar(200) NULL,
	[date] date NULL,
	slotId int NULL,
	lecturerId varchar(20) NULL,
	groupId int NULL,
	roomId varchar(10) NULL
)
ALTER TABLE Session ADD  CONSTRAINT FK_Session_Group FOREIGN KEY(groupId)
REFERENCES [Group] (groupId)
ALTER TABLE Session ADD  CONSTRAINT FK_Session_Room FOREIGN KEY(roomId)
REFERENCES Room (roomId)
ALTER TABLE Session ADD  CONSTRAINT FK_Session_TimeSlot FOREIGN KEY(slotId)
REFERENCES TimeSlot (slotId)
ALTER TABLE Session ADD  CONSTRAINT FK_Session_Instructor FOREIGN KEY(lecturerId)
REFERENCES Instructor(instructorId)


CREATE TABLE Attend(
	studentId varchar(8) NOT NULL,
	sessionId int NOT NULL,
	[status] bit NULL,
	recordTime timestamp NULL,
	PRIMARY KEY (studentId, sessionId)
)
ALTER TABLE Attend ADD  CONSTRAINT FK_Attend_Session FOREIGN KEY(sessionId)
REFERENCES Session (sessionId)

ALTER TABLE Attend ADD  CONSTRAINT FK_Attend_Student FOREIGN KEY(studentId)
REFERENCES Student (studentId)

DROP TABLE Attend
DROP TABLE Participate
DROP TABLE [Session]
DROP TABLE [Group]
DROP TABLE Student
DROP TABLE Room
DROP TABLE Instructor
DROP TABLE TimeSlot
DROP TABLE Course
