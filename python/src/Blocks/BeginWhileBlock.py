from Block import Block

class BeginWhileBlock(Block):
    '''
    Generates sql statements for a begin while block
    '''
    def __init__(self,stage,indent, loopCondition):
        '''
        constructor
        @param stage: current stage  
        @type stage: string 
        @param indent: indent level
        @type indent: int
        @param loopCondition: condition inside the while loop
        @type loopCondition: string
        '''
        super(BeginWhileBlock,self).__init__(stage,indent)
        self.append("BEGIN"+"\n")
        self.append("WHILE "+loopCondition+" LOOP")
        self.sql=self.sb 