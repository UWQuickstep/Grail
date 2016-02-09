from Block import Block
class CreateTableBlock(Block):
    '''
    generates the create table sql statement
    '''
    
    def __init__(self,stage,indent,tableName,attrs):
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
        super(CreateTableBlock,self).__init__(stage,indent)
        self.append("CREATE TABLE " + tableName + "(")
        ind=0
        for attr in attrs:
            if(ind==len(attrs)-1):
                self.append(attr,indent+1)
            else:    
                self.append(attr+",",indent+1)  
            ind=ind+1      
        self.append(")" + ";")   
        self.sql=self.sb 