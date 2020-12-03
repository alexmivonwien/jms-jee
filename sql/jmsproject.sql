
-- SET GLOBAL general_log = 'OFF'
-- create database jmsjee;
-- CREATE USER 'homeToDealuser'@'localhost' IDENTIFIED BY 'sasho0304';
-- GRANT ALL PRIVILEGES ON jmsjee.* TO 'homeToDealuser'@'localhost' IDENTIFIED BY 'sasho0304' WITH GRANT OPTION;
-- ALTER DATABASE jmsjee CHARACTER SET utf8 COLLATE utf8_unicode_ci;



use jmsjee;


CREATE TABLE if not exists EVENT (
   id int(11) not null AUTO_INCREMENT,
   typ  varchar(255),
   constraint PK_ID primary key (id)
) ;

CREATE TABLE if not exists ACT (
	id int(11) not null AUTO_INCREMENT, 
	typ  varchar(255),
    constraint PK_ID primary key (id)
) ;

CREATE TABLE if not exists EVENT_ACT (
  id int(11) not null AUTO_INCREMENT, 
  EVENT_ID int(10) unsigned NOT NULL,
  ACT_ID int(10) unsigned NOT NULL,
  constraint PK_ID primary key (id)
) ;


CREATE TABLE if not exists CONFIG (
  NAME  varchar(255),
  VAL varchar(255),
  constraint PK_ID primary key (NAME)
 ) ;

-- =============================================

 use jmsjee;
 
INSERT INTO jmsjee.EVENT VALUES (1, 'INFO');
INSERT INTO jmsjee.EVENT VALUES (2, 'WARNING');
INSERT INTO jmsjee.EVENT VALUES (3, 'EXCEPTION');

INSERT INTO jmsjee.ACT VALUES (1, 'SendMail');
INSERT INTO jmsjee.ACT VALUES (2, 'LogFile');
INSERT INTO jmsjee.ACT VALUES (3, 'LogDB');

INSERT INTO jmsjee.EVENT_ACT (id, EVENT_id, ACT_ID) VALUES (1, 1, 2);
INSERT INTO jmsjee.EVENT_ACT (id, EVENT_id, ACT_ID) VALUES (2, 2, 3);
INSERT INTO jmsjee.EVENT_ACT (id, EVENT_id, ACT_ID) VALUES (3, 3, 3);
INSERT INTO jmsjee.EVENT_ACT (id, EVENT_id, ACT_ID) VALUES (4, 3, 1);


INSERT INTO jmsjee.CONFIG VALUES ('INTERVAL', 9000);