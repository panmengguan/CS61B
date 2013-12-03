JFLAGS = -g -Xlint:unchecked -Xlint:deprecation

SRCS = $(wildcard *.java)
CLASSES = $(SRCS:.java=.class)

.PHONY: default style clean

default: $(SRCS)
	$(RM) $(CLASSES)
	javac $(JFLAGS) $(SRCS) || { $(RM) $(CLASSES); false; }

# Check style of source files with style61b.
style:
	style61b $(STYLEFLAGS) $(SRCS)

clean:
	rm -f *.class *.o *~ 
