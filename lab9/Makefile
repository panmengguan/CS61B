# Makefile for Lab #9

#    gmake	      # Defaults to the first target: default
#    gmake default    # Same as gmake compile.
#    gmake compile    # Compile all sources
#    gmake clean      # Remove extraneous or rebuildable files
#    gmake style      # Run style checks.
#    gmake time       # Time Dups1, Dups2.

SRCS = $(wildcard *.java)

CLASSES = $(SRCS:.java=.class)

# Compiler flags for Java compilation

JFLAGS = -g -Xlint:unchecked

# The targets following .PHONY aren't actually files; they serve as commands.
.PHONY: compile default style clean

# By default, compile all sources
default: compile

compile: $(CLASSES)

style:
	style61b $(SRCS)

# Remove extraneous or reconstructable files.
clean:
	rm -f *.class *~

$(CLASSES): $(SRCS)
	javac $(JFLAGS) $(SRCS)

