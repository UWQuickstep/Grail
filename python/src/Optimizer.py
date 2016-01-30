from Blocks.Block import Block
from Blocks.EndWhileBlock import EndWhileBlock 
from Blocks.FlowControlBlock import FlowControlBlock 
from Blocks.InsertUpdateBlock import InsertUpdateBlock 


class Optimizer:
    '''
    class that contains the functions that's used for optimizing grail
    '''
    options={}
    blocks=[]
    senders=[]
    senderIndex=0
    
    def __init__(self,opts,bls,sendlist):
        '''
        constructor for the optimizer class
        @param opts:parser generated options
        @type opts: dictionary
        @param bls: list of blocks
        @type bls: list of strings
        @param sendlist: sender list
        @type sendlist: list of strings
        '''
        self.options=opts
        self.blocks=bls
        self.senders=sendlist 
        if(len(self.senders)>0):
            self.senderIndex=0
        
    def createIdx(self):  # @DontTrace
        '''
        creating an index on the "edge" table depending on the direction of sending messages (src/dest)
        '''
        dirval=self.options["SendMsgDir"]
        for idx in range(0,len(self.blocks)):
            if(self.blocks[idx].getStage()=="beginWhile"):
                break
        if(dirval=="in"):
            self.blocks.insert(idx-1,Block("createIdx",0,self.getCreateIdxSQL("dest")))
        elif(dirval=="out"):
            self.blocks.insert(idx-1,Block("createIdx",0,self.getCreateIdxSQL("src")))   
        elif(dirval=="all"):
            self.blocks.insert(idx-1,Block("createIdx",0,self.getCreateIdxSQL("src")))
            self.blocks.insert(idx-1,Block("createIdx",0,self.getCreateIdxSQL("dest")))
            
    def getCreateIdxSQL(self,attr):
        '''
        returns the sql statement for index creation given the attribute (src/dest)
        @param attr: src/dest
        @type attr: string
        '''
        return "CREATE INDEX idx_" + attr + " ON edge(" + attr + ");"
     
    def removeFromList(self,lists,index):
        '''
        function to remove an index from list
        @param lists: any list
        @type lists: list
        @param index: index in the list to be removed
        @type index: int
        '''
        lists=lists[:index] + lists[index+1 :]
     
    def mergeSendCombineMsg(self):
        dropBlocks=[]
        for sender in self.senders:
            for i in range(0,len(self.blocks)):
                if(self.blocks[i].getStage()=="drop"+sender):
                    if(sender != "cur"):
                        dropBlocks.append(self.blocks[i])
                        self.removeFromList(self.blocks,i)
                    else:
                        self.blocks[i]=Block("renamecur",self.blocks[i].getIndentLevel(),"ALTER TABLE cur RENAME TO cur_alias;")
                        dropBlocks.append(Block("dropcuralias",self.blocks[i].getIndentLevel(),"DROP TABLE IF EXISTS cur_alias;"))   
                    break;   
            
    
        self.blocks.insert(0,Block("intdropcuralias", 0, "DROP TABLE IF EXISTS cur_alias;"))  
    
        combineMsgBlockIndex = 0
        for combineMsgBlockIndex in range(0,len(self.blocks)):
            if(self.blocks[combineMsgBlockIndex].getStage()=="combineMsg"):
                break;
        
        
        sendMsgBlockIndex = 0;
        combineMsg=self.blocks[combineMsgBlockIndex].getSql()
        indent=self.blocks[combineMsgBlockIndex].getIndentLevel()
        self.removeFromList(self.blocks,combineMsgBlockIndex)
             
        for sendMsgBlockIndex in range(0,len(self.blocks)):
            if(self.blocks[sendMsgBlockIndex].getStage()=="sendMsg"):
                break;
        self.removeFromList(self.blocks,sendMsgBlockIndex-1)
        sendMsg=self.blocks[sendMsgBlockIndex].getSql()
        sendMsgBlock=self.blocks[sendMsgBlockIndex]
       
        aggFunc=self.options["aggFunc"].replace("message.val",self.options["contentStr"])
        if(self.options["msgDir"]=="all"):
            sendMsgBlock.append("GROUP BY id")
            sendMsgBlock.append(";")
            sendMsg=sendMsgBlock.getSql()
            sendMsg = sendMsg.replace("SELECT *", "SELECT id, "+ self.options["aggFunc"].replace("message.val", "val") + " as val ")
            sendMsg = sendMsg.replace("GROUP BY src", "")
            sendMsg = sendMsg.replace("GROUP BY dest", "")
        else:
            sendMsg=sendMsg.replace(self.options["contentStr"],aggFunc)  
        self.blocks[sendMsgBlockIndex].setSql(sendMsg)    
        
        if(self.options["isSender"]=="all" and self.options["setValNewVal"]=="cur.val"):
            sendMsg=sendMsg.replace("cur","next")
        else:
            sendMsg=sendMsg.replace("cur","cur_alias") 
             
        sendMsg = sendMsg.replace("INTO message", "INTO cur")
        
        setIsFirstBlock = Block("setIsFirst", indent, "isFirst := 0;")
        for block in dropBlocks:
            sendMsg+=block.getSql()
        self.blocks.insert(combineMsgBlockIndex,FlowControlBlock("flowControl",
                                         indent,
                                         "isFirst = 1",
                                         combineMsg + setIsFirstBlock.getSql(),
                                         sendMsg))
        i=0
        for i in range(0,len(self.blocks)):
            if(self.blocks[i].getStage()=="dropmessage"):
                self.removeFromList(self.blocks,i)
        
        i=0
        for i in range(0,len(self.blocks)):
            if(self.blocks[i].getStage()=="declare"):
                self.blocks[i].setSql(self.blocks[i].getSql()+"\n"+"isFirst integer := 1;")
        i=0
        for i in range(0,len(self.blocks)):
            if(self.blocks[i].getStage()=="endWhile"): 
                endWhileBlock = self.blocks[i]
                self.blocks[i]=EndWhileBlock(endWhileBlock.getStage(),
                                         endWhileBlock.getIndentLevel(),
                                         endWhileBlock.getEndStr(),
                                         self.senders[self.senderIndex]) 
        self.senderIndex=self.senderIndex+1  #TODO 
    
   
    def allSender(self):
        '''
        Reduce update when all vertices are senders.
        '''
        block=self.blocks[0]
        idx=0
        if(self.options["isSender"]=="all" and self.options["setValNewVal"]=="cur.val"):
            for idx in range(0,len(self.blocks)):
                if(self.blocks[idx].getStage()=="setVal"):
                    block=self.blocks[idx]
                    break
            self.removeFromList(self.blocks,idx)
            self.blocks.insert(idx,InsertUpdateBlock(block))
            for idx in range(0,len(self.blocks)):
                if(self.blocks[idx].getStage()=="dropcur"):
                    self.removeFromList(self.blocks,idx)
                    break            
 
    def run(self):
        '''
        run the optimizer
        '''
        self.createIdx()
        self.allSender()
        self.mergeSendCombineMsg()
