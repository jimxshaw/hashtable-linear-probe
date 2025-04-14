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
