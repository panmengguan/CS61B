echo Set 1
java -ea make.Main -o output -e error -f make-tests/comment.mk -D make-tests/comment A1; cat output; cat error
echo Set 2
java -ea make.Main -o output -e error -f make-tests/comment.mk -D make-tests/comment A2; cat output; cat error
