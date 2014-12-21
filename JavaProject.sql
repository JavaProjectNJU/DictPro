

CREATE TABLE USERTABLE(
username CHAR(20) NOT NULL PRIMARY KEY Unique,
password CHAR(20) NOT NULL,
nickname CHAR(20),
email CHAR(30),
sex boolean,
online boolean,
image blob,
logindate blob 
);

insert into USERTABLE(username,password,nickname,email,sex) values('zhangry','123456','Roy','zhangry868@126.com',true);
insert into USERTABLE(username,password,nickname,email,sex) values('Haohao','123456','Haohao','Wuhao1994@126.com',true);
insert into USERTABLE(username,password,nickname,email,sex) values('Jam','123456','Haohao','Jam@126.com',true);


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
/*
alter table BaiduPraise add constraint DEL_USER1 foreign key(username)
references USERTABLE(username) on delete cascade;
alter table BingPraise add constraint DEL_USER2 foreign key(username)
references USERTABLE(username) on delete cascade;
alter table YouDaoPraise add constraint DEL_USER3 foreign key(username)
references USERTABLE(username) on delete cascade;

alter table FriendRelation add constraint DEL_USER4 foreign key(username1)
references USERTABLE(username) on delete cascade;
alter table FriendRelation add constraint DEL_USER5 foreign key(username2)
references USERTABLE(username) on delete cascade;
*/
-- Test-------------------------------------------------------------------------
insert into Dictionary(Word,NumZanBaidu,NumZanYoudao,NumZanBing) values('Good',0,0,0);
insert into BingPraise(username,Word) values('roy','Good');

select* from FriendRelation;

insert into FriendRelation(username1,username2) values('zhangry868','roy');

delete from FriendRelation where username1='zhangry868' and username2 = 'roy';

select* from Dictionary;
select* from BaiduPraise;

Select * from Dictionary;
insert into USERTABLE(username,password) values('zhangry868','123456');

Select * from usertable;

Select * from friendrelation;
Select * from wordcard;
