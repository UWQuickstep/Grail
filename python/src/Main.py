from Grail import Grail
 
#!/usr/bin/python

import sys, getopt

def main(argv):
    inputfile = ''
    outputfile = ''
    print inputfile, outputfile
    try:
        opts, args = getopt.getopt(argv,"hi:o:",["ifile=","ofile="])
    except getopt.GetoptError:
        print 'test.py -i <inputfile> -o <outputfile>'
        sys.exit(2)
    for opt, arg in opts:
        if opt == '-h':
            print 'test.py -i <inputfile> -o <outputfile>'
            sys.exit()
        elif opt in ("-i", "--ifile"):
            inputfile = arg
        elif opt in ("-o", "--ofile"):
            outputfile = arg
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
