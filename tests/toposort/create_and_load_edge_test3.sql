drop table IF EXISTS edge;
create table edge(src int, dest int);
insert into edge values(3,4);
insert into edge values(4,2);
insert into edge values(5,2);
insert into edge values(5,1);
insert into edge values(6,1);
insert into edge values(6,3);
select * from edge;
