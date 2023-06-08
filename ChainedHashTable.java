import java.util.List;
import java.util.ListIterator;
import java.util.ArrayList;
import java.util.LinkedList;

public class ChainedHashTable<K, V> implements HashTable<K, V> {
    final static int DEFAULT_INIT_CAPACITY = 4;
    final static double DEFAULT_MAX_LOAD_FACTOR = 2;
    final private HashFactory<K> hashFactory;
    final private double maxLoadFactor;
    private int capacity;
    private HashFunctor<K> hashFunc;
    private int count;//to count how many elements are in the table
    private LinkedList<Pair<K,V>>[] arrHash;


    /*
     * You should add additional private members as needed.
     */

    public ChainedHashTable(HashFactory<K> hashFactory) {
        this(hashFactory, DEFAULT_INIT_CAPACITY, DEFAULT_MAX_LOAD_FACTOR);
    }

    public ChainedHashTable(HashFactory<K> hashFactory, int k, double maxLoadFactor) {
        this.hashFactory = hashFactory;
        this.maxLoadFactor = maxLoadFactor;
        this.capacity = 1 << k;
        this.hashFunc = hashFactory.pickHash(k);
        this.count=0;
        this.arrHash= new LinkedList[capacity];
    }

    public V search(K key) {
        int index =hashFunc.hash(key);
        LinkedList<Pair<K,V>> list = arrHash[index];

        if (list != null) {
            for (Pair<K,V> current : list) {
                if (current.first() == key) {
                    return current.second();
                }
            }
        }
        return null;
    }

    public void insert(K key, V value) {
        if((double)(this.count+1)/this.capacity >= maxLoadFactor) {
        	reHash();
        }
        int hashIndex=hashFunc.hash(key);
        Pair<K,V> add=new Pair<K, V>(key,value);
        if (arrHash[hashIndex] == null) {
        	arrHash[hashIndex] = new LinkedList<>();
        	arrHash[hashIndex].add(add);
        }
        
        else {
        	arrHash[hashIndex].addFirst(add);
        }
        
        count++;
    }

    public boolean delete(K key) {
        boolean deleted=false;
        int deleteIndex=hashFunc.hash(key);
        if(!arrHash[deleteIndex].isEmpty()) {
	        ListIterator<Pair<K,V>> it = arrHash[deleteIndex].listIterator();
	        while(it.hasNext()&&!deleted) {
				Pair<K,V> curr=it.next();
				if(curr.first()==key) {
					arrHash[deleteIndex].remove(curr);
					deleted=true;
					count--;
				}
	        }
        }
        return deleted;
    }

    public HashFunctor<K> getHashFunc() {
        return hashFunc;
    }

    public int capacity() { return capacity; }
    
    private void reHash() {
    	int k = ((int) (Math.log(capacity) / Math.log(2))) + 1;
    	capacity = capacity*2;
    	this.hashFunc = hashFactory.pickHash(k);
    	LinkedList<Pair<K,V>>[] reArr=new LinkedList[capacity];
    	for (LinkedList<Pair<K,V>> list : arrHash) {
            if (list != null) {
                for (Pair<K,V> pair : list) {
                    int newIndex = hashFunc.hash(pair.first());
                    if (reArr[newIndex] == null) {
                    	reArr[newIndex] = new LinkedList<>();
                    }
                    reArr[newIndex].add(pair);
                }
            }
    		arrHash=reArr;
    	}
    	
    }
}
