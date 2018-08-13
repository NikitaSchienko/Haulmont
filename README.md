Test Task
=========

Prerequisites
-------------

* [Java Development Kit (JDK) 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Maven 3](https://maven.apache.org/download.cgi)

Build and Run
-------------

1. Run in the command line:
	```
	mvn package
	mvn jetty:run
	```
	mvn -Djetty.port=8181 jetty:run

2. Open `http://localhost:8080` in a web browser.

INSERT INTO PATIENT VALUES(1,'Вадим','Дюжев','Александрович',89376562767)
INSERT INTO PATIENT VALUES(2,'Олег','Пахомов','Олегович',89376562315)
INSERT INTO PATIENT VALUES(3,'Елена','Белова','Александровна',89376562222)
INSERT INTO PATIENT VALUES(4,'Вадим','Иванов','Иванович',89371112267)
INSERT INTO PATIENT VALUES(5,'Кирилл','Сахипов','Видомов',89376552267)
INSERT INTO PATIENT VALUES(6,'Елена','Дружинина','Путина',89376562267)
INSERT INTO DOCTOR VALUES(1,'Вадим','Сидоров','Александрович','Окулист')
INSERT INTO DOCTOR VALUES(2,'Александр','Куц','Олегович','Лор')
INSERT INTO DOCTOR VALUES(3,'Ольга','Винина','Александровна','Терапевт')
INSERT INTO DOCTOR VALUES(4,'Вадим','Вадимов','Игоревич','Терапевт')
INSERT INTO RECIPE(DESCRIPTION, PATIENT, DOCTOR, DATE_START, DATE_LENGTH, PRIORITY) VALUES ('Аллергия на кошек. Зодак, Супрастин - принимать за 3 дня перед встречей с аллергеном.',1,2,1535572800000,1535578800000,'Нормальный')
INSERT INTO RECIPE(DESCRIPTION, PATIENT, DOCTOR, DATE_START, DATE_LENGTH, PRIORITY) VALUES ('Противовирусное средство 3 раза в день в течении месяца',5,4,1535572800000,1535578800000,'Срочный')
INSERT INTO RECIPE(DESCRIPTION, PATIENT, DOCTOR, DATE_START, DATE_LENGTH, PRIORITY) VALUES ('Смекта по 1 табл. 1 раз в день',2,4,1535572800000,1599579500000,'Срочный')
 java -classpath lib/hsqldb.jar org.hsqldb.server.Server --database.0 file:hsqldb/demodb --dbname.0 testdb