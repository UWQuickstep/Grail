from Grail import Grail
grail=""
grail=Grail("/home/siddharth/workspace/GrailPython/src/Config.txt")
grail.run()
for block in grail.getBlocks():
    block.display()       