class TbNameGen:
    '''
    Class that's used to generate the next available table name. Starts from Table1, Table2 and so on
    '''
    curIdx=0  #static variable
    def getNextTbName(self):
        '''
        returns the next available table name
        '''
        TbNameGen.curIdx=TbNameGen.curIdx+1
        return "Table"+ str(TbNameGen.curIdx-1);
    
    