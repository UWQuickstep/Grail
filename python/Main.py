from Grail import Grail
grail=""
grail=Grail("Config.txt")
grail.run()
for block in grail.getBlocks():
    block.display()      
