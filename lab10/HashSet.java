import java.util.Arrays;

/** A set of non-null items of type T.  T must have a .hashCode method that is
 *  consistent with its .equals method: A.equals(B) implies
 *  A.hashCode() == B.hashCode().
 *  @author Kiet Lam
 */
public class HashSet<T> {

    /** Default initial size for item space. */
    private static final int DEFAULT_SIZE = 64;
    /** Minimum initial size for number of buckets. */
    private static final int DEFAULT_BUCKETS = 32;

    /** A new empty set that attempts to limit the time required for the .add,
     *  .remove, and .contains operations to O(LOADFACTOR).  LOADFACTOR > 0.
     *  Initially, configure the set to expect up to INITIALSIZE items,
     *  if positive. */
    public HashSet(double loadFactor, int initialSize) {
        if (loadFactor <= 0.0) {
            throw new IllegalArgumentException("bad load factor");
        }
        if (initialSize <= 0) {
            initialSize = DEFAULT_SIZE;
        }
        _loadFactor = loadFactor;
        alloc(initialSize);
        _size = 0;
    }

    /** Remove any item equal to (.equals) ITEM from me.  The storage
     *  used is released so that it can be reused without further allocation.
     *  Hint: the find and addFree methods are useful here.  */
    public void remove(T item) {
        int bucket = _items.length + hash(item);

        int link = find(item, bucket);
        if (_links[link] == -1) {
            return;
        } else {
            while (_links[link] != -1) {
                if (_items[_links[link]].equals(item)) {
                    int end = _links[_links[link]];
                    _links[link] = end;
                    _size -= 1;
                    break;
                }

                link = _links[link];
            }
        }
    }

    /** Returns the number of items currently in me. */
    public int size() {
        return _size;
    }

    /** Add ITEM to me, if not already present (according to .equals()).
     *  ITEM must not be null. */
    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException("attempt to add null value");
        }
        int bckt;
        bckt = _items.length + hash(item);
        if (_links[find(item, bckt)] != -1) {
            return;
        }
        if (_free == -1) {
            resizeItems();
            bckt = _items.length + hash(item);
        }
        int newItem = _free;
        _free = _links[_free];
        _items[newItem] = item;
        _links[newItem] = _links[bckt];
        _links[bckt] = newItem;
        _size += 1;
    }

    /** Return true iff a value equal to ITEM is in me. */
    public boolean contains(T item) {
        if (item == null) {
            return false;
        }
        return _links[find(item, _items.length + hash(item))] != -1;
    }

    /** Return the current hash value for ITEM. */
    private int hash(T item) {
        return item.hashCode() % (_links.length - _items.length);
    }

    /** Add item slots START .. END-1 to the free list. */
    private void addFree(int start, int end) {
        for (int i = start; i < end; i += 1) {
            _links[i] = _free;
            _free = i;
        }
    }

    /** Search the chain starting at _links[K] for ITEM, returning a
     *  value j such that _items[_links[j]] is ITEM if it is present
     *  in the chain, and _links[j] is -1 otherwise. Assumes ITEM != null. */
    private int find(T item, int k) {
        while (_links[k] != -1) {
            if (item.equals(_items[_links[k]])) {
                return k;
            }
            k = _links[k];
        }
        return k;
    }

    /** Allocate _items and _links arrays for a maximum of SIZE items,
     *  and an appropriate number of buckets (SIZE / _loadFactor()).
     *  Initializes the _links array and the _free pointer. */
    @SuppressWarnings("unchecked")
    private void alloc(int size) {
        int nbuckets =
            Math.max(DEFAULT_BUCKETS, (int) Math.ceil(size / _loadFactor));
        _items = (T[]) new Object[size];
        _links = new int[size + nbuckets];
        _free = -1;
        addFree(0, _items.length);
        Arrays.fill(_links, size, _links.length, -1);
    }

    /** Increase the size of the _items array, and add the new slots to the
     *  free list. Increase the number of buckets accordingly. Assumes the
     *  free list is empty. */
    private void resizeItems() {
        assert _free == -1;
        int N = _items.length;
        T[] oldItems = _items;
        alloc(N * 2);
        _size = 0;
        for (T item : oldItems) {
            add(item);
        }
    }

    /** The load factor (maximum of size / # buckets). */
    private double _loadFactor;
    /** Current number of items. */
    private int _size;
    /** Values of items in this set. */
    private T[] _items;
    /** The array of links in the external chains of values, including
     *  the buckets.  Let N be _items.length, and suppose that the
     *  hash value for a particular key is h.  Then the indices in
     *  _items of the keys that hash to h will be _links[N + h],
     *  _links[_links[N+h]], _links[_links[_links[N+h]]], etc. The last
     *  element of the chain will be marked with _link value of -1.
     *  Thus, the bucket h is empty if _links[h+N] == -1, and  _links[N],
     *  ..., _links[_links.length-1] are the heads of the buckets. */
    private int[] _links;
    /** Items in _link and _items that do not correspond to currently stored
     *  data are "free", and can be used to store additions to the table. All
     *  such items are linked into a single chain (through the _link array, as
     *  for chains of existing items), starting at _free.  _free==-1 indicates
     *  an empty free list. */
    private int _free;
}
