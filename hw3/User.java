/** Functions to sum and increment the elements of a WeirdList. */
class User {
    /** Returns the result of adding N to each element of L. */
    static WeirdList add(WeirdList L, int n) {
        AddFunction func = new AddFunction(n);
        return L.map(func);
    }

    /** Returns the sum of the elements in L */
    static int sum(WeirdList L) {

        WhatAHackFunction hack = new WhatAHackFunction();

        L.map(hack);

        return hack._counter;
    }

    // FILL IN OTHER CLASSES USED BY HERE (HINT, HINT).
    // You MAY want to add some private nested classes (and methods) to
    // be used by User (start them off with 'private static class...'),
    // OR you may want to add more class files to those provided in the
    // skeleton.  In the latter case, be sure you 'svn add' them as well.

    private static class AddFunction implements IntUnaryFunction {

        int _n;

        public AddFunction(int n) {
            _n = n;
        }

        public int apply(int x) {
            return _n + x;
        }
    }

    private static class WhatAHackFunction implements IntUnaryFunction {

        int _counter = 0;

        public int apply(int x) {
            _counter += x;
            return _counter;
        }
    }
}
