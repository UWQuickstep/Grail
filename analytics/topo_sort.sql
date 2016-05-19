DROP TABLE IF EXISTS cur;

DROP TABLE IF EXISTS message;

DROP TABLE IF EXISTS next;

DROP TABLE IF EXISTS out_cnts;

DROP TABLE IF EXISTS toupdate;

DROP INDEX IF EXISTS idx_src;

DROP INDEX IF EXISTS idx_dest;

DROP TABLE IF EXISTS next;
 CREATE TABLE next AS
SELECT id AS id, CAST(0 AS INT) AS val1, CAST(NULL AS INT) AS val2
FROM vertex
;

DROP SEQUENCE IF EXISTS my_seq;
CREATE SEQUENCE my_seq MINVALUE 0;
SELECT setval('my_seq',0);

INSERT INTO in_cnts select vertex.id, count(src) as cnt from vertex left outer join edge on vertex.id = edge.dest group by vertex.id;

CREATE TABLE message(
 id int,
 val INT
);

INSERT INTO message
(SELECT id, CAST(0 as INT)
FROM in_cnts
WHERE cnt=0 );

DO $$
DECLARE

flag integer := -1;

BEGIN

WHILE flag != 0 LOOP

 DROP TABLE IF EXISTS cur;
 CREATE TABLE cur AS
SELECT message.id AS id, SUM(message.val) AS val
 FROM message
 GROUP BY id
 ;

 DROP TABLE IF EXISTS message;

 UPDATE next SET 
 val1=cur.val+next.val1
 FROM 
 cur
 WHERE 
 next.id=cur.id
 ;

 UPDATE next SET 
 val2=nextval('my_seq')
 FROM 
 in_cnts,
 cur
 WHERE 
 next.id=in_cnts.id and 
 next.id=cur.id
 and next.val1 = in_cnts.cnt
 ;

 DROP TABLE IF EXISTS message;
 CREATE TABLE message AS
SELECT edge.dest AS id, 1 AS val
 FROM next, cur, edge
 WHERE next.id = cur.id  AND edge.src = cur.id AND next.val2 is not null
 GROUP BY dest
 ;

 DROP TABLE IF EXISTS cur;

 flag := (SELECT COUNT (*) FROM message);
END LOOP;
 
END $$;

