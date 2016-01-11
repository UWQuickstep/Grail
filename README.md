# Grail
This repository contains code for the Grail method described in the [CIDR 2015 paper]( http://pages.cs.wisc.edu/~jignesh/publ/Grail.pdf "Grail CIDR paper"). There is also [an associated slide deck]
(http://www.cs.wisc.edu/%7Ejignesh/publ/Grail-slides.pdf "Grail presentation").

At a high level, you load graph data into the `vertex` and the `edge` tables below, and then run the code here to generate SQL to analyze the graphs. The code here generates T-SQL (for Microsoft SQL Server, which we had used in the original paper). 
Over time, we are interested in making Grail work with other databases. Let us know if you want to help!

## Loading graph data in the format needed by Grail
Before we can run Grail, we need to load the graph data in to the database. A key aspect of Grail is that it works with data stored in traditional relations. The graph data must be represented using a standard "adjacency list" representation, stored in two relational tables. These two tables are called `vertex` and `edge` tables.

Create the `vertex` table is created in SQL as:

```sql
    CREATE TABLE vertex {
      vertex_id INT
    }
```

Then, create the `edge` table in a similar way as:
```sql
    CREATE TABLE edge {
      src_id INT,
      dest_id INT
    }
```

Additional primary key, indices and other constraints can also be added to the schema creating commands above. (A crucial aspect of future work for Grail is to automate these physical schema design for higher performance.)

Next, load your graph data into these two tables. Here use the standard methods of your choice to load. 

You are now ready to run graph queries against the data that you have loaded. The basic idea is that a graph analytics program can be run as a sequence of SQL queries, which can be generated by the Grail code. So, let go to the next step and generate the Grail code for three graph operation: Shortest Path, PageRank, and Connected Component)

## Running Grail

As stated in the [original Grail paper] (http://pages.cs.wisc.edu/~jignesh/publ/Grail.pdf, "Grail CIDR paper"), Grail is a high-level description of the graph analytic method that preserves the familiar vertex-centric method of programming graph analytics. 

To run graph analytics with Grail, there are four steps.

1. Write a configuration file. We have written several of these to get you started. See the directory `src/configs` for three files. The file `pagerank.txt` is the specification for coomputing PageRank. The files `configs/sssp.txt` and `configs/wcc.txt` contain the specification for Single Source Shortest Path (SSSP), and Weakly Connected Component (WCC) respectively.

2. Compile the configuration file using Grail, as follows: 
  * Go to the "src" directory (`cd src`). 
  * Then, compile the code. The code is written in Java, so you will need to [install the Java Development Kit](http://www.oracle.com/technetwork/java/javase/downloads/index.html "JDK Install Page"). Compile using the command: `javac *.java`
  * Finally, run the Grail code by speficying the apppopriate configuration file. If no configuration file is specified, then the default configuration file `config.txt` is used. Here are some examples: 
     - `java Grail` (uses the default configuration file `config.txt`)
     - `java Grail configs/pagerank.txt` (to generate the SQL for a pagerank graph program)
     - `java Grail configs/sssp.txt` (to generate the SQL for a Single Source Shortest Path(SSSP) graph program)
     - `java Grail configs/wcc.txt` (to generate the SQL for a Weakly Connected Component(WCC) graph program)


3. Execute the SQL statements generated by Grail (i.e. output of the step above).

## Details about the attributes of configuration file
If you need to write a new graph algorithm, you will need to specify a new configuration file. You should read the [CIDR 2015 paper]( http://pages.cs.wisc.edu/~jignesh/publ/Grail.pdf "Grail CIDR paper") for details about the attributes that are specified in the configuration file, and here are some of the highlights. 

1. VertexValueType: The type of value that a vertex represents. It should be one of the types supported by the database engine. Examples: int, float, etc.

2. MessageValueType: The type of messages that a vertex can send and receive. It should also be one of the types supported by the database engine. Examples: int, float, etc.

3. InitiateVal: The initial value of the vertices. For example, if the vertices have int value, the initial value could be zero.

4. Initial Message: The initial messages to all or specific vertices. (ALL, value) and (some_vertex_id, value) is used for sendind the initial message to all vertices and to a specific vertex respectively.

5. CombineMessage: The function to aggregate the values in the messages. The message will be automatically grouped on the destination vertex id. The aggregation should be supported in the database engine. It can be MIN, MAX, or a user-defined aggregate function (UDAF). The UDAF should be defined first.

6. UpdateAndSend: The updateandsend part can be a combination of one or more of the following actions: mutate values, send messages, and flow control.

7. End: NO_MESSAGE or (ITER, max_iterations) - NO_MESSAGE terminates the graph computation, when there are no more messages remaining to be processed. (ITER, max_iterations) terminates the program after max_iterations number of iterations.
