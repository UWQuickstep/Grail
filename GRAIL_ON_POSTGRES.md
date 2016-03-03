The instructions here will allow you to get GRAIL running with PostgreSQL in 
a VirtualBox environment. If you already have PostgreSQL running, then you can
simply jump to [Step 4](Setup, compile and execute GRAIL) below. 

##1. Setup VirtualBox and Vagrant
You need to setup both VirtualBox and Vagrant. If you don't have these installed
already, then head over to https://www.virtualbox.org/wiki/Downloads and 
http://www.vagrantup.com/downloads to download and then install them.

##2. Clone Grail code from github
Go to the directory in your machine where you want to check out the Grail code. 
Then, clone the GRAIL code by typing the following command into a terminal window.

```shell
git clone https://github.com/UWQuickstep/Grail.git
```

##3. Setup and start the virtual machine
Next go to the `Grail/vagrant` directory. This directory has virtual machine
configurations for different operating systems (for now there is only one).
Pick the distro of your choice, and `cd` into that directory. For this document,
we will assume that you pick `ubuntu`. So, issue the following command:

```shell
cd Grail/vagrant/ubuntu
```

Next, start a virtual machine using the Vagrant file in that directory. From 
the terminal window, issue the following command:

```shell
vagrant up
```

The last command will take a while as Vagrant works with VirtualBox to fetch a
box image for Ubuntu. The box image is many hundred MiBs is size, so it takes a
while. This image is fetched only once and will be stored by Vagrant in a 
directory (likely `~/.vagrant.d/boxes/`), so you won't incur this network IO 
if you repeat the steps above. A side-effect is that Vagrant has now used a 
few hundred MiBs of space on your machine. You can see the list of boxes that 
Vagrant has downloaded using the command `vagrant box list`. If you need to drop
some box images, follow the instructions posted 
[here](https://www.vagrantup.com/docs/cli/box.html).

While viewing the `Vagrantfile`, a few more things to notice here are:

The parameter `vb.memory` sets the memory for the virtual machine. You could
dial that number up or down depending on the actual memory in your machine.
The parameter `vb.cpus` sets the number of cores that the virtual machine.
Again, feel free to change this number based on the machine that you have.
Notice the parameter `config.vm.synced_folder`. This configuration requests
that the code you checked out is mounted as `/Grail` in the virtual machine.
More on this later below.

Once the command above (`vagrant up`) returns, we are ready to start the
virtual machine. Type the following command into the terminal window (make sure
that you are in the directory `Grail/vagrant/ubuntu`):

```shell
vagrant ssh
```

Now you are in the virtual machine shell in a guest OS that is running in your
actual machine (the host). Everything that you do in the guest machine will be
isolated from the host, except for any changes that you make to `/Grail` 
- recall that we requested that the code we checked out show up at this mount
point in the virtual machine. Thus, if you type ls `/Grail` in the virtual
machine shell, then you will see the Grail code that you checked out.

##4. Setup, compile and execute GRAIL 
First lets change the Postgres user password. You can pick any passwore you
like. Here we simply use the password: `postgres`. This password might be
required in the future. Next, run the following command:

```shell
sudo -u postgres psql -c "ALTER USER postgres PASSWORD 'postgres';"
```

Now we have to execute the GRAIL Python script to generate the output. The
Python script has the following signature: 
`python Main.py -i <inputfile> -o <grail outputfile>`

As an example, to use this script to generate the code for the Single-Source
Shortest Path (SSSP) algorithm, run the following commands: 

```shell
cd /Grail
python Main.py -i ./analytics/sssp.grail -o output
``` 
If you check the file `output` it has the SQL code to computer SSSP.

(To generate the GRAIL SQL output for Weakly Connected Components or PageRank 
analyses, you can use `wcc.grail` or `pagerank.grail` in the above command 
instead of `sssp.grail`.)

Next, change the user to `postgres`, create the GRAIL database Grail, and
connect to it:

```shell
sudo -su postgres
createdb Grail
psql Grail
```

You should now be in the `psql` shell.

Recall from the [original GRAIL paper](http://pages.cs.wisc.edu/~jignesh/publ/Grail.pdf),
that GRAIL expects the graph data to be loaded in two tables: A `vertex` table
and an `edge` table. Let us create these tables. We can do that by using the
`\i` command. The general syntax for that is `\i <script-to-run>`. Let us create
the table using the following command (typed into the `psql` shell):

```shell
Example:
\i /Grail/datagen/sssp/create_and_load_edge.sql
\i /Grail/datagen/sssp/create_and_load_vertex.sql
```

Alternatively, to generated a random unweighted graph, you can type in:

```shell
\i /Grail/datagen/generate_weightedgraph_tables.sql 
```                   
You can also write your own script to feed in the required graph data into
the two tables `vertex` and `edge` before execution.

Now run the SQL script to execute the graph algorithm. Once again in the 
`psql` shell type in: 

```shell
\i /Grail/output
```

Some Additional Info:
  From within the psql prompt:
  
       \q : Exit psql shell
       \dt : View current tables from within psql shell
  exit: To change user back to root user (vagrant)
  

##5. Interpretation of Results and What to Expect

###Single Source Shortest Path

`next` table: This table contains the shortest distance to each of the nodes
from node 1 in each iteration. The final content of this table is the "solution"
to SSSP. 

`message` table: This table contains the vertex-distance data that is used in
each iteration. Since the `message` table could contain multiple rows for the
same vertex id, this table is further reduced to the `cur` table.

`cur` table: This table contains the most appropriate value for each vertex
computed based on an aggregate function (decided by the user and specified
in the config file). This information is obtained from the message table.

`toupdate` table:  The `cur` table is used to look for distances to each vertex
that is smaller than the current minimum distance (stored in the `next` table),
and the vertices to updated along with the new distance values are stored in
the `toupdate` table.

###Weakly Connected Components

In this algorithm, the value of a vertex during the execution (distance in the
case of single source shortest path) is defined as the minimal index of a vertex
in the connected subgraph that it belongs to. So if nodes 1, 2, 3, and 4 belong
to a subgraph, then the value of each of these nodes will be 1 (minimal index 
of a vertex in the subgraph). 

`next` table: Contains the minimal index of the vertex in the connected subgraph
to which it belongs to.

`message` table: Intermediate table which contains the potential minimal values
for each vertex, obtained from the `edge` and the previous `toupdate` table. 

`cur` table: Contains the most appropriate value for each vertex computed based
on an aggregate function (decided by the user and mentioned in the config). This
is obtained from the `message` table.

`toupdate` table:  The `cur` table is then used to look for values for each
vertex that are smaller than the current minimum value (stored in the `next`
table), and the vertices to updated along with the new values are stored in the 
`toupdate` table.

###Page Rank

This algorithm is based on `http://www.webworkshop.net/pagerank.html`

Each page is a vertex in the graph and links to pages are edges in the graph.

In this algorithm, the value associated with a vertex during the execution is
the pagerank. In each iteration, the pagerank of a page is modified based on
the contributions of the pagerank of incoming edges. So in effect, each edge
contributes a fraction of its influence to all outgoing edges. 

`next` table: This table contains the page rank of each page (which is 
represented by a vertex in our graph).

`out_cnts` table: Contains the number of distinct outgoing edges for each vertex.

`message` table: This is an intermediate table that stores the contribution of
each of the edges for a vertex based on the information in the `out_cnts`,
`edge` table and the current pagerank values (stored in the `cur` table). 
Stores the fraction ( (pagerank/out_cnts * 0.85) + 0.15 ) of the contribution
of each incoming edge.

`cur` table: This is an intermediate table that aggregates/accumulates the
values in the message table for each of the vertices.
