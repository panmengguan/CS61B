# All Java sources.  If you add more files, be sure to add them here as
# well.
SRCS = Tester.java HW4.java

CLASSES = $(SRCS:.java=.class)

JFLAGS = -g

.PHONY: default style clean check

default: $(CLASSES)

style:
	style61b $(SRCS)

clean:
	$(RM) *.class *~ */*~ OUT

# Check HW4 by running Tester (with time limit 4 seconds and output
# limit of 2048 characters) and checking against tests/output.std.
check: default
	ulimit -t 4; ulimit -f 4; java -ea Tester > OUT
	diff OUT tests/output.std

$(CLASSES): $(SRCS)
	javac $(JFLAGS) $(SRCS)
