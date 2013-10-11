#    make	      # Defaults to the first target: default
#    make default    # Same as make compile.
#    make compile    # Compiles Java code in this directory.
#    make style      # Runs style checks (only on instructional machines)
#    make check      # Compile if needed and test using HW1Test.
#    make clean      # Remove extraneous or rebuildable files

# Define $(SRCS) to be a list of Java files.
SRCS = Progs.java HW1Test.java IntList.java

STYLE_SUPPRESS = $(MASTERDIR)/testing-files/hw1-suppress.xml

# The targets following .PHONY aren't actually files; they serve as commands.
.PHONY: compile default check clean style

# By default, compile all sources
default: compile

# To compile all source files, just bring the file YearCheck.class up to date.
compile: HW1Test.class

# Run our style checks.
style:
	if [ -f hw1-suppress.xml ]; then \
	    style61b -s hw1-suppress.xml $(SRCS); \
	else \
	    style61b -s $(STYLE_SUPPRESS) $(SRCS); \
	fi

# Test the compiled program, after first making sure it is up-to-date.
check: HW1Test.class
	java HW1Test > tests.out
	diff -b tests.std tests.out

# Remove extraneous or reconstructable files.
clean:
	rm -f *.class *~

HW1Test.class: $(SRCS)
	javac -g $(SRCS)
