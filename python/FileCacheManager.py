class FileCacheManager:
    filecontent=[]
    def __init__(self,fileName):
        '''
        constructor
        @param fileName: filename containing config
        @type fileName: parses the content and stores the lines in filecontent
        '''
        f=open(fileName,'r')
        for text in f.readlines():
            self.filecontent.append(text)
        
              
        
         
        