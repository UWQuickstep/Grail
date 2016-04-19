drop table IF EXISTS edge;
create table edge(src int, dest int);
insert into edge values(1,2);
insert into edge values(3,4);

select * from edge;
