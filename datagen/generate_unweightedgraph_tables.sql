-- Delete existing vertex, edge and tmp tables
DROP TABLE IF EXISTS vertex,edge,temp;

-- Creating edge and vertex tables
CREATE TABLE vertex (id int PRIMARY KEY);
CREATE TABLE edge (src int, dest int );

DO $$
DECLARE

-- set the number of nodes and edges in the graph
nodecount integer := 5;
edgecount integer := 10;

BEGIN

-- Generate the vertex table
INSERT INTO vertex
   SELECT generate_series
   FROM generate_series(1, nodecount);

WHILE edgecount != 0 LOOP
	-- Insert a row in the edge table
	INSERT INTO edge 
	SELECT floor(random()*(nodecount-1)+1), floor(random()*(nodecount-1)+1);
edgecount := edgecount - 1;

END LOOP;
END $$;

-- Remove all duplicate rows in the edge table
SELECT DISTINCT  * INTO temp FROM edge;
DELETE FROM edge;
INSERT INTO edge SELECT * fROM temp;
DROP TABLE temp;

-- delete rows where source and destination are equal
DELETE FROM edge WHERE src=dest;





