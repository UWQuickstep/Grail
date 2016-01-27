from Block import Block

class DeclareBlock(Block):
    '''
    Generates declaration statements
    '''
    def __init__(self,stage,indent, declareString):
        '''
        constructor
        @param stage: stage of the block
        @type stage: string
        @param indent: indent level
        @type indent: int
        @param declareString: declaration statements separated by \n
        @type declareString: string
        '''
        super(DeclareBlock,self).__init__(stage,indent)
        self.append("DO $$")
        self.append("DECLARE"+"\n")
        self.append(declareString)
        self.sql=self.sb 