from Block import Block

class InsertUpdateBlock(Block):
    '''
    Generates SQL block for using INSERT to express UPDATE
    '''
    otherTable=""
    def __init__(self,updateBlock):
        '''
        
        @param updateBlock:Original UpdateVertexBlock
        @type updateBlock: Block
        '''
        super(InsertUpdateBlock,self).__init__(updateBlock.getStage(),updateBlock.getIndentLevel())
        self.otherTable=updateBlock.getOtherTable()
        self.append("INSERT INTO " + self.otherTable)
        self.append("SELECT *")
        self.append("FROM next")
        self.append("WHERE NOT EXISTS (")
        self.append("  SELECT * FROM " + self.otherTable)
        self.append("  WHERE " + self.otherTable + ".id = next.id)")
        self.append("DROP TABLE next;")
        self.append("ALTER TABLE "+ self.otherTable +" RENAME TO"+next+ ";")
        self.sql = self.sb