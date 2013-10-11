# Makefile for Lab #3

#    gmake	      # Defaults to the first target: default
#    gmake default    # Same as gmake compile.
#    gmake compile    # Compile all sources
#    gmake clean      # Remove extraneous or rebuildable files
#    gmake style      # Run style checks.
#    gmake time       # Time Dups1, Dups2.

# Define $(SRCS) to be a list of Java files.
SRCS = Dups1.java Dups2.java Natural.java

MAIN_CLASSES = $(SRCS:.java=.class)

# Compiler flags for Java compilation

JFLAGS = -g

# The targets following .PHONY aren't actually files; they serve as commands.
.PHONY: compile default style time clean

# By default, compile all sources
default: compile

compile: $(MAIN_CLASSES)

style:
	style61b $(SRCS)

time: Dups1.class Dups2.class
	@echo "No iterators, linked:"
	time java Dups1 linked < some-words.txt
	@echo "======="
	@echo "No iterators, arrays:"
	time java Dups1 arrays < some-words.txt
	@echo "======="
	@echo "Iterators, linked:"
	time java Dups2 linked < some-words.txt
	@echo "======="
	@echo "Iterators, arrays:"
	time java Dups2 arrays < some-words.txt

# Remove extraneous or reconstructable files.
clean:
	rm -f *.class *~

$(MAIN_CLASSES): $(SRCS)
	javac $(JFLAGS) $(SRCS)

