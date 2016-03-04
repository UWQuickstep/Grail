# Makefile for GRAIL 

ANALYTICS_DIR_IN = analytics
ANALYTICS_DIR_OUT = analytics

GRAIL_OUTPUT = 	$(ANALYTICS_DIR_OUT)/sssp.sql \
		$(ANALYTICS_DIR_OUT)/pagerank.sql \
		$(ANALYTICS_DIR_OUT)/wcc.sql

GRAIL_INPUT = 	$(ANALYTICS_DIR_IN)/sssp.sql \
		$(ANALYTICS_DIR_IN)/pagerank.sql \
		$(ANALYTICS_DIR_IN)/wcc.sql

$DEPS =	CommonDefs.py Grail.py TbNameGen.py ConfigParser.py Main.py \
	Translator.py FileCacheManager.py Optimizer.py \
	Blocks/BeginWhileBlock.py Blocks/FlowControlBlock.py \
	Blocks/Block.py Blocks/InsertBlock.py \
	Blocks/CombineMessageBlock.py Blocks/InsertUpdateBlock.py \
	Blocks/CreateTableBlock.py Blocks/SelectIntoBlock.py \
	Blocks/DeclareBlock.py Blocks/SelectIntoExtended.py \
	Blocks/DropIndexBlock.py Blocks/UpdateVertexBlock.py \
	Blocks/DropTableBlock.py Blocks/__init__.py \
	Blocks/EndWhileBlock.py

%.sql: %.grail $(DEPS)
	@echo Generating GRAIL SQL for $@ from $<
	python Main.py -i $< -o $@

gen: $(GRAIL_OUTPUT)

clean:
	/bin/rm -f $(GRAIL_OUTPUT) *.pyc Blocks/*.pyc
