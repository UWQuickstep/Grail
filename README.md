GRAIL 
============================
GRAIL is an approach to run a common class of iterative graph analytics on top
of existing relational database engines. So, you do not need a specialized graph
engine! The original paper is at: 
http://pages.cs.wisc.edu/~jignesh/publ/Grail.pdf.

Overview of GRAIL
-----------------
To use GRAIL, you have to load the graph data into two basic tables in your
relational database system. These two tables are: `vertex` and `edge`.

The vertex table is created in SQL as:

```sql
    CREATE TABLE vertex {
      vertex_id INT
    }
```

The edge table is created as:
```sql
    CREATE TABLE edge {
      src_id INT,
      dest_id INT
    }
```

Additional primary key, indices and other constraints can also be added 
to the schema creating commands above.

Once you have load your graph data into these two tables, you then express
the iterative graph analytics in a configuration file (see examples below). 
Then, you compile the configuration file using Grail to produce a SQL script.
You then compute the graph analytics by running the SQL script against the
database engine that has the graph data (stored in tables as described above). 

Directory Organization
----------------------
There are three directories here: `analytics`, `java`, and `python`.

1. The `analytics` directory contains configuration files for various graph
   analytics. This directory has the following files:

   | **Filename**        | **# Description**                                  |
   | :------------------ |---------------------------------------------------:|
   | pagerank.grail      | PageRank algorithm                                 |
   | sssp.grail          | Single-source shortest path                        |
   | wcc.grail           | Weakly connected components                        | 

2. The `java` directory contains a Java-based Grail compiler to generate SQL
   code for Microsoft SQL Server (this is what was used in the original Grail 
   paper).

3. The `python` directory contains a Python-based Grail compiler to generate SQL
   code for PostgreSQL. 


Description of the attributes in the configuration file 
-------------------------------------------------------
1. *VertexValueType*: The type of value that a vertex represents. It should be
   one of the types supported by the relational database engine, e.g. 
   `int` and `float`.

2. *MessageValueType*: The type of messages that a vertex can send and receive.
   It should also be one of the types supported by the database system, e.g.
   `int` and `float`.

3. *InitiateVal*: The initial value of the vertices. For example, if the vertices
   are of type `int`, then initial value could be zero.

4. *Initial Message*: The initial messages to send to all or specific vertices.
   (ALL, value) and (some_vertex_id, value) is used for sending the initial
   message to all vertices and to a specific vertex respectively.

5. *CombineMessage*: The method to compute an aggregate value on the "messages."
   The message will be automatically grouped on the destination `vertex id`.
   The aggregation should be supported by the database system. Some exmaples are
   built-in SQL aggregates like `MIN` and `MAX`. Many SQL system allow a user to
   define aggregate functions, i.e. they support user-defined aggregate
   functions (UDAF). UDAFs are also permitted here. Note that the UDAFs must be
   defined and registered with the database system separately.

6. *UpdateAndSend*: This part can be a combination of one or more of the
  following actions: mutate values, send messages, and flow control.

7. *End*: Ther are two possiblities: `NO_MESSAGE` or `(ITER, max_iterations)` 
   - The `NO_MESSAGE` option terminates the graph computation, when there are no
     more messages that need to be processed. 
   - The `(ITER, max_iterations)` option terminates the program after 
     `max_iterations` number of iterations.
