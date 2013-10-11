# Makefile for Lab #1.

# This is a "makefile": a set of directions for performing various tasks 
# on a set of files. The make program (the GNU variant of the make program, 
# and called just 'make' on Linux and MacOS X systems) uses this file as
# its script.

# The command 'make TARGET' tells make to do what it takes to bring
# TARGET "up to date."  The usual commands are 

#    make	      # Defaults to the first target: default
#    make default    # Same as make compile.
#    make compile    # Same as make YearCheck.class
#    make style      # Use style61b for a style check.
#    make check      # Compile if needed and test YearCheck.
#    make clean      # Remove extraneous or rebuildable files
#    make YearCheck.class
#		      # Bring YearCheck.class up to date: that is, recompile
#                     # it if it does not exist or is older than any of 
#                     # the source (.java) files.


STYLE_FLAGS = -s lab1-suppress.xml

# Define $(SRCS) to be a list of Java files.
SRCS = YearCheck.java

# The targets following .PHONY aren't actually files; they serve as commands.
.PHONY: compile default check clean style

# By default, compile all sources
default: compile

# To compile all source files, just bring the file YearCheck.class up to date.
compile: YearCheck.class

# Check Java code against our required style.
style:
	style61b $(STYLE_FLAGS) $(SRCS)

# Test the compiled program, after first making sure it is up-to-date.
#check: YearCheck.class
#	java YearCheck 1900 1901 

# Alternative test procedure.  See the activities file for lab1, section 5.
check: YearCheck.class
	sh -ve tests.cmd > tests.out
	diff -b tests.std tests.out

# Remove extraneous or reconstructable files.
clean:
	rm -f *.class *~ tests.out

# YearCheck.class is up-to-date if it exists and is younger (was changed more
# recently than) all the files in $(SRCS).  If it isn't, use the 'javac' 
# command to build it.
YearCheck.class: $(SRCS)
	javac -g $(SRCS)

