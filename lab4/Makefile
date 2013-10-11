# Makefile for Lab #4

#    gmake	      # Defaults to the first target: default
#    gmake default    # Same as gmake compile.
#    gmake compile    # Compile all sources
#    gmake clean      # Remove extraneous or rebuildable files
#    gmake style      # Run style checks.
#    gmake time       # Time Dups1, Dups2.

# Define $(SRCS) to be a list of Java files.
SRCS = Quiz1.java

MAIN_CLASSES = $(SRCS:.java=.class)

# Compiler flags for Java compilation

JFLAGS = -g

# The targets following .PHONY aren't actually files; they serve as commands.
.PHONY: compile default clean

# By default, compile all sources
default: compile

compile: $(MAIN_CLASSES)

style:
	style61b $(SRCS)

# Remove extraneous or reconstructable files.
clean:
	rm -f *.class *~

$(MAIN_CLASSES): $(SRCS)
	javac $(JFLAGS) $(SRCS)

