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

//    public boolean insert(K key, V value) {
//        // Only rehash if the table is full.
//        // Rehash will double the table's size and purge deleted entries.
//        if (this.count == this.size) {
//            rehash();
//        }
//    }

    // Rehash will double the previous table size. It only re-inserts
    // active entries. Reset the count field so the new table
    // isn't full during rehash.
    private void rehash() {
        HashEntry<K, V>[] previousTable = this.table;
        int previousSize = this.size;

        this.size = previousSize * 2; // Double the size.
        this.table = new HashEntry[this.size];
        this.count = 0; // Reset the count back to 0.

        for (int i = 0; i < previousSize; i++) {
            HashEntry<K, V> entry = previousTable[i];

            // Entry must exist and be active.
            if (entry != null && !entry.isDeleted()) {
                insert(entry.getKey(), entry.getValue());
            }
        }
    }

    // Returns the hash value for the input key or -1 if not found.
    public int getHashValue(K key) {
        return getProbeIndex(key);
    }

    // Checks if the key exists in the table. If yes, return the value of
    // that key's entry or null if the key doesn't exist.
    public V find(K key) {
        int probeIndex = getProbeIndex(key);

        return probeIndex == -1 ? null : table[probeIndex].getValue();
    }

    // Performs lazy deletion by marking the entry as deleted.
    // Returns true if deleted, false if it's not found in the table.
    public boolean delete(K key) {
        int probeIndex = getProbeIndex(key);

        if (probeIndex == -1) {
            return false;
        }

        table[probeIndex].delete();

        return true;
    }

    // Helper function to do linear probing to get the probe index.
    private int getProbeIndex(K key) {
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

            i++;
        }

        // Not found.
        return -1;
    }

    // Calculates the base hash index for the input key.
    // This hash index will later be added to f(i).
    // E.g. hashfunction(key) = ((hash index) + f(i)) % |TableSize|
    // Integer key: uses the int value directly.
    // String key: sums the ASCII values of the chars and use that as hash.
    // Result is mapped to the table size.
    private int getBaseHashIndex(K key) {
        validateKey(key);

        int base;

        if (key instanceof Integer) {
            base = (Integer) key;
        }
        // The key, after being validated, must be a String if
        // it's not an int.
        else {
            base = 0;
            String keyString = (String) key;

            for (int i = 0; i < keyString.length(); i++) {
                base += keyString.charAt(i);
            }
        }

        // Converts the input key into a valid table index so it'll always be in bounds.
        // E.g. Default table size is 3. Base of 42 % 3 = 0, base of 530 % 3 = 2, etc.
        return base % this.size;
    }

    // Input key must exist and must be either a string or int.
    private void validateKey(K key) {
        if (key == null || (!(key instanceof String) && !(key instanceof Integer))) {
            throw new IllegalArgumentException("Key must be not be null and it must be String or Integer.");
        }
    }

    // Input value must exist.
    private void validateValue(V value) {
        if (value == null) {
            throw new IllegalArgumentException("Value must not be null.");
        }
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
