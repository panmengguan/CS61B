SRCS = Adder.java Nybbles.java Tests.java

# Flags to pass to Java compilations (include debugging info and report
# "unsafe" operations.
JFLAGS = -g -Xlint:unchecked

# The $(NAME:.java=.class) notation substitutes .java for trailing .class
# extensions.
CLASSES = $(SRCS:.java=.class)

# Tell make that these targets are not really files.
.PHONY: clean default style

# By default, recompile if any sources have changed since the last build.
# This will fail to recompile properly if you compile, then delete
# a .class file in the draw package, and then compile again.  You
# shouldn't do that, of course, but if it happens, just do a 'make clean'
# first and then 'gmake'
default: $(CLASSES)

$(CLASSES): $(SRCS)
	javac $(JFLAGS) $(SRCS)

# Check style of source files with style61b.
style:
	style61b $(SRCS)

# Remove files that are unnecessary or that can be regenerated with make.
clean :
	$(RM) *~ *.class

check: $(CLASSES)
	java Tests
