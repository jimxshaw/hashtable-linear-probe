public class HashTableLinearProbe<K, V> {
    private static final int DEFAULT_SIZE = 3;

    private HashEntry<K, V>[] table;
    private int size; // Total number of slots (array length).
    private int count; // Count of active, non-deleted entries.

    @SuppressWarnings("unchecked")
    public HashTableLinearProbe() {
        this.size = DEFAULT_SIZE;
        this.table = (HashEntry<K, V>[]) new HashEntry[this.size];
        this.count = 0;
    }

    @SuppressWarnings("unchecked")
    public  HashTableLinearProbe(int customSize) {
        this.size = customSize;
        this.table = (HashEntry<K, V>[]) new HashEntry[this.size];
        this.count = 0;
    }

    public int getCount() {
        return this.count;
    }

    // Returns the hash value for the input key or -1 if not found.
    public int getHashValue(K key) {
        // hashfunction(key) = ((base index) + f(i)) % |TableSize|
        // f(i) = i, from i = 0 ... i = |TableSize|
        int baseIndex = getBaseHashIndex(key);
        int i = 0;

        // Linear probes through the table up to the
        // size limit to find the key.
        while (i < this.size) {
            int probeIndex = (baseIndex + i) % this.size;

            HashEntry<K, V> entry = table[probeIndex];

            if (entry == null) {
                return -1;
            }
            // Entry must exist and its key must match input key.
            else if (!entry.isDeleted() && entry.getKey().equals(key)) {
                return probeIndex;
            }

            // Increment i during linear probing to resolve collisions.
            i++;
        }

        // Not found.
        return -1;
    }

    // Checks if the key exists in the table. If yes, return the value of
    // the key or null if the key doesn't exist.
    public V find(K key) {
        // hashfunction(key) = ((base index) + f(i)) % |TableSize|
        // f(i) = i, from i = 0 ... i = |TableSize|
        int baseIndex = getBaseHashIndex(key);
        int i = 0;

        // Linear probes through the table up to the
        // size limit to find the key's value.
        while (i < this.size) {
            int probeIndex = (baseIndex + i) % this.size;

            HashEntry<K, V> entry = table[probeIndex];

            if (entry == null) {
                return null;
            }
            // Entry must exist and its key must match input key.
            else if (!entry.isDeleted() && entry.getKey().equals(key)) {
                return entry.getValue();
            }

            // Increment i during linear probing to resolve collisions.
            i++;
        }

        // Found nothing after probing the entire table.
        return null;
    }

    // Calculates the base hash index for the input key.
    // This hash index will later be added to f(i).
    // E.g. hashfunction(key) = ((hash index) + f(i)) % |TableSize|
    // Integer key: uses the int value directly.
    // String key: sums the ASCII values of the chars and use that as hash.
    // Result is mapped to the table size.
    private int getBaseHashIndex(K key) {
        int base;

        if (key instanceof Integer) {
            base = (Integer) key;
        }
        else if (key instanceof String keyString) {
            base = 0;

            for (int i = 0; i < keyString.length(); i++) {
                base += keyString.charAt(i);
            }
        }
        else {
            throw new IllegalArgumentException("Key must be either String or Integer.");
        }

        // Converts the input key into a valid table index so it'll always be in bounds.
        // E.g. Default table size is 3. Base of 42 % 3 = 0, base of 530 % 3 = 2, etc.
        return base % this.size;
    }

    public int getSize() {
        return this.size;
    }

    private static class HashEntry<K, V> {
        private K key;
        private V value;
        private boolean isDeleted;

        public HashEntry(K key, V value) {
            this.key = key;
            this.value = value;
            this.isDeleted = false;
        }

        public K getKey() {
            return this.key;
        }

        public V getValue() {
            return this.value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public boolean isDeleted() {
            return this.isDeleted;
        }

        public void delete() {
            this.isDeleted = true;
        }
    }
}
