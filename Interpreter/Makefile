.RECIPEPREFIX= 
SOURCE_DIR = src
OUTPUT_DIR = classes
JAR_DIR = jars

FIND := find
MKDIR := mkdir -p
RM := rm -rf

JAVA = java
JAVAC = javac
JAR = jar

export PATH := $(JAVA_HOME):$(PATH)

JFLAGS = -sourcepath $(SOURCE_DIR) \
  -d $(OUTPUT_DIR)              

JARFLAGS = cfm  
  
class_path := OUTPUT_DIR                

export CLASSPATH := $(class_path)

.PHONY: all
all: compile jars

all_javas := $(OUTPUT_DIR)/all.javas

.PHONY: compile
compile: $(all_javas)
	$(JAVAC) $(JFLAGS) @$<
	
.INTERMEDIATE: $(all_javas)
$(all_javas):
	$(FIND) $(SOURCE_DIR) -name '*.java' >$@
	
.PHONY: jars
jars: 
	$(JAR) $(JARFLAGS) $(JAR_DIR)/Interporater.jar Manifest.mf -C $(OUTPUT_DIR) edu
	
.PHONY: clean
clean: $(RM) $(OUTPUT_DIR)

.PHONY: classpath
classpath: @echo CLASSPATH='$(CLASSPATH)'