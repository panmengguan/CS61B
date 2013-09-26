/** Functions to sum and increment the elements of a WeirdList.
 *  @author Kiet Lam
 */
class User {
    /** Returns the result of adding N to each element of L. */
    static WeirdList add(WeirdList L, int n) {
        AddFunction func = new AddFunction(n);
        return L.map(func);
    }

    /** Returns the sum of the elements in L.*/
    static int sum(WeirdList L) {

        WhatAHackFunction hack = new WhatAHackFunction();

        L.map(hack);

        return hack.getCounter();
    }

    /** An add function.*/
    private static class AddFunction implements IntUnaryFunction {

        /** The number to add.*/
        private int _n;

        /** Constructs an add function with N.*/
        public AddFunction(int n) {
            _n = n;
        }

        /** Returns the X + _n.*/
        public int apply(int x) {
            return _n + x;
        }

        /** Returns the N.*/
        int getN() {
            return _n;
        }
    }

    /** Super hackish function.*/
    private static class WhatAHackFunction implements IntUnaryFunction {

        /** The counter.*/
        private int _counter = 0;

        /** Returns _counter + X.*/
        public int apply(int x) {
            _counter += x;
            return _counter;
        }

        /** Returns the current counter.*/
        int getCounter() {
            return _counter;
        }
    }
}
