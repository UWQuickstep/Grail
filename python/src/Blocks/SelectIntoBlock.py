from Block import Block
class SelectIntoBlock(Block):
    '''
    Generates the Select statements that union/intersect 2 sql queries
    '''
    
    def __init__(self,stage,indent,attrs,targetTb,lhs,rhs,op):
        '''
        constructor: constructs that generates sql statement like union/intersect
        @param stage: stage of the block
        @type stage: current stage
        @param indent: indent level
        @type indent: int
        @param attrs: attributes list to select into
        @type attrs: list
        @param targetTb: target table name
        @type targetTb: string
        @param lhs: sql to generate  the left table
        @type lhs: string
        @param rhs: sql to generate the right table
        @type rhs: string
        @param op: operation on the 2 tables
        @type op: string
        '''
        super(SelectIntoBlock,self).__init__(stage,indent)
        line=""
        if (targetTb != ""):
            line+="DROP TABLE IF EXISTS "+targetTb+";"
            line+="\n CREATE TABLE "+targetTb+" AS"
        line+="\nSELECT "
        for i in range(0,len(attrs)-1):
            line=line+attrs[i]+", "
        line+=attrs[len(attrs)-1]
        self.append(line)
        self.append("FROM (");
        templhs=lhs.getSql()
        templhs=templhs[0:templhs.rfind(";")]
        temprhs=rhs.getSql()
        temprhs=temprhs[0:temprhs.rfind(";")]
        self.concat(templhs);
        self.append(op, indent + 1);
        self.concat(temprhs);
        self.append(") AS Temp");
        self.sql = self.sb