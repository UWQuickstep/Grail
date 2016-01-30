class Block(object):
    sb=""
    sql=""
    indentLevel=-1
    def __init__(self,stage="",indentLevel=-1,sql=""):
        '''
        constructor for the class; generates the sql statements for a generic block
        @param stage:stage of the code block
        @type stage:string
        @param indentLevel: indent level of the statements
        @type indentLevel: int
        @param sql: sql statements in the blocl
        @type sql: string
        '''
        self.stage=stage
        self.indentLevel=indentLevel
        self.sb=""
        if(sql!="" and indentLevel!=-1):
            self.append(sql)
            self.sql=self.sb
        elif(sql!="" and indentLevel==-1):
            self.sql=sql
    def concat(self,stat):
        '''
        concatenates string to the existing sql statement
        @param stat: string to concat
        @type stat: string
        '''
        self.sb+=stat
    def append(self,stat,indent=-1):
        '''
        appends string to the existing sql 
        @param stat: string to append
        @type stat: string
        @param indent: indent level current
        @type indent: int
        '''
        if(indent==-1):
            self.append(stat,self.indentLevel)
        else:
            line=""
            for i in range(0,indent):
                line+=" "
            line+=stat
            self.sb+=line 
            self.sb+="\n"           
    def getStage(self):
        '''
        returs the stage
        '''
        return self.stage
    def getSql(self):
        '''
        returns the sql statements
        '''
        self.sql=self.sb
        return self.sql
    def getIndentLevel(self):
        '''
        returns the indent level
        '''
        return self.indentLevel
    def setStage(self,stage):
        '''
        sets the stage
        @param stage: string containing the stage of the block
        @type stage: string
        '''
        self.stage=stage
    def setSql(self,sql):
        '''
        sets the sql into the block
        @param sql: string containing sql
        @type sql: string
        '''
        self.sql=sql
    def setIndentLevel(self,indent):
        '''
        sets the indent level
        @param indent: indent level
        @type indent: int
        '''
        self.indentLevel=indent
    def display(self):
        '''
        displays the sql statements
        '''
        if(self.sql==""):
            self.sql=self.sb
        print self.sql                    