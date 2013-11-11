JFLAGS = -g -Xlint:unchecked

%.class: %.java
	javac $(JFLAGS) $<

JAVA_SRCS = BinaryTree.java HeapStuff.java Heap.java BinaryTreeTest.java

.PHONY: default style clean

default: $(JAVA_SRCS:.java=.class)

# Check style of source files with style61b.
style:
	style61b $(JAVA_SRCS)

clean:
	rm -f *.class *.o *~ csort
