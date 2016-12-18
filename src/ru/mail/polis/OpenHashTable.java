package ru.mail.polis;

import java.util.Arrays;

//TODO: write code here
public class OpenHashTable<E extends Comparable<E>> implements ISet<E> {

    class Deleted extends Object{
        @Override
        public String toString() {
            return "_deleted";
        }
    }
    private final Deleted deleted = new Deleted();
    private Object hashTable[];
    private int tableSize;

    public OpenHashTable() {
        this.hashTable = new Object[8];
        this.tableSize = 0;
    }

    @Override
    public int size() {
        return tableSize;
    }
    
    private int hash1(int key) {
        return key%hashTable.length;
    }

    /**
     * Вторая хэш функция для реализации двойного хеширования
     * @param key
     * @return всегда возвращает целое нечетное число, т.к. размер таблицы всегда является степенью двойки
     */
    private int hash2(int key) {
        int h2 = 1 + (key & 7);
        if(h2%2 == 0) h2++;
        return h2;
    }
    
    public boolean loadFactor() {
        float load = (float)tableSize/(float)hashTable.length;
        if(load>0.5)
            return true;
        return false;
    }

    private void reHash() {
        int newSize = hashTable.length*2;
        Object old[] = Arrays.copyOf(hashTable, hashTable.length);
        hashTable = new Object[newSize];
        tableSize = 0;
        for(int i=0; i<old.length; i++)
            if(old[i] != null && !(old[i].equals(deleted))) {
                add((E)old[i]);
            }

    }

    @Override
    public boolean isEmpty() {
        if(size() == 0)
            return true;
        return false;
    }

    @Override
    public boolean contains(E value) {
        int key = value.hashCode();
        int h = hash1(key);
        if(hashTable[h]==null)
            return false;
        if(hashTable[h].equals(value))
            return true;
        int h2 = hash2(key);
        for(int prob = 1; prob<hashTable.length; prob++) {
            int idx = h + h2*prob;
            idx %= hashTable.length;
            if(hashTable[idx] == null)
                return false;
            if(hashTable[idx].equals(value))
                return true;
        }
        return false;
    }

    @Override
    public boolean add(E value) {
        if(value.equals(deleted))
            return false;
        int key = value.hashCode();
        int prob;
        int h = hash1(key);
        if (insert(value, h)) {
            tableSize++;
            if(loadFactor())
                reHash();
            return true;
        }
        else {
            int h2 = hash2(key);
            for(prob = 1; prob<hashTable.length; prob++) {
                int idx = h + h2*prob;
                idx %= hashTable.length;
                if(insert(value, idx)) {
                    tableSize++;
                    if(loadFactor())
                        reHash();
                    return true;
                }
            }
            return false;
        }
    }

    private boolean insert(E value, int idx) {
        if(idx < 0 || idx > hashTable.length)
            return false;
        if(hashTable[idx] == null || hashTable[idx].equals(deleted)) {
            hashTable[idx] = value;
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(E value) {
        int key = value.hashCode();
        int h = hash1(key);
        if(hashTable[h]==null)
            return false;
        if(hashTable[h].equals(value)) {
            tableSize--;
            hashTable[h] = deleted;
            return true;
        }
        int h2 = hash2(key);
        for(int prob = 1; prob<hashTable.length; prob++) {
            int idx = h + h2*prob;
            idx %= hashTable.length;
            if(hashTable[idx] == null)
                return false;
            if(hashTable[idx].equals(value)) {
                tableSize--;
                hashTable[idx] = deleted;
                return true;
            }
        }
        return false;
    }

    public void print() {
        for(int i=0; i<hashTable.length; i++)
            System.out.printf(hashTable[i]+" ");
        System.out.println();
    }
}
