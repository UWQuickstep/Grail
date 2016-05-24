DROP TABLE IF EXISTS cur_alias;

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

CREATE TABLE out_cnts AS select vertex.id, count(dest) as cnt from vertex left outer join edge on vertex.id = edge.src group by vertex.id;

CREATE TABLE message(
 id int,
 val float
);

INSERT INTO message
(SELECT *, CAST(0 as float)
FROM vertex
);

CREATE INDEX idx_src ON edge(src);

DO $$
DECLARE

flag integer :=  50;

isFirst integer := 1;
BEGIN

WHILE flag != 0 LOOP

 IF (isFirst = 1)
 THEN
 DROP TABLE IF EXISTS cur;
 CREATE TABLE cur AS
SELECT message.id AS id, SUM(message.val)*0.85 + 0.15 AS val
 FROM message
 GROUP BY id
 ;
 isFirst := 0;
 ELSE
 DROP TABLE IF EXISTS message;
 CREATE TABLE message AS
SELECT edge.dest AS id, SUM(cur_alias.val/out_cnts.cnt)*0.85 + 0.15 AS val
 FROM cur_alias, edge, out_cnts
 WHERE edge.src = cur_alias.id AND out_cnts.id = cur_alias.id AND out_cnts.cnt > 0
 ;
 DROP TABLE IF EXISTS cur_alias;
 END IF;

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
SELECT edge.dest AS id, SUM(cur.val/out_cnts.cnt)*0.85 + 0.15 AS val
 FROM cur, edge, out_cnts
 WHERE edge.src = cur.id AND out_cnts.id = cur.id AND out_cnts.cnt > 0
 ;

 ALTER TABLE cur RENAME TO cur_alias;

 flag := flag - 1;
END LOOP;
 
END $$;

