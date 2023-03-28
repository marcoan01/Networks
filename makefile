JFLAGS = -g
JC = javac

.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
Calc.java \
Server.java \
Client.java \
Client_2.java \
Client_3.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class


