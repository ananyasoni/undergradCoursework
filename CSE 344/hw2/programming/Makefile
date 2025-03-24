TARGET = HW2
JARS = lib/sqlite-jdbc-3.47.2.0.jar:lib/junit-platform-console-standalone-1.11.4.jar
SRC = *.java 
CLASS_DIR = bin
CLASSPATH = $(CLASS_DIR):$(JARS)

all: test

compile:
	mkdir -p $(CLASS_DIR)
	javac -d $(CLASS_DIR) -cp $(JARS) $(SRC)

test: compile
	java -jar lib/junit-platform-console-standalone-1.11.4.jar \
	    execute \
		--class-path "bin:lib/sqlite-jdbc-3.47.2.0.jar" \
		--scan-classpath 

clean:
	rm -rf $(CLASS_DIR)
