from Block import Block
class DropTableBlock(Block):
    '''
    generates sql to drop a table 
    '''
    
    def __init__(self,stage,indent,tbName):
        '''
        constructor: generates sql to drop table
        @param stage: stage of the block
        @type stage: string
        @param indent: indent level
        @type indent: int
        @param tbName: Name of table to drop
        @type tbName: string
        '''
        super(DropTableBlock,self).__init__(stage,indent)
        self.append("DROP TABLE IF EXISTS "+tbName+";")
        self.sql=self.sb