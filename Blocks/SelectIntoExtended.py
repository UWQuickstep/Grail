from Block import Block  
class SelectIntoExtended(Block):
    '''
    The SQL block for select into statement. Typically, the form is
    SELECT a AS temp1, b AS temp2, c....
    FROM var1,var2
    WHERE <condition>
    '''
    def __init__(self,stage,indent,attrs,targetTb,fromList,pred,joinWithId,groupBy):
        '''
        constructor
        @param stage: stage of the  block
        @type stage: string
        @param indent: indent level
        @type indent: int
        @param attrs: attribute list to project
        @type attrs: list
        @param targetTb: target table name (select into <>)
        @type targetTb: string
        @param fromList: list of tables to select from
        @type fromList: list
        @param pred: conditions/predicates used for selection
        @type pred: string
        @param joinWithId: indicates whether to join with the common vertex id or not
        @type joinWithId: boolean/int
        @param groupBy: attribute on which we should group result on
        @type groupBy: string
        '''
        super(SelectIntoExtended,self).__init__(stage,indent)
        line=""
        if (targetTb != ""):
            line+="DROP TABLE IF EXISTS "+targetTb+";"
            line+="\n CREATE TABLE "+targetTb+" AS"
        line+="\nSELECT "
        for i in range(0,len(attrs)-1):
            line=line+attrs[i]+", "
        line=line+attrs[len(attrs)-1]
        self.append(line)
        line=""
        for i in range(0,len(fromList)-1):
            line=line+fromList[i]+", "
        line+=fromList[len(fromList)-1]
      
        self.append("FROM " + line)
        line=""
        
        if (joinWithId):
            line+="WHERE "
            for i in range(0,len(fromList)-2):
                if(fromList[i]=="edge" or fromList[i+1]=="edge"):
                    continue
            
            	if(i==0):
                	line=line+fromList[i]+".id = "+fromList[i+1]+".id "
            	else:
                	line=line+"AND " + fromList[i] + ".id = " + fromList.get[i+1]+ ".id "              
            
            if(len(fromList)>2 and fromList[len(fromList)-2]!="edge" and fromList[len(fromList)-1]=="edge"):
                if(len(fromList)==2):
                    line=line+fromList[len(fromList)-2]+ ".id = " + fromList[len(fromList) - 1] + ".id"
                else:
                    line=line+" AND " + fromList[len(fromList) - 2]+ ".id = " + fromList[len(fromList) - 1] + ".id"
                      
            if(pred != ""):
                if(len(line)==6):
                    line+=pred
                else:
                    line=line+" AND "+pred    
        else:
            if(pred != ""):
                line=line+"WHERE "+pred
        
        if(len(line)!=0):
            self.append(line)
                  
        if(groupBy != ""):
            self.append("GROUP BY "+groupBy)
        self.append(";")
        self.sql=self.sb
