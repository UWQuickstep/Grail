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

    def update_next(self,col_dict,from_lst):
	stmt = "UPDATE next set "
	keys = col_dict.keys()
	print keys
    	for i in range(0,len(keys)-1):
		stmt+=keys[i]+"="+col_dict[keys[i]]+","

	stmt+=keys[len(keys)-1]+"="+col_dict[keys[len(keys)-1]]+"\n"
	
	#from table list
	stmt+="FROM "
	for i in range(0,len(from_lst)-1):
		stmt+= from_lst[i]+","
	stmt+=from_lst[len(from_lst)-1]
	stmt+="\n"

	stmt+="WHERE "	
	for i in range(0,len(from_lst)-1):
		stmt+= "next.id="+from_lst[i]+".id and "
	stmt+="next.id="+from_lst[len(from_lst)-1]+".id"

	print stmt
			

    def __init__(self,stage,indent,col_dict,from_lst):
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
	'''
        self.otherTable = otherTable;
        self.valueExpression = valueExpression;
        self.append("UPDATE next SET val = " + valueExpression);
        self.append("FROM "+otherTable);
        self.append("WHERE next.id = " + otherTable + ".id" + ";");
        self.sql = self.sb

  	self.update_next(col_dict,from_lst) 
	'''

	self.append("UPDATE next set ")
	keys = col_dict.keys()
	for i in range(0,len(keys)-1):
		self.append(keys[i]+"="+col_dict[keys[i]]+",")
	self.append(keys[len(keys)-1]+"="+col_dict[keys[len(keys)-1]])
	self.append("FROM ")
	for i in range(0,len(from_lst)-1):
		self.append(from_lst[i]+",")
	self.append(from_lst[len(from_lst)-1])
	self.append("WHERE ")
	for i in range(0,len(from_lst)-1):
		self.append("next.id="+from_lst[i]+".id and ")
	self.append("next.id="+from_lst[len(from_lst)-1]+".id;")
	self.sql = self.sb	
		
