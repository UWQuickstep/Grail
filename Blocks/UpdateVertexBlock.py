from Block import Block
class UpdateVertexBlock(Block):
    '''
    generates sql statements to update the value of the vertices
    '''
    
    otherTable=""
    valueExpression="" 
    def getValueExpression(self):
        '''
        returns the scalar function/value expression
        '''
        return self.valueExpression
    def getOtherTable(self):
        '''
        returns the name of the other table
        '''
        return self.otherTable
    def __init__(self,stage,indent,otherTable,valueExpression):
        '''
        constructor
        @param stage: stage of the block
        @type stage: string
        @param indent: indent level
        @type indent: int
        @param otherTable: name of the other table from which new values are retrieved
        @type otherTable: string
        @param valueExpression: expression or scalar function of the attribute in the other table
        @type valueExpression: string
        '''
        super(UpdateVertexBlock,self).__init__(stage,indent)
        self.otherTable = otherTable;
        self.valueExpression = valueExpression;
        self.append("UPDATE next SET val = " + valueExpression);
        self.append("FROM "+otherTable);
        self.append("WHERE next.id = " + otherTable + ".id" + ";");
        self.sql = self.sb
        