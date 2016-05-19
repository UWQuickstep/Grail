DROP TABLE IF EXISTS cur;

DROP TABLE IF EXISTS message;

DROP TABLE IF EXISTS next;

DROP TABLE IF EXISTS out_cnts;

DROP TABLE IF EXISTS in_cnts;

DROP TABLE IF EXISTS toupdate;

DROP INDEX IF EXISTS idx_src;

DROP INDEX IF EXISTS idx_dest;

DROP TABLE IF EXISTS next;
 CREATE TABLE next AS
SELECT id AS id, CAST(0 AS float) AS  val
FROM vertex
;

INSERT INTO out_cnts select vertex.id, count(dest) as cnt from vertex left outer join edge on vertex.id = edge.src group by vertex.id;

CREATE TABLE message(
 id int,
 val float
);

INSERT INTO message
(SELECT *, CAST(0 as float)
FROM vertex
);

DO $$
DECLARE

flag integer :=  50;

BEGIN

WHILE flag != 0 LOOP

 DROP TABLE IF EXISTS cur;
 CREATE TABLE cur AS
SELECT message.id AS id, SUM(message.val)*0.85 + 0.15 AS val
 FROM message
 GROUP BY id
 ;

 DROP TABLE IF EXISTS message;

 UPDATE next SET 
 val=cur.val
 FROM 
 cur
 WHERE 
 next.id=cur.id
 ;

 DROP TABLE IF EXISTS message;
 CREATE TABLE message AS
SELECT edge.dest AS id, cur.val/out_cnts.cnt AS val
 FROM cur, edge, out_cnts
 WHERE edge.src = cur.id AND out_cnts.id = cur.id
 ;

 DROP TABLE IF EXISTS cur;

 flag := flag - 1;
END LOOP;
 
END $$;

