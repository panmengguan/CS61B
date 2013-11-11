/** Demonstration of heaps as priority queues.
 *  @author P. N. Hilfinger */
class HeapStuff {

    /** This main program is started like this:
     *
     *      java HeapStuff ARGS
     *
     *  where ARGS is X V0 V1 V2 V3 ...
     *
     *  It puts the strings Vi into a heap, and then prints out all values
     *  that are >= X (String comparison). */
    public static void main(String[] args) {
        Heap<String> H = new Heap<String>();
        for (int i = 1; i < args.length; i += 1) {
            H.add(args[i]);
        }
        H.printLarger(args[0]);
    }

}
