CREATE TABLE USERTABLE(
username CHAR(20) NOT NULL PRIMARY KEY Unique,
password CHAR(20) NOT NULL
);

CREATE TABLE FriendRelation(
username1 CHAR(20) NOT NULL,
username2 CHAR(20) NOT NULL,
CONSTRAINT CKey PRIMARY KEY(username1,username2));

CREATE TABLE Dictionary(
Word CHAR(30) PRIMARY KEY,
Baidu blob,
Bing blob,
Youdao blob,
NumZanBaidu INT,
NumZanYoudao INT,
NumZanBing INT
);

CREATE TABLE Wordcard(
Sender CHAR(30),
Receiver CHAR(30),
Image blob
);

CREATE TABLE BaiduPraise(
username CHAR(20) NOT NULL,
Word CHAR(30) NOT NULL,
CONSTRAINT CKey PRIMARY KEY(username,Word));

Create Trigger AddPraiseBaidu
After INSERT on BaiduPraise 
for each row
Update Dictionary
Set NumZanBaidu = NumZanBaidu + 1 where Word = new.Word;

Create Trigger DelPraiseBaidu
After DELETE on BaiduPraise 
for each row
Update Dictionary
Set NumZanBaidu = NumZanBaidu - 1 where Word = old.Word;

CREATE TABLE YouDaoPraise(
username CHAR(20) NOT NULL,
Word CHAR(30) NOT NULL,
CONSTRAINT CKey PRIMARY KEY(username,Word));

Create Trigger AddPraiseYouDao
After INSERT on YouDaoPraise 
for each row
Update Dictionary
Set NumZanYouDao = NumZanYouDao + 1 where Word = new.Word;

Create Trigger DelPraiseYouDao
After DELETE on YouDaoPraise 
for each row
Update Dictionary
Set NumZanYouDao = NumZanYouDao - 1 where Word = old.Word;

CREATE TABLE BingPraise(
username CHAR(20) NOT NULL,
Word CHAR(30) NOT NULL,
CONSTRAINT CKey PRIMARY KEY(username,Word));

Create Trigger AddPraiseBing
After INSERT on BingPraise 
for each row
Update Dictionary
Set NumZanBing = NumZanBing + 1 where Word = new.Word;

Create Trigger DelPraiseBing
After DELETE on BingPraise 
for each row
Update Dictionary
Set NumZanBing = NumZanBing - 1 where Word = old.Word;
-- Test-------------------------------------------------------------------------
insert into Dictionary(Word,NumZanBaidu,NumZanYoudao,NumZanBing) values('Good',0,0,0);
insert into BingPraise(username,Word) values('roy','Good');

insert into Praise(username,Word) values('zhangry868','Hello');

select* from FriendRelation;

insert into FriendRelation(username1,username2) values('zhangry868','roy');

delete from FriendRelation where username1='zhangry868' and username2 = 'roy';

select* from Dictionary;
select* from BaiduPraise;

Select * from Dictionary;
insert into USERTABLE(username,password) values('zhangry868','123456');

Select * from usertable;
