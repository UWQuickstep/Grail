from Block import Block

class CombineMessageBlock(Block):
    '''
    Generates sql statement to perform an aggregate function on an attribute and insert into cur
    '''
    aggFunc=""
    def getAggFunc(self):
        return self.aggFunc
    def __init__(self,stage,indent,aggFunc):
        '''
        constructor: generates the sql 
        @param stage: stage of the block
        @type stage: string
        @param indent: indent level
        @type indent: int
        @param aggFunc: aggFunc to perform
        @type aggFunc: string
        '''
        super(CombineMessageBlock,self).__init__(stage,indent)
        self.aggFunc=aggFunc
        self.append("SELECT message.id as id, " + aggFunc + " as val")
        self.append("INTO cur")
        self.append("FROM message" + ";")
        self.sql=self.sb