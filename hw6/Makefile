JFLAGS = -g -Xlint:unchecked

%.class: %.java
	javac $(JFLAGS) $<

CC = gcc

CFLAGS = -O2 -g -Wall

JAVA_SRCS = Sorter.java Sorter2.java Sorter3.java BigMat.java BitMatrix.java BigMatTest.java

.PHONY: default style clean

default: csort $(JAVA_SRCS:.java=.class)

csort: csort.c
	$(CC) $(CFLAGS) -o $@ $<

# Check style of source files with style61b.
style:
	style61b $(JAVA_SRCS)

clean:
	rm -f *.class *.o *~ csort
