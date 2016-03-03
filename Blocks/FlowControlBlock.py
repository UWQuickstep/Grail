from Block import Block
class FlowControlBlock(Block):
    '''
    generates sql for the if..then..else block
    '''
    
    def __init__(self,stage,indent,flowControl,lhs,rhs):
        '''
        generates sql statement for if block
        @param stage: stage of the block
        @type stage: string
        @param indent: indent level
        @type indent: int
        @param flowControl: condition of the if statement
        @type flowControl: string
        @param lhs: sql to be executed if condition is true
        @type lhs: string
        @param rhs: sql to be executed if condition is false
        @type rhs: string
        '''
        super(FlowControlBlock,self).__init__(stage,indent)
        self.append("IF (" + flowControl + ")")
        self.append("THEN")
        self.concat(lhs)
        self.append("ELSE")
        self.concat(rhs)
        self.append("END IF;")
        self.sql = self.sb
        