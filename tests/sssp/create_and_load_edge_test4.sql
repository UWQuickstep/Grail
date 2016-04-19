drop table IF EXISTS edge;
create table edge(src int, dest int, weight int);
insert into edge values(1,2,2);
insert into edge values(2,3,4);
insert into edge values(2,4,2);
insert into edge values(1,4,1);
insert into edge values(4,5,1);
insert into edge values(5,3,1);
select * from edge;
