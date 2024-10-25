JFLAGS=-g
JC=javac
JVM=java

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES=TP_1.TpMobile.java TP_1.UnMobile.java TP_1.UneFenetre.java

MAIN=TP_1.TpMobile

default:classes

classes: $(CLASSES:.java=.class)

run:$(MAIN).class
	$(JVM) $(MAIN)

clean:
	rm *.class