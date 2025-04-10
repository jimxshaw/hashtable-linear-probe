public class HashTableLinearProbe<K, V> {
    private static final int DEFAULT_SIZE = 3;

    private HashEntry<K, V>[] table;
    private int size; // Total number of slots (array length).
    private int count; // Count of active, non-deleted entries.

    public HashTableLinearProbe() {
        this.size = DEFAULT_SIZE;
        this.table = new HashEntry[size];
        this.count = 0;
    }

    public  HashTableLinearProbe(int customSize) {
        this.size = customSize;
        this.table = new HashEntry[size];
        this.count = 0;
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
    }
}
