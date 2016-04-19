drop table IF EXISTS edge;
create table edge(src int, dest int);
insert into edge values(1,2);
insert into edge values(1,3);
insert into edge values(1,4);
insert into edge values(2,1);
insert into edge values(2,3);
insert into edge values(2,4);
insert into edge values(3,1);
insert into edge values(3,2);
insert into edge values(3,4);
insert into edge values(4,1);
insert into edge values(4,2);
insert into edge values(4,3);

select * from edge;
