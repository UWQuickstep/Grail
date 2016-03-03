from FileCacheManager import FileCacheManager

class ConfigParser:
    '''
    Class that contains functions to constuct the config map from the config file
    '''
    configContent={}
    def __init__(self,fileName):
        '''
        constructor that generates the map of the config file
        @param fileName: file containing config
        @type fileName: string
        '''
        configFile=FileCacheManager(fileName)
        tempConfigContent={}
        #Now configFile.filecontent ccontains the file content
        for line in configFile.filecontent:
            lineSplit=line.split(":")
            if(len(lineSplit)==1):
                if("UpdateAndSend" in tempConfigContent):
                    tempConfigContent["UpdateAndSend"]=tempConfigContent["UpdateAndSend"]+"\n"+lineSplit[0]
                else:
                    tempConfigContent["UpdateAndSend"]=lineSplit[0]
            else:
                tempConfigContent[lineSplit[0]]=lineSplit[1]
        
        for k,v in tempConfigContent.iteritems():
            newk=k.strip()
            newv=v.strip()
            self.configContent[newk]=newv
            
                            
                      
                         


