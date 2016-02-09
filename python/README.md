**<h3>1. Setup VirtualBox and Vagrant</h3>**

You need to setup both VirtualBox and Vagrant. If you don't have these installed already, then head over to https://www.virtualbox.org/wiki/Downloads and http://www.vagrantup.com/downloads to download and then install them.

**<h3>2. Clone Grail code from github</h3>**

Go to the directory in your machine where you want to check out the Grail code, and clone the Grail DB code by typing the following command into a terminal window.

```shell
git clone https://github.com/UWQuickstep/Grail.git
```

**<h3>3. Setup and start the virtual machine</h3>**

Next go to the `Grail/vagrant` directory. This directory has virtual machine configurations for different operating systems (for now there is only one). Pick the distro of your choice, and cd to that directory. For this document, we will assume that you pick `ubuntu`. So, issue the following command:

```shell
cd Grail/vagrant/ubuntu
```

Next, let us start a virtual machine using the Vagrant file in that directory. From the terminal window, issue the following command:

```shell
vagrant up
```

The last command will take a while as Vagrant works with VirtualBox to fetch a box image for Ubuntu. The box image is many hundred MiBs is size, so it takes a while. This image is fetched only once and will be stored by vagrant in a directory (likely `~/.vagrant.d/boxes/`), so you won't incur this network IO if you repeat the steps above. A side-effect is that vagrant has now used a few hundred MiBs of space on your machine. You can see the list of boxes that vagrant has downloaded using vagrant box list. If you need to drop some box images, follow the instructions posted here.

While viewing the `Vagrantfile`, a few more things to notice here are:

The parameter `vb.memory` sets the memory for the virtual machine. You could dial that number up or down depending on the actual memory in your machine.
The parameter `vb.cpus` sets the number of cores that the virtual machine. Again, feel free to change this number based on the machine that you have.
Notice the parameter `config.vm.synced_folder`. This configuration requests that the code you checked out is mounted as `/Grail` in the virtual machine. More on this later below.
Once the command above (vagrant up) returns, we are ready to start the virtual machine. Type in the following command into the terminal window (make sure that you are in the directory `Grail/vagrant/ubuntu`):

```shell
vagrant ssh
```

Now you are in the virtual machine shell in a guest OS that is running in your actual machine (the host). Everything that you do in the guest machine will be isolated from the host, except for any changes that you make to `/Grail` - recall that we requested that the code we checked out show up at this mount point in the virtual machine. Thus, if you type ls `/Grail` in the virtual machine shell, then you will see the Grail code that you checked out.

**<h3>4. Setup, compile and execute Grail</h3>**

Change the postgres user password to "postgres". This password might be required in future.
Run the following command:

```shell
sudo -u postgres psql -c "ALTER USER postgres PASSWORD 'postgres';"
```

Now we have to execute the Grail Python script to generate the output. The Python script has the following signature: 
`python Main.py -i <inputfile> -o <grail outputfile>`

A sample execution of this script for the Single-Source Shortest Path (SSSP) algorithm is:

```shell
cd /Grail/python/src
python Main.py -i ../../configs/sssp.txt -o output
```

Next, change the user to `postgres`, create the Grail database Grail, and connect to it:

```shell
sudo -su postgres
createdb Grail
psql Grail
```

Recall from the [original Grail paper](http://pages.cs.wisc.edu/~jignesh/publ/Grail.pdf), that Grail expects

http://pages.cs.wisc.edu/~jignesh/publ/Grail.pdf.


Let us generate the `vertex` and `edge` tables. 

       7. \i <sql file to generate edge table>
       8. \i <sql file to generate vertex table>
     Example:
          \i /Grail/datagen/sssp/create_and_load_edge.sql
          \i /Grail/datagen/sssp/create_and_load_vertex.sql
                             OR
          \i /Grail/datagen/generate_weightedgraph_tables.sql 
                    

     

Now run the sql for the graph algorithm on the vertex and edge tables by executing the generated Grail output 

      9. \i <grail output file>
          Example: 
               \i /Grail/python/src/output

Some Additional Info:
  From within the psql prompt:
  
       \q : Exit psql shell
       \dt : View current tables from within psql shell
  exit: To change user back to root user (vagrant)







