from Block import Block

class DropIndexBlock(Block):
    '''
    generates sql to drop an index on a table
    '''
    
    def __init__(self,stage,indent,index,tbName):
        '''
        constructor: 
        @param stage: stage of the block 
        @type stage: string
        @param indent: indent level
        @type indent: int
        @param index: index to drop
        @type index: string
        @param tbName: table name 
        @type tbName: string
        '''
        super(DropIndexBlock,self).__init__(stage,indent)
        self.append("DROP INDEX IF EXISTS " + str(index) + ";")
        self.sql=self.sb