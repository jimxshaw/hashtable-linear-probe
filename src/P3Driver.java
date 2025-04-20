public class P3Driver {
    public static void main(String[] args) {
        /* INTEGER KEY TABLE */
        System.out.println("\n===== INTEGER KEY TABLE =====");
        HashTableLinearProbe<Integer, String> hashTable = new HashTableLinearProbe<>();

        /* INSERT */
        System.out.println("**** TEST INSERT  ****");
        System.out.println("Insert 1: " + hashTable.insert(1, "One"));
        System.out.println("Insert 2: " + hashTable.insert(2, "Two"));
        System.out.println("Insert 3: " + hashTable.insert(3, "Three"));

        // Should be false.
        System.out.println("Insert duplicate 2: " + hashTable.insert(2, "Two"));

        try {
            System.out.println("Insert NULL key: ");

            // Should throw exception.
            hashTable.insert(null, "NULLKey");
        } catch (IllegalArgumentException e) {
            System.out.println("Exception caught: " + e.getMessage());
        }

        try {
            System.out.println("Insert NULL value: ");

            // Should throw exception.
            hashTable.insert(4, null);
        } catch (IllegalArgumentException e) {
            System.out.println("Exception caught: " + e.getMessage());
        }


        /* DELETE */
        System.out.println("\n**** TEST DELETE ****");

        // Should be true.
        System.out.println("Delete key 2: " + hashTable.delete(2));

        // False.
        System.out.println("Delete non-existent key 99: " + hashTable.delete(99));

        // False.
        System.out.println("Delete key 2 again: " + hashTable.delete(2));


        /* FIND */
        System.out.println("\n**** TEST FIND ****");

        // Should be "One".
        System.out.println("Find key 1: " + hashTable.find(1));

        // NULL.
        System.out.println("Find deleted key 2: " + hashTable.find(2));

        // NULL.
        System.out.println("Find non-existent key 99: " + hashTable.find(99));


        /* RE-USING DELETED SLOT*/
        System.out.println("\n**** TEST RE-USING DELETED SLOT ****");

        // Should re-use the slot.
        System.out.println("Insert key 2 again (was deleted): " + hashTable.insert(2, "TwoAgain"));

        // Should be "TwoAgain".
        System.out.println("Find key 2: " + hashTable.find(2));


        /* REHASH */
        System.out.println("\n**** TEST REHASH ****");
        System.out.println("Current size before rehash: " + hashTable.getSize());

        // Insert 4th item to trigger rehash (default size = 3).
        System.out.println("Insert key 4: " + hashTable.insert(4, "Four"));

        System.out.println("New size after rehash: " + hashTable.getSize());

        // Show keys still exist.
        // Should be "One".
        System.out.println("Find key 1: " + hashTable.find(1));

        // Should be "TwoAgain".
        System.out.println("Find key 2: " + hashTable.find(2));

        // Should be "Three"
        System.out.println("Find key 3: " + hashTable.find(3));

        // Check if key 2 moved.
        int afterIndex = hashTable.getHashValue(2);
        System.out.println("Index of key 2 after rehash: " + afterIndex);


        /* GET HASH VALUES */
        System.out.println("\n**** TEST GET HASH VALUES ****");

        System.out.println("Hash index of key 3: " + hashTable.getHashValue(3));

        // Should be -1.
        System.out.println("Hash index of key 99 (not inserted): " + hashTable.getHashValue(99));

        // Delete should return false.
        System.out.println("Hash index of deleted key 99: " + hashTable.delete(99));

        // Should give valid index.
        System.out.println("Hash index of deleted key 2 (re-inserted): " + hashTable.getHashValue(2));


        /* STRING KEY TABLE */
        System.out.println("\n===== STRING KEY TABLE =====");
        HashTableLinearProbe<String, Integer> stringTable = new HashTableLinearProbe<>();

        /* INSERT */
        System.out.println("**** TEST INSERT  ****");

        System.out.println("Insert 'apple': " + stringTable.insert("apple", 100));
        System.out.println("Insert 'banana': " + stringTable.insert("banana", 200));


        /* FIND */
        System.out.println("\n**** TEST FIND ****");

        // Should be 200.
        System.out.println("Find 'banana': " + stringTable.find("banana"));


        /* DELETE */
        System.out.println("\n**** TEST DELETE ****");

        // True.
        System.out.println("Delete 'banana': " + stringTable.delete("banana"));

        // NULL.
        System.out.println("Find 'banana' after delete: " + stringTable.find("banana"));


        /* RE-USING DELETED SLOT*/
        System.out.println("\n**** TEST RE-USING DELETED SLOT ****");

        // Should re-use the slot.
        System.out.println("Insert 'banana' again: " + stringTable.insert("banana", 250));

        // Should be 250.
        System.out.println("Find 'banana': " + stringTable.find("banana"));


        /* GET HASH VALUES */
        System.out.println("\n**** TEST GET HASH VALUES ****");

        // Should give valid index.
        System.out.println("Hash index of 'banana': " + stringTable.getHashValue("banana"));

    }
}