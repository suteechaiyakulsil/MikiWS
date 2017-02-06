WS Example by request SETIndex Name http://localhost:8080/MikiWS/miki/stockindexservice/SET


WS Example with update Example http://localhost:8080/MikiWS/miki/stockindexservice/SET/2017-02-06-01.40/1,600.00


Script to create mySQL database

------------------------------------------------------------------
--  TABLE Stock
------------------------------------------------------------------

CREATE TABLE `Stock`
(
   `SetIndex`     varchar(20),
   `Last`         varchar(20),
   `LastUpdate`   datetime,
   `ID`           int(10)
);

------------------------------------------------------------------
