from Block import Block
class EndWhileBlock(Block):
    '''
    generates sql to end a while loop
    '''
    
    endStr=""
    
    def getEndStr(self):
        '''
        returns the ending string
        '''
        return self.endStr
    def __init__(self,stage,indent,endStr,counter):
        '''
        constructor: generates the sql for end while
        @param stage: stage of the block
        @type stage: string
        @param indent: indent level
        @type indent: int
        @param endStr: end string 
        @type endStr: string
        @param counter: table name thats used to decide the number of iterations
        @type counter: string
        '''
        super(EndWhileBlock,self).__init__(stage,indent)
        self.endStr=endStr
        if(self.endStr=="NO_MESSAGE"): 
            self.append("flag := (SELECT COUNT (*) FROM " + str(counter) + ");")
        else:
            self.append("flag := flag - 1;") 
        self.append("END LOOP;",indent-1)
        self.append("\nEND$$")
        self.sql=self.sb            