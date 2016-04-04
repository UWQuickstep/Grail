import sys,getopt

def fileToTuples(file, delimiter):
    f1 = open(file,"r")
    data1 = [] #list of tuples from f1
    for line in f1.readlines():
        tokens = line.split(delimiter)
        tuple = ""
        for token in tokens:
            tuple = tuple + token.strip()
        data1.append(tuple)
    f1.close()
    return data1


def main(argv):
    '''Main function that does the actual work your description goes in here.
    Args:
    infile1 and infile2 are the two files to be compared for value similarity with default delimiter of "|"
    Returns:
    0 if the two files match else 1
    The code assumes similar listing of attributes in the two files
    '''
    infile1=""
    infile2=""
    delimiter="|"

    try:
        opts, args = getopt.getopt(argv,"hf:F:",["infile1=","infile2="])
    except getopt.GetoptError:
        print 'test.py -f <inputfile1> -F <inputfile2>'
        sys.exit(2)

    for opt, arg in opts:
        if opt == '-h':
            print 'test.py -f <inputfile1> -F <inputfile2>'
            sys.exit()
        elif opt in ("-f", "--infile1"):
            infile1 = arg
        elif opt in ("-F", "--infile2"):
            infile2 = arg


    data1 = fileToTuples(infile1,delimiter)
    data2 = fileToTuples(infile2,delimiter)
    if len(data1) != len(data2):
        return 1
    else:
        for i,val in enumerate(data1):
            if(data1[i] != data2[i]):
                return 1
        return 0

if __name__ == "__main__":
    rc = main(sys.argv[1:])
    if rc > 0:
        print 'Input files are different'
        sys.exit(1)
    else:
        print 'Input files are similar'
        sys.exit(0)

