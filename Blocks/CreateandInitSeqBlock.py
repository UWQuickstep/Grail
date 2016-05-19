from Block import Block
class CreateandInitSeqBlock(Block):
    '''
    generates the create table sql statement
    '''
    
    def __init__(self,stage,indent,seq_tokens):
        '''
        constructor: generates a create table sql
        @param stage:current stage
        @type stage: string
        @param indent: indent level
        @type indent: int
        @param tableName: Name of table to create
        @type tableName: string
        @param attrs: attribute list to have in the table
        @type attrs: list
        '''
        
	super(CreateandInitSeqBlock,self).__init__(stage,indent)
	self.append("DROP SEQUENCE IF EXISTS "+ seq_tokens[0] + ";")
        self.append("CREATE SEQUENCE " + seq_tokens[0] + " MINVALUE "+seq_tokens[1] + ";")
        self.append("SELECT setval(" + seq_tokens[2]+ "," + seq_tokens[3] + ");")   
        self.sql=self.sb 

