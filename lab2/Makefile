# Makefile for Lab #2

#    gmake	      # Defaults to the first target: default
#    gmake default    # Same as gmake compile.
#    gmake compile    # Same as gmake Root1.class Root2.class
#    gmake -k check   # Compile if needed and Root1, Root2, and arith package.
#                     # -k tells gmake not to stop at the first error. Even if
#                     # the Root1, Root2 tests fail, still do the arith tests.
#    gmake clean      # Remove extraneous or rebuildable files
#    gmake Root1.class 
#    gmake Root2.class
#    gmake arith/Rational.class
#		      # Builds class files, if needed.

# Define $(SRCS) to be a list of Java files.
SRCS = Root1.java Root2.java arith/Rational.java arith/RationalTest.java

MAIN_CLASSES = Root1.class Root2.class arith/RationalTest.class

# Compiler flags for Java compilation

JFLAGS = -g

# The targets following .PHONY aren't actually files; they serve as commands.
.PHONY: compile default check clean check-local check-arith

# By default, compile all sources
default: compile

compile: $(MAIN_CLASSES)

style:
	style61b $(SRCS)

check: check-local check-arith

check-local: Root1.class Root2.class 
	sh -ve tests.cmd > tests.out
	diff -b tests.std tests.out

# $(MAKE) is the standard way to refer to "the make program" when doing 
# a "recursive make" such as the command line below. The "-C arith" 
# option means "switch to directory arith before running me".
check-arith:
	$(MAKE) -C arith check

# Remove extraneous or reconstructable files.
clean:
	rm -f *.class *~ tests.out
	$(MAKE) -C arith clean

# Root1.class and Root2.class depend on arith/Rational.class (as well as on
# their respective source files).
$(MAIN_CLASSES): $(SRCS)
	javac $(JFLAGS) $(SRCS)

