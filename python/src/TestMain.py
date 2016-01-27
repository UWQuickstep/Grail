#import CommonDefs
#from .Block import Block
'''
from BeginWhileBlock import BeginWhileBlock
from CombineMessageBlock import CombineMessageBlock
from CreateTableBlock import CreateTableBlock
from DropIndexBlock import DropIndexBlock
from DropTableBlock import DropTableBlock
from EndWhileBlock import EndWhileBlock
from FlowControlBlock import FlowControlBlock
from InsertBlock import InsertBlock
from UpdateVertexBlock import UpdateVertexBlock
from InsertUpdateBlock import InsertUpdateBlock
from SelectIntoExtended import SelectIntoExtended
from SelectIntoBlock import SelectIntoBlock
'''

#from FileCacheManager import FileCacheManager
#from CommonDefs import CommonDefs
#from ConfigParser import ConfigParser
#file=FileCacheManager("Config.txt")
#configFile=ConfigParser("Config.txt")
#print configFile.configContent
#print file.filecontent
#print CommonDefs.INT_MAX

#statement=BeginWhileBlock("BeginWhile",1,"(ITER,10)")
#print statement.display()

#statement=CombineMessageBlock("CombineMsg",1,"Min(message.val)*2")
#print statement.display()

#attrs=["id int","val "+"INT"]
#statement=CreateTableBlock("createMsg",1,"message",attrs)
#print statement.display()


#statement=DropIndexBlock("initdropsrcindex", 1, "idx_src", "edge")
#print statement.display()


#statement=DropTableBlock("initdropnext", 1, "next")
#print statement.display()

 
#statement=EndWhileBlock("endwhile", 1, "(ITER,10)", "message" ) 
#statement.display()    


#sendMsg="SELECT id, MIN(val) * 2 as val \n  INTO cur\n  FROM (\n    SELECT src AS id, next.val/out_cnts AS val\n    FROM next, edge, out_cnts\n    WHERE edge.dest = next.id  AND out_cnts.id = next.id\n    \n    union all\n    SELECT dest AS id, next.val/out_cnts AS val\n    FROM next, edge, out_cnts\n    WHERE edge.src = next.id  AND out_cnts.id = next.id\n    \n  )s\n  GROUP BY id\n  ;\n"    
#combineMsg="SELECT message.id AS id, MIN(message.val) * 2 AS val\n  INTO cur\n  FROM message\n  GROUP BY id\n"
#sql="SET @isFirst = 0\n"
#statement=FlowControlBlock("flowControl",1,"@isFirst = 1",combineMsg + sql,sendMsg)    
#statement.display()     


#attrs=[1,2]
#attrs[1]="HELLO"
#statement= InsertBlock("initMsg",1,"*, " + "CAST(" + attrs[1] + " as " + "INT"+ ")","message","vertex")     
#statement.display()   

#statement=UpdateVertexBlock("setVal",1,"cur","cur.val")
#statement.display()
 

#statement=InsertUpdateBlock(UpdateVertexBlock("setVal",1,"cur","cur.val"))
#statement.display()

#statement=SelectIntoExtended("genCnt",0,["src AS ID","message as ID"],"out_cnts",["edge","hello"],"",False,"src")     
#statement.display()  

#statement=SelectIntoBlock("genCnt",0,["src AS ID","message as ID"],"out_cnts",["edge","hello"],"",False,"src")     
#statement.display()


#statement1=SelectIntoExtended("genCnt",0,["src AS ID","message as ID"],"out_cnts",["edge","Firsthello"],"",False,"src")      
#statement2=SelectIntoExtended("genCnt",0,["src AS ID","message as ID"],"out_cnts",["edge","Seconfllo"],"",False,"src")    
#statement=SelectIntoBlock("sendMsg",0,["*"],"message",statement1,statement2,"union all")
#statement.display()