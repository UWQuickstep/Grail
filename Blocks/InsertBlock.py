from Block import Block
class InsertBlock(Block):
    '''
    generates sql for an insert block
    '''
    
    def __init__(self,stage,indent,attr,targetTb,fromTb,pred=""):
        '''
        constructor: generates insert into sql statements
        @param stage: stage of the block
        @type stage: string
        @param indent: indent level
        @type indent: int
        @param attr: attribute list to select from
        @type attr: list
        @param targetTb:table name of the target table
        @type targetTb: string
        @param fromTb: table name of the from table
        @type fromTb: string
        '''
        super(InsertBlock,self).__init__(stage,indent)
        self.append("INSERT INTO " + targetTb)
        self.append("(SELECT " + attr)
        self.append("FROM " + fromTb )
        if(len(pred) > 0):
            tail = "WHERE "+pred+" "
        tail = tail + ");"
        self.append(tail)
        self.sql = self.sb