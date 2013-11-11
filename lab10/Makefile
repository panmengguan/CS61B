JFLAGS = -g -Xlint:unchecked

%.class: %.java
	javac $(JFLAGS) $<

JAVA_SRCS = Bitsort.java Quicksort.java HashSet.java HashSetTest.java

.PHONY: default style clean

default: $(JAVA_SRCS:.java=.class)

# Check style of source files with style61b.
style:
	style61b $(JAVA_SRCS)

clean:
	rm -f *.class *.o *~ csort
