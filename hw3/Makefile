# All Java sources.  If you add more files, be sure to add them here as
# well.
SRCS = IntUnaryFunction.java TrReader.java Translate.java WeirdList.java User.java Tests.java

CLASSES = $(SRCS:.java=.class)

JFLAGS = -g

.PHONY: default style clean

default: $(CLASSES)

style:
	style61b $(SRCS)

clean:
	$(RM) *.class *~

$(CLASSES): $(SRCS)
	javac $(JFLAGS) $(SRCS)

check: $(CLASSES)
	java Tests
