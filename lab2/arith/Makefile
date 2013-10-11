# Makefile for Lab #2: arith package

#    gmake	      # Defaults to the first target: default
#    gmake default    # Same as gmake compile.
#    gmake compile    # Same as gmake Rational.class
#    gmake check      # Compile if needed and test Rational
#    gmake clean      # Remove extraneous or rebuildable files
#    gmake Rational.class
#		      # Builds class files, if needed.

# Define $(SRCS) to be a list of Java files.
SRCS = Rational.java RationalTest.java

# Compiler flags for Java compilation.  

JFLAGS = -g

# The targets following .PHONY aren't actually files; they serve as commands.
.PHONY: compile default check clean

# By default, compile all sources
default: compile

# To compile all source files, just bring the file YearCheck.class up to date.
compile: $(SRCS:.java=.class)

# A general pattern for compiling Java files.  Set -sourcepath to the 
# next directory out, since we are in a package.
%.class: %.java
	javac $(JFLAGS) -sourcepath .. $<

style:
	cd ..; style61b arith/*.java

# Perform unit tests on Rational.
check: Rational.class RationalTest.class
	cd ..; java arith.RationalTest

# Remove extraneous or reconstructable files.
clean:
	rm -f *.class *~ 

