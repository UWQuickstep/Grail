from Grail import Grail


import sys, getopt

def main(argv):
    inputfile = 'config.grail'
    outputfile = 'output'

    grail=Grail(inputfile)
    grail.run()
    f=open(outputfile,'w')
    outputString=""
    for block in grail.getBlocks():
        ret=block.getBlockSql()
        outputString+=ret+"\n"

    f.write(outputString)
    print "Successflly completed Grail execution. Please check the ouput file for results.\n"

if __name__ == "__main__":
    main(sys.argv[1:])
