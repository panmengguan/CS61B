echo Set 1
java -ea make.Main -o output -e error -f make-tests/make02.mk -D make-tests/file02 A1; cat output
echo Set 2
java -ea make.Main -o output -e error -f make-tests/make02.mk -D make-tests/file02 A2; cat output
